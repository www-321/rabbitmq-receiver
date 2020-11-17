package com.rabbitreceiver.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(value = Exception.class)
    public Object FeignException() {
        return "";
    }
}
