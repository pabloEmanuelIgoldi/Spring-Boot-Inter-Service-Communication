package com.consumer.client.venta;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.consumer.util.CommonUtil;

import feign.RequestInterceptor;

/**
 * Crea un hearder-authorization para las api que usen esta configuracion.
 */
@Configuration
public class FeignVentasConfig {

	@Value("${app.processor.security.token}")
	private String ventasToken;

	@Bean
	RequestInterceptor ventasTokenInterceptor() {
		return template -> template.header(CommonUtil.HEADER_KEY_AUTHORIZATION, CommonUtil.BEARER + ventasToken);
	}
}
