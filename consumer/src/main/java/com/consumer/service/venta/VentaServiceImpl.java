package com.consumer.service.venta;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.consumer.client.venta.ListVentaResponseDTO;
import com.consumer.client.venta.VentaFeignClient;
import com.consumer.client.venta.VentaResponseDTO;
import com.consumer.dto.VentaDTO;
import com.consumer.dto.VentaRequestDTO;
import com.consumer.exception.ServiceException;
import com.consumer.util.CommonUtil;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VentaServiceImpl implements IVentaService {
	
	private final VentaFeignClient productoFeingClient;

	public VentaServiceImpl(VentaFeignClient productoFeingClient) {
		this.productoFeingClient = productoFeingClient;
	}

	@Retryable(
            maxAttempts = 5, //Cantidad de intentos.
            backoff = @Backoff(delay = 2000) // Espera de 2 segundos entre reintentos.
        )
	@Override
	public List<VentaDTO> get() {
		try {
			ListVentaResponseDTO response = this.productoFeingClient.get();
			return this.mapper(response.getData());
		} catch (FeignException ex) {
			log.error(CommonUtil.SALTO_LINEA + "ERROR VentaServiceImpl.get(): " + CommonUtil.SALTO_LINEA + ex);
			throw new ServiceException("Error al llamar al servicio externo.");
		}
	}
	
	private List<VentaDTO> mapper(List<com.consumer.client.venta.VentaDTO> data) {
		 return data.stream()
				 	 .map(dto -> new VentaDTO(dto.getId(), dto.getFecha(), dto.getMontoTotal(), dto.getIdsProducto()))
				 	 .collect(Collectors.toList());
	}
    
	@Retryable(
            maxAttempts = 5, //Cantidad de intentos.
            backoff = @Backoff(delay = 2000) // Espera de 2 segundos entre reintentos.
        )
	@Override
	public VentaDTO getById(Long id) {
		try {
			VentaResponseDTO response = this.productoFeingClient.getById(id);
			com.consumer.client.venta.VentaDTO vtaResponse = response.getData();		 
			return VentaDTO.builder().id(vtaResponse.getId())
									 .fecha(vtaResponse.getFecha())
									 .idsProducto(vtaResponse.getIdsProducto())
									 .montoTotal(vtaResponse.getMontoTotal())
									 .build();
		} catch (FeignException ex){
			log.error(CommonUtil.SALTO_LINEA + "ERROR VentaServiceImpl.getById(): " + CommonUtil.SALTO_LINEA + ex);
			throw new ServiceException("Error al llamar al servicio externo.");
		 }
	}

	@Override
	public void addVenta(VentaRequestDTO req) {
		try {
			com.consumer.client.venta.VentaRequestDTO clientVentaDto = 
					com.consumer.client.venta.VentaRequestDTO.builder()
															 .montoTotal(req.getMontoTotal())
															 .idsProducto(req.getIdsProducto())
															 .build();
			this.productoFeingClient.post(clientVentaDto);
		} catch (FeignException ex){
			log.error(CommonUtil.SALTO_LINEA + "ERROR VentaServiceImpl.getById(): " + CommonUtil.SALTO_LINEA + ex);
			throw new ServiceException("Error al llamar al servicio externo.");
		 }
		
	}

	@Retryable(
            maxAttempts = 5, //Cantidad de intentos.
            backoff = @Backoff(delay = 2000) // Espera de 2 segundos entre reintentos.
        )
	@Override
	public void delete(Long id) {
		try {
			this.productoFeingClient.delete(id);
		} catch (FeignException ex){
			log.error(CommonUtil.SALTO_LINEA + "ERROR VentaServiceImpl.delete(): " + CommonUtil.SALTO_LINEA + ex);
			throw new ServiceException("Error al llamar al servicio externo.");
		 }
		
	}

}
