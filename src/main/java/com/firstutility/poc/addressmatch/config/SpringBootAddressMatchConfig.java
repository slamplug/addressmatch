package com.firstutility.poc.addressmatch.config;


import com.firstutility.poc.addressmatch.interceptors.ApiKeyRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpringBootAddressMatchConfig {

    @Bean(name = "apiKeyRestTemplate")
    public RestTemplate apiKeyRestTemplate() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new ApiKeyRequestInterceptor());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
