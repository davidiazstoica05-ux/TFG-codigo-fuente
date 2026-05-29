package com.tfg_david.dam.City_Courier.excepciones;

public class RutaInvalidaException extends RuntimeException {
    public RutaInvalidaException(String mensaje) {
        super(mensaje);
    }
}