package com.processor.exception;

public class UnauthorizedException extends Exception {

	private static final long serialVersionUID = 6712225373906192514L;
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
