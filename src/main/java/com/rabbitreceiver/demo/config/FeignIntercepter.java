package com.rabbitreceiver.demo.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignIntercepter implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("token", "1232323");

    }
}
