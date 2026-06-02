package com.tfg_david.dam.City_Courier.utilidades;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tfg_david.dam.City_Courier.excepciones.CapacidadExcedidaException;
import com.tfg_david.dam.City_Courier.excepciones.RepartidorNoDisponibleException;
import com.tfg_david.dam.City_Courier.excepciones.RutaInvalidaException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CapacidadExcedidaException.class)
    public String manejarCapacidadExcedida(CapacidadExcedidaException ex, Model model) {
        String nombreVista;
        String tituloError;
        String mensajeError;

        nombreVista = "errors/excepcion-negocio";
        tituloError = "Límite de Carga Superado";
        mensajeError = ex.getMessage();

        model.addAttribute("titulo", tituloError);
        model.addAttribute("mensaje", mensajeError);

        return nombreVista;
    }

    @ExceptionHandler(RutaInvalidaException.class)
    public String manejarRutaInvalida(RutaInvalidaException ex, Model model) {
        String nombreVista;
        String tituloError;
        String mensajeError;

        nombreVista = "errors/excepcion-negocio";
        tituloError = "Problema con la Ruta";
        mensajeError = ex.getMessage();

        model.addAttribute("titulo", tituloError);
        model.addAttribute("mensaje", mensajeError);

        return nombreVista;
    }

    @ExceptionHandler(RepartidorNoDisponibleException.class)
    public String manejarRepartidorNoDisponible(RepartidorNoDisponibleException ex, Model model) {

    	String nombreVista;
        String tituloError;
        String mensajeError;

        nombreVista = "errors/excepcion-negocio";
        tituloError = "Repartidor No Disponible";
        mensajeError = ex.getMessage();

        model.addAttribute("titulo", tituloError);
        model.addAttribute("mensaje", mensajeError);

        return nombreVista;
    }
}