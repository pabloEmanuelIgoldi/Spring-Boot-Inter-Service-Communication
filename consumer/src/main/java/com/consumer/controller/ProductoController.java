package com.consumer.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.dto.ApiResponseDTO;
import com.consumer.dto.ProductoDTO;
import com.consumer.service.producto.IProductoService;
import com.consumer.util.CodeResponseUtil;
import com.consumer.util.MensajeResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/consumer/producto")
@Tag(name = "Producto", description = "Endpoints para gestionar productos.")
@Validated
public class ProductoController {
    
	private final IProductoService productoService;

	public ProductoController(IProductoService productoService) {
		this.productoService = productoService;
	}
	
	
	@Operation(summary = "Busqueda.", description = "Retorna todos los productos sin filtros.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Error en la peticion."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "404", description = "Recurso no existente."),
					@ApiResponse(responseCode = "404", description = "Recurso no existente."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Problemas con servicios externos.")})
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<ProductoDTO>>> get() {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, MensajeResponseUtil.SUCCESS,
				CodeResponseUtil.SUCCESS, LocalDateTime.now(), this.productoService.get()));
	}
	
	@Operation(summary = "Busqueda por ID.", description = "Retorna un producto filtrando por ID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Error en la peticion."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "404", description = "Recurso no existente."),
					@ApiResponse(responseCode = "404", description = "Recurso no existente."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Problemas con servicios externos.")})
	@Parameters({@Parameter(name = "id", description = "ID del Producto"),})
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<ProductoDTO>> getById(@PathVariable @Positive Long id) throws Exception {
		return ResponseEntity.status(HttpStatus.OK)
								.body(new ApiResponseDTO<>(true, 
														   MensajeResponseUtil.SUCCESS, 
														   CodeResponseUtil.SUCCESS, 
														   LocalDateTime.now(), 
														   this.productoService.getById(id)));
	}
	
	
	
}
