package com.provider.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provider.dto.ApiResponseDTO;
import com.provider.dto.ProductoDTO;
import com.provider.exception.EntityNotFoundException;
import com.provider.exception.UnauthorizedException;
import com.provider.util.CodeResponseUtil;
import com.provider.util.MensajeResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Productos.", description = "Manejo de operaciones para productos.")
@RestController
@RequestMapping("v1/productos")
@Validated
public class ProductoController {
	
	@Value("${security.token}")
	private String token;
	
	@Operation(summary = "Busqueda.", description = "Retorna todos los productos sin filtros.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Servicio no disponible.")})
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<ProductoDTO>>> get(@RequestHeader Map<String, String> headers) throws Exception {
		this.validarAutorizacion(headers);
		List<ProductoDTO> data = this.mockProductos();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO<>(true, MensajeResponseUtil.SUCCESS,
				CodeResponseUtil.SUCCESS, LocalDateTime.now(), data));
	}

	@Operation(summary = "Busqueda.", description = "Retorna un producto filtrando por ID.")
	@Parameters({@Parameter(name = "id", description = "ID del Producto"),})
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Request erroneo."),
					@ApiResponse(responseCode = "401", description = "Usuario no autorizado."),
					@ApiResponse(responseCode = "404", description = "Entidad no encontrada."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor."),
					@ApiResponse(responseCode = "503", description = "Servicio no disponible.")})
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<ProductoDTO>> getById(@PathVariable @Positive Long id, @RequestHeader Map<String, String> headers) throws Exception {
		log.info("BUSCAR PRODUCTO POR ID: " + id + ".");
		this.validarAutorizacion(headers);
		ProductoDTO data = this.buscarProductoPorId(id);
		return ResponseEntity.status(HttpStatus.OK)
								.body(new ApiResponseDTO<>(true, MensajeResponseUtil.SUCCESS, CodeResponseUtil.SUCCESS, LocalDateTime.now(), data));
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

	private ProductoDTO buscarProductoPorId(Long id) throws Exception {
		List<ProductoDTO> productos = this.mockProductos();
		ProductoDTO producto = productos.stream().filter(p -> p.getId().equals(id)).findFirst()
				.orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
		return producto;
	}

	private List<ProductoDTO> mockProductos() {
		List<ProductoDTO> productos = new ArrayList<>();
		productos.add(ProductoDTO.builder().id(234L).nombre("Latop Dell Inspiron 3535").precio(1245000.0).build());
		productos.add(ProductoDTO.builder().id(2111L).nombre("Placa red Pci Express").precio(32456.9).build());
		productos.add(ProductoDTO.builder().id(24679L).nombre("Disco SSD Kingston 480 GB").precio(32456.9).build());
		return productos;
	}

}
