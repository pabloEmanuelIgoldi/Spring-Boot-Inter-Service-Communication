package com.processor.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VentaDTO {
    
	private Long id;
	private Date fecha;	
	private double montoTotal;
	private List<Long> idsProducto;	
}
