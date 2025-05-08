package com.consumer.service.venta;

import java.util.List;

import com.consumer.dto.VentaDTO;
import com.consumer.dto.VentaRequestDTO;

public interface IVentaService {
	
	List<VentaDTO> get();
	
	VentaDTO getById(Long id);
	
	void addVenta(VentaRequestDTO req);
	
	void delete(Long id);
}
