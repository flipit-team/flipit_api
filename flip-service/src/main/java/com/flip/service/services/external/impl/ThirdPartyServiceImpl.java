package com.flip.service.services.external.impl;

import com.flip.service.exception.HttpResponseException;
import com.flip.service.services.external.ThirdPartyService;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Charles on 25/12/2022
 */
@Log4j2
@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    private final HttpClientBuilder httpClientBuilder;
    private CloseableHttpClient httpClient;
    private final Map<String, String> fixedHeaders;

    public ThirdPartyServiceImpl() {
        this(HttpClientBuilder.create());
    }

    public ThirdPartyServiceImpl(HttpClientBuilder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
        this.fixedHeaders = new HashMap<>();
        setContentType("application/json");

        // set in execute
        this.httpClient = null;
    }

    public void setContentType(String contentType) {
        addFixedHeader("Content-Type", contentType);
    }

    public void setAuthHeader(String value) {
        addFixedHeader("Authorization", value);
    }

    public void addFixedHeader(String key, String value) {
        fixedHeaders.put(key, value);
    }

    public void setBasicAuth(String username, String password) {
        String base64Str = new String(Base64.encodeBase64((username + ":" + password).getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
        setAuthHeader("Basic " + base64Str);
    }

    public InputStream get(String url, Map<String, String> additionalHeaders) throws IOException {
        HttpGet request = new HttpGet(url);
        return executeRequest(request, additionalHeaders);
    }

    public InputStream post(String url, Map<String, String> additionalHeaders, String body) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity((body == null) ? "" : body, Charsets.UTF_8));
        return executeRequest(request, additionalHeaders);
    }

    public InputStream put(String url, Map<String, String> additionalHeaders, String body) throws IOException {
        HttpPut request = new HttpPut(url);
        request.setEntity(new StringEntity((body == null) ? "" : body, Charsets.UTF_8));
        return executeRequest(request, additionalHeaders);
    }

    public InputStream delete(String url, Map<String, String> additionalHeaders) throws IOException {
        HttpDelete request = new HttpDelete(url);
        return executeRequest(request, additionalHeaders);
    }

    public InputStream patch(String url, Map<String, String> additionalHeaders, String body) throws IOException {
        HttpPatch request = new HttpPatch(url);
        request.setEntity(new StringEntity((body == null) ? "" : body, Charsets.UTF_8));
        return executeRequest(request, additionalHeaders);
    }

    public String getAsString(String url, Map<String, String> additionalHeaders) throws IOException {
        return streamToString(get(url, additionalHeaders));
    }

    public String postAsString(String url, Map<String, String> additionalHeaders, String body) throws IOException {
        return streamToString(post(url, additionalHeaders, body));
    }

    private HttpResponse execute(HttpRequestBase request, Map<String, String> additionalHeaders) throws IOException {
        for (Map.Entry<String, String> entry : fixedHeaders.entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue());
        }
        if (!CollectionUtils.isEmpty(additionalHeaders)) {
            for (Map.Entry<String, String> entry : additionalHeaders.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }

        request.setHeader("Host", request.getURI().getHost());

        /*
         * execute is meant to be called once, followed by consuming the
         * InputStream, and finally closeHttpClient().
         */
        if (this.httpClient != null) {
            closeHttpClient();
        }

        this.httpClient = this.httpClientBuilder.build();
        return this.httpClient.execute(request);
    }

    private InputStream executeRequest(HttpRequestBase request, Map<String, String> additionalHeaders) throws IOException {
        HttpResponse r = execute(request, additionalHeaders);
        int respCode = r.getStatusLine().getStatusCode();

        if (respCode == 204) {
            return null;
        }
        if (respCode < 200 || respCode > 209) {
            throw new HttpResponseException(r);
        }

        return r.getEntity().getContent();
    }

    private void closeHttpClient() {
        if (this.httpClient != null) {
            try {
                this.httpClient.close();
            } catch (IOException e) {
                log.warn("Failed to close P2PHttpClient httpClient", e);
            }
            this.httpClient = null;
        }
    }

    private static String streamToString(InputStream stream) throws IOException {
        if (stream == null) {
            return null;
        }

        // TODO: handle character encoding by checking the content type of the response
        return IOUtils.toString(stream);
    }

}
