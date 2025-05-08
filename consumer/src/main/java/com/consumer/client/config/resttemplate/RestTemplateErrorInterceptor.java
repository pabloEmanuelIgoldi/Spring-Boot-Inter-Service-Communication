package com.consumer.client.config.resttemplate;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.consumer.exception.BadRequestException;
import com.consumer.exception.EntityNotFoundException;
import com.consumer.exception.ServiceException;
import com.consumer.exception.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;

/**
 * Manejo de errores de la libreria Rest Template.
 */
@Slf4j
public class RestTemplateErrorInterceptor implements ClientHttpRequestInterceptor{
	
    @Override
    public ClientHttpResponse intercept(
        HttpRequest request,
        byte[] body,
        ClientHttpRequestExecution execution
    ) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        
        // Manejar errores HTTP (4xx/5xx)
        if (response.getStatusCode().isError()) {
            handleError(response, request);
        }
        
        return response;
    } 
    
	private void handleError(ClientHttpResponse response, HttpRequest request) throws IOException {
		HttpStatusCode statusCode = response.getStatusCode();
		String errorBody = new String(response.getBody().readAllBytes());
		String url = request.getURI().toString();
		String method = request.getMethod().name();
		log.error("Error en " + method + " " + url + ": " + statusCode + " - " + errorBody, statusCode);
		if (statusCode instanceof HttpStatus httpStatus) {
			switch (httpStatus) {
			case NOT_FOUND:
				throw new EntityNotFoundException("Error al llamar al servicio externo: no existe el recurso.");
			case UNAUTHORIZED:
				throw new UnauthorizedException("Error al llamar al servicio externo: sin autorizacion para el recurso.");
			case BAD_REQUEST:
				throw new BadRequestException("Error al llamar al servicio externo: mal request.");
			default:
				throw new ServiceException("Error al llamar al servicio externo.");
			}
		}else {
			throw new ServiceException("Error al llamar al servicio externo.");
		}
	}
}
