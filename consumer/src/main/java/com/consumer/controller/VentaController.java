package com.consumer.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.dto.ApiResponseDTO;
import com.consumer.dto.VentaDTO;
import com.consumer.dto.VentaRequestDTO;
import com.consumer.service.venta.IVentaService;
import com.consumer.util.CodeResponseUtil;
import com.consumer.util.MensajeResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/ventas")
@Tag(name = "Venta", description = "Endpoints para gestionar ventas.")
@Validated
public class VentaController {
    
	private final IVentaService ventaService;

	public VentaController(IVentaService ventaService) {
		this.ventaService = ventaService;
	}
	
	@Operation(summary = "Busqueda.", description = "Retorna todos las ventas sin filtros.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Error en la peticion."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "404", description = "Recurso no existente."),
					@ApiResponse(responseCode = "404", description = "Recurso no existente."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Problemas con servicios externos.")})
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<VentaDTO>>> get() {
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponseDTO<>(true, MensajeResponseUtil.SUCCESS,
											CodeResponseUtil.SUCCESS, LocalDateTime.now(), this.ventaService.get()));
	}
	
	@Operation(summary = "Busqueda por ID.", description = "Retorna una venta filtrando por ID.")
	@Parameters({@Parameter(name = "id", description = "ID de la venta."),})
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Request erroneo."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "404", description = "Entidad no encontrada."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Servicio no disponible.")})
	@GetMapping("/{id}")
	public  ResponseEntity<ApiResponseDTO<VentaDTO>> getById(@PathVariable @Positive Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponseDTO<>(true, 
									 MensajeResponseUtil.SUCCESS,
									 CodeResponseUtil.SUCCESS, 
									 LocalDateTime.now(), 
									 this.ventaService.getById(id)));
	}
	
	@Operation(summary = "Creacion.", description = "Crea una venta con los datos del resquest.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Creacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Request erroneo."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Servicio no disponible.")})
	@PostMapping
	public ResponseEntity<ApiResponseDTO<String>> createdVenta( @Parameter(description = "Información de la nuevo venta") @Valid @RequestBody VentaRequestDTO req) {
		this.ventaService.addVenta(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(
													new ApiResponseDTO<>(true, 
																		 MensajeResponseUtil.CREATED,
																		 CodeResponseUtil.CREATED, 
																		 LocalDateTime.now(), 
																		 null));
	}
	
	@Operation(summary = "Eliminacion.", description = "Elimina una venta filtrando por ID.")
	@Parameters({@Parameter(name = "id", description = "ID de la Venta"),})	
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Request erroneo."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "404", description = "Entidad no encontrada."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Servicio no disponible.")})
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<String>> delete(@PathVariable @Positive Long id) throws Exception {
		this.ventaService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true,
																			  MensajeResponseUtil.SUCCESS,
																			  CodeResponseUtil.SUCCESS, 
																			  LocalDateTime.now(), 
																			  null));
	}
	
	
	
}
