package com.example.service2.config;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
public class StartUpConfig {

    @Value("${client.ssl.trustStore}")
    private String trustStore;

    @Value("${client.ssl.trustStore.password}")
    private String trustStorePassword;

    @Value("${client.ssl.version}")
    private String sslVersion;

    @Bean
    public RestTemplate getRestTemplate() {
        final SSLContext sslContext;
        try {
            sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(new File(trustStore),
                            trustStorePassword.toCharArray())
                    .setProtocol(sslVersion)
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to setup client SSL context", e);
        }
        final HttpClient httpClient = HttpClientBuilder.create()
                .setSSLContext(sslContext)
                .build();
        final ClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }
}
