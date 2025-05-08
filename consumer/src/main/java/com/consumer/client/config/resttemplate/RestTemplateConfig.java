package com.consumer.client.config.resttemplate;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Bean
	RestTemplate restTemplate() {
	    //Configura timeouts
	    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	    factory.setConnectTimeout(5000);  // 5 segundos (en milisegundos)
	    factory.setReadTimeout(10000);    // 10 segundos

	    // Crea el RestTemplate con la fabrica
	    RestTemplate restTemplate = new RestTemplate(factory);

	    // Agrega interceptor de errores(400,401,404, etc)
	    restTemplate.setInterceptors(List.of(new RestTemplateErrorInterceptor()));

	    return restTemplate;
	}
}