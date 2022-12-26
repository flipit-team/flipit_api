package com.flip.service.exception;

import org.apache.http.HttpResponse;

import java.io.IOException;

public class HttpResponseException extends IOException {
    final HttpResponse response;

    public HttpResponse getResponse() {
        return response;
    }

    public HttpResponseException(HttpResponse response) {
        super(response.getStatusLine().getReasonPhrase());
        this.response = response;
    }

}