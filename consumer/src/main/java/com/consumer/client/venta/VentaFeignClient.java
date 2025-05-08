package com.consumer.client.venta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "productos-client", url = "${app.processor.venta.url}", configuration = FeignVentasConfig.class )
public interface VentaFeignClient {

	@GetMapping
	ListVentaResponseDTO get();

	@GetMapping("/{id}")
	VentaResponseDTO getById(@PathVariable Long id);
	
	@PostMapping
	void post (@RequestBody VentaRequestDTO req);
	
	@DeleteMapping("/{id}")
	void delete(@PathVariable Long id);
}