package com.consumer.client.producto;

public interface ProductoRestTemplateClient {

	ListProductoResponseDTO get();

	ProductoResponseDTO getById(Long id) throws Exception;
}
