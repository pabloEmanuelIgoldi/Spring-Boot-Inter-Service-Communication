package com.consumer.service.producto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.consumer.client.producto.ListProductoResponseDTO;
import com.consumer.client.producto.ProductoResponseDTO;
import com.consumer.client.producto.ProductoRestTemplateClient;
import com.consumer.dto.ProductoDTO;
import com.consumer.exception.ServiceException;
import com.consumer.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductoServiceImpl implements IProductoService {
	
	
	private final ProductoRestTemplateClient productoRestTemplateClient;

	public ProductoServiceImpl(ProductoRestTemplateClient productoRestTemplateClient) {		
		this.productoRestTemplateClient = productoRestTemplateClient;
	}

	@Retryable(
            maxAttempts = 5, //Cantidad de intentos.
            backoff = @Backoff(delay = 2000) // Espera de 2 segundos entre reintentos.
        )
	@Override
	public List<ProductoDTO> get() {
		  try {
				ListProductoResponseDTO response = this.productoRestTemplateClient.get();
				return this.mapper(response.getData());

	        } catch (ResourceAccessException ex) {
	        	// Captura: Connection refused, timeouts, etc.
	        	log.error(CommonUtil.SALTO_LINEA + "ERROR ProductoServiceImpl.get(): " + CommonUtil.SALTO_LINEA + ex);
    			throw new ServiceException("Error al llamar al servicio externo.");
	        }
	}

	private List<ProductoDTO> mapper(List<com.consumer.client.producto.ProductoDTO> data) {
		 return data.stream()
                 .map(dto -> new ProductoDTO(dto.getId(), dto.getNombre(), dto.getPrecio()))
                 .collect(Collectors.toList());
	}

    @Retryable(
            maxAttempts = 5, //Cantidad de intentos.
            backoff = @Backoff(delay = 2000) // Espera de 2 segundos entre reintentos.
        )
	@Override
	public ProductoDTO getById(Long id) throws Exception {
		try {
			log.info("INGRESO A ProductoServiceImpl.getById() con ID: " + id);
			ProductoResponseDTO response = this.productoRestTemplateClient.getById(id);
			com.consumer.client.producto.ProductoDTO productoResponse = response.getData();
			return ProductoDTO.builder().id(productoResponse.getId())
										.nombre(productoResponse.getNombre())
										.precio(productoResponse.getPrecio())
										.build();
		}catch (ResourceAccessException ex) {
        	log.error(CommonUtil.SALTO_LINEA + "ERROR ProductoServiceImpl.get(): " + CommonUtil.SALTO_LINEA + ex);
			throw new ServiceException("Error al llamar al servicio externo.");
		}
	}

}
