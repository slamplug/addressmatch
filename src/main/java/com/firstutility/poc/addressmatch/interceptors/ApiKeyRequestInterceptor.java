package com.firstutility.poc.addressmatch.interceptors;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;


public class ApiKeyRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final String API_KEY = "d1e3c9c04d7141d0c1f93b19d18bbddf3785e6c12f65e8741dc7765e1e97c31ca8db0bea7b956727695f29f78c50e53310699c83a80adba1c0c8e31e01ba3e40";

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution)
            throws IOException {
        HttpRequestWrapper wrapper = new HttpRequestWrapper(httpRequest);
        wrapper.getHeaders().set("API-Key", API_KEY);
        return clientHttpRequestExecution.execute(wrapper, bytes);
    }
}
