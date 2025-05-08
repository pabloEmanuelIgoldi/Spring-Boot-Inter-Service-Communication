package com.consumer.exception;

public class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = 151189900880330225L;

	private String mensaje;

	public UnauthorizedException(String mensaje) {
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