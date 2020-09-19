package com.renjian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebproApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebproApplication.class, args);
    }

    @Bean
    public RestTemplate getRest(){
        return new RestTemplate();
    }
}
