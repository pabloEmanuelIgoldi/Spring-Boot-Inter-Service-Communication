package com.consumer.exception;

/**
 * Error general en el llamado de servicios externos.
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 2389702169640972411L;
	private String mensaje;
	
	public ServiceException(String mensaje) {
		super();
		this.setMensaje(mensaje);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
