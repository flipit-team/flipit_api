package com.flip.service.services.external;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Charles on 25/12/2022
 */
public interface ThirdPartyService {

    void setContentType(String contentType);

    void setAuthHeader(String value);

    void addFixedHeader(String key, String value);

    void setBasicAuth(String username, String password);

    InputStream get(String url, Map<String, String> additionalHeaders) throws IOException;

    InputStream post(String url, Map<String, String> additionalHeaders, String body) throws IOException;

    InputStream put(String url, Map<String, String> additionalHeaders, String body) throws IOException;

    InputStream delete(String url, Map<String, String> additionalHeaders) throws IOException;

    InputStream patch(String url, Map<String, String> additionalHeaders, String body) throws IOException;

    String getAsString(String url, Map<String, String> additionalHeaders) throws IOException;

    String postAsString(String url, Map<String, String> additionalHeaders, String body) throws IOException;

}
