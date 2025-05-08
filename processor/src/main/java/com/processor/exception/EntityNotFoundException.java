package com.processor.exception;

public class EntityNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5391415654919791567L;
	private String mensaje;
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public EntityNotFoundException(String mensaje) {
		super();
		this.mensaje = mensaje;
	}
}
