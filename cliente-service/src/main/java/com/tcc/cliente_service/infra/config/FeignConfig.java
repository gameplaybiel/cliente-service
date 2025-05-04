package com.tcc.cliente_service.infra.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(
                5000, // connectTimeout
                5000  // readTimeout
        );
    }
}