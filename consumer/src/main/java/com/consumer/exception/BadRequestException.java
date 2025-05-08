package com.consumer.exception;

/**
 * Error 400 en el llamado de servicios externos.
 */
public class BadRequestException extends RuntimeException {
	
    private static final long serialVersionUID = -5764158627075890943L;
    private String mensaje;

    public BadRequestException(String mensaje) {
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
