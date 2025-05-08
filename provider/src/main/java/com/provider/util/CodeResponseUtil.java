package com.provider.util;

public class CodeResponseUtil {
	// Peticion exitosa
	public static final int SUCCESS = 1100;
	public static final int CREATED = 1101;

	// Errores de cliente
	public static final int ENTITY_NOT_FOUND = 1200;
	public static final int BAD_REQUEST = 1201;
	
	// Errores de autorizacion/autenticacion
	public static final int UNAUTHORIZED = 1301;
	
	// Errores del servidor
    public static final int INTERNAL_ERROR = 1500;
}
