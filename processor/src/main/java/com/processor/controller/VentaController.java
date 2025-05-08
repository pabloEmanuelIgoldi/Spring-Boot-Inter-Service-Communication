package com.processor.controller;

import org.springframework.web.bind.annotation.RestController;

import com.processor.dto.ApiResponseDTO;
import com.processor.dto.VentaDTO;
import com.processor.dto.VentaRequestDTO;
import com.processor.exception.EntityNotFoundException;
import com.processor.exception.UnauthorizedException;
import com.processor.util.CodeResponseUtil;
import com.processor.util.MensajeResponseUtil;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequestMapping("/v1/ventas")
public class VentaController {
	
	@Value("${security.token}")
	private String token;   
	private List<VentaDTO> ventas = new ArrayList<>();
	private Long idVentaCounter = 1L;
	
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<VentaDTO>>> get(@RequestHeader Map<String, String> headers) throws Exception {
		this.validarAutorizacion(headers);
		return ResponseEntity.status(HttpStatus.OK).body(
													new ApiResponseDTO<>(true, 
																		 MensajeResponseUtil.SUCCESS, 
																		 CodeResponseUtil.SUCCESS, 
																		 LocalDateTime.now(), 
																		 this.ventas));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<VentaDTO>> getById(@PathVariable Long id, @RequestHeader Map<String, String> headers) throws Exception {
		this.validarAutorizacion(headers);
		return ResponseEntity.status(HttpStatus.OK).body(
													new ApiResponseDTO<>(true, 
																		 MensajeResponseUtil.SUCCESS, 
																		 CodeResponseUtil.SUCCESS, 
																		 LocalDateTime.now(), 
																		 this.buscarVentaPorId(id)));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDTO<String>> post(@RequestBody VentaRequestDTO req, @RequestHeader Map<String, String> headers) throws Exception {
		this.validarAutorizacion(headers);
		this.agregarVenta(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(
													new ApiResponseDTO<>(true, 
																		 MensajeResponseUtil.CREATED, 
																		 CodeResponseUtil.CREATED, 
																		 LocalDateTime.now(), 
																		 null));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<String>> delete(@PathVariable Long id, @RequestHeader Map<String, String> headers) throws Exception {
		this.eliminarVenta(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, 
																			  MensajeResponseUtil.SUCCESS, 
																			  CodeResponseUtil.SUCCESS, 
																			  LocalDateTime.now(), 
																			  null));
	}
	
	private void agregarVenta(VentaRequestDTO req) {
		VentaDTO nuevaVenta = VentaDTO.builder().id(this.idVentaCounter++)
												.fecha(new Date())
												.idsProducto(req.getIdsProducto())
												.montoTotal(req.getMontoTotal())
												.build();
	    ventas.add(nuevaVenta);
	}
	
	private VentaDTO buscarVentaPorId(Long id) throws Exception {
		VentaDTO venta = ventas.stream().filter(p -> p.getId().equals(id)).findFirst()
												  .orElseThrow(() -> new EntityNotFoundException("Venta no encontrado con ID: " + id));
		return venta;
	}


	private boolean eliminarVenta(Long id) throws Exception {
		this.buscarVentaPorId(id);
		return ventas.removeIf(v -> v.getId().equals(id));
	}
	
	private void validarAutorizacion(Map<String, String> headers) throws UnauthorizedException {
		String bearerToken = headers.get("authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String tokenRequest = bearerToken.substring(7);
            if(!token.equals(tokenRequest)) {
            	throw new UnauthorizedException("Usuario sin autorizacion.");
            }
        }else {
        	throw new UnauthorizedException("Usuario sin autorizacion.");
        }
	}	

}
