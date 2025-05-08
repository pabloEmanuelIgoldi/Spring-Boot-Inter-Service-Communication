package com.consumer.client.producto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.consumer.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductoRestTemplateClientImpl implements ProductoRestTemplateClient {

	@Value("${app.provider.security.token}")
	private String token;

	@Value("${app.provider.endpoint.productos}")
	private String endpointGet;

	@Value("${app.provider.endpoint.producto-by-id}")
	private String endpointGetById;

	private final RestTemplate restTemplate;

	public ProductoRestTemplateClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ListProductoResponseDTO get() {
		ResponseEntity<ListProductoResponseDTO> response = restTemplate.exchange(endpointGet, 
																				 HttpMethod.GET,
																				 this.getEntity(), 
																				 ListProductoResponseDTO.class);
		return response.getBody();
	}
	
	public ProductoResponseDTO getById(Long id) throws Exception {
		ResponseEntity<ProductoResponseDTO> response = restTemplate.exchange(endpointGetById, 
																			 HttpMethod.GET,
																			 this.getEntity(), 
																			 ProductoResponseDTO.class, 
																			 id);		
		return response.getBody();		
	}


	private HttpEntity<String> getEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(CommonUtil.HEADER_KEY_AUTHORIZATION, CommonUtil.BEARER + token);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return entity;
	}
}
