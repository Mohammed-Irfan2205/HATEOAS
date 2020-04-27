package com.hateoas.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan; 

@ComponentScan(basePackages = "com.hateoas")
@SpringBootApplication
public class HateOasApplication extends SpringBootServletInitializer {

    private static Class applicationClass = HateOasApplication.class;

    public static void main(String[] args) {
        SpringApplication.run(HateOasApplication.class, args);
    }
}
