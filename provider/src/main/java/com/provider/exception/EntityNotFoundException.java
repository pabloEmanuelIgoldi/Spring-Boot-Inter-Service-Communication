package com.provider.exception;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = -385965482839859375L;	
	private String mensaje;

	public EntityNotFoundException(String mensaje) {
		super();
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
