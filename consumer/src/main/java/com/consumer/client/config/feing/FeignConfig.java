package com.consumer.client.config.feing;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

@Configuration
public class FeignConfig {

	@Bean
    Request.Options options() {
		 return new Request.Options(
	                3000L, TimeUnit.MILLISECONDS, // Timeout de conexión
	                6000L, TimeUnit.MILLISECONDS, // Timeout de lectura
	                false                         // followRedirects
	        );
    }

}
