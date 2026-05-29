package com.tfg_david.dam.City_Courier.excepciones;

public class CapacidadExcedidaException extends RuntimeException {
    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}