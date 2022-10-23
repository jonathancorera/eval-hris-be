

/*
 * A class that holds the web configuration to allows Cross Origin Resource Sharing.
 * This is required in order to let Spring Security to allow requests from client 
 * when both client are backend are running on the same IP address.
 */
package com.ccms.hris.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }
}