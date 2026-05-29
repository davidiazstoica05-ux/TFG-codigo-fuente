package com.tfg_david.dam.City_Courier.excepciones;

public class RepartidorNoDisponibleException extends RuntimeException {
    public RepartidorNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}