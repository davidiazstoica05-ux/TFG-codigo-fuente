package com.tfg_david.dam.City_Courier.utilidades;

import org.springframework.dao.DataIntegrityViolationException;
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

	@ExceptionHandler(DataIntegrityViolationException.class)
	public String manejarViolacionDeIntegridad(DataIntegrityViolationException ex, Model model) {
		String nombreVista;
		String tituloError;
		String mensajeError;

		nombreVista = "errors/excepcion-negocio";
		tituloError = "Error de Datos Duplicados";

		mensajeError = "No se ha podido completar la operación porque los datos introducidos (como el DNI) ya están registrados en el sistema.";

		model.addAttribute("titulo", tituloError);
		model.addAttribute("mensaje", mensajeError);

		return nombreVista;
	}

	@ExceptionHandler(Exception.class)
	public String manejarExcepcionesGenerales(Exception ex, Model model) {
		String nombreVista;
		String tituloError;
		String mensajeError;

		nombreVista = "errors/excepcion-negocio";
		tituloError = "Error Inesperado del Servidor";
		mensajeError = "Ha ocurrido un error inesperado en la aplicación. Por favor, contacte con el soporte técnico.";

		model.addAttribute("titulo", tituloError);
		model.addAttribute("mensaje", mensajeError);

		ex.printStackTrace();

		return nombreVista;
	}
}
