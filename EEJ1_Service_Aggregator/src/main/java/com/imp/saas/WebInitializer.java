package com.imp.saas;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class WebInitializer extends SpringBootServletInitializer {   
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MultitenancyApplication.class);
    }    
}