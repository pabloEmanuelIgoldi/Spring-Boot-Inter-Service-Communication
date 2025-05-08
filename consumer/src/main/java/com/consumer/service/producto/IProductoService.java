package com.consumer.service.producto;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.consumer.dto.ProductoDTO;

public interface IProductoService {
    
	List<ProductoDTO> get();
	
	ProductoDTO getById(@PathVariable Long id)  throws Exception;
}
