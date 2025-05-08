package com.consumer.client.config.feing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.*;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;

import com.consumer.exception.BadRequestException;
import com.consumer.exception.EntityNotFoundException;
import com.consumer.exception.ServiceException;
import com.consumer.exception.UnauthorizedException;
import com.consumer.util.CommonUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Manejo de errores de la libreria Feing.
 */
@Slf4j
@Configuration
public class FeignErrorHandler {

	@Bean
	ErrorDecoder feignErrorDecoder() {
		return new CustomFeignErrorDecoder();
	}

	public static class CustomFeignErrorDecoder implements ErrorDecoder {
		@Override
		public Exception decode(String methodKey, Response response) {
			HttpStatusCode statusCode = HttpStatusCode.valueOf(response.status());
			String errorBody = getErrorBody(response);
			log.error(CommonUtil.SALTO_LINEA + "Error en la llamada a " + methodKey + CommonUtil.SALTO_LINEA 
											 + "ErrorBody: "+  errorBody+ CommonUtil.SALTO_LINEA  
											 + "StatusCode: "+ statusCode);
			// Método centralizado para manejar errores
			return handleError(statusCode, errorBody, methodKey);
		}

		private Exception handleError(HttpStatusCode statusCode, String errorBody, String methodKey) {
			return switch (statusCode.value()) {
				case 400 -> new BadRequestException("Error al llamar al servicio externo: mal request.");
				case 401 -> new UnauthorizedException("Error al llamar al servicio externo: acceso no autorizado.");
				case 404 -> new EntityNotFoundException("Error al llamar al servicio externo: recurso no encontrado en " + errorBody);
				default -> new ServiceException("Error al llamar al servicio externo.");
			};
		}

		private String getErrorBody(Response response) {
			try {
				if (response.body() != null) {
					return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
				}
			} catch (IOException ignored) {
			}
			return "Sin detalles del error";
		}
	}
}
