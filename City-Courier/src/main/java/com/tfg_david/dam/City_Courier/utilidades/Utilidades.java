package com.tfg_david.dam.City_Courier.utilidades;

public class Utilidades {

	public static Long extraerCodigoSiEsNumerico(String busqueda) {

		char caracter;

		if (busqueda == null || busqueda.trim().isEmpty()) {

			return null;

		}

		busqueda = busqueda.trim();
		caracter = busqueda.charAt(0);

		if (Character.isDigit(caracter)) {

			return Long.valueOf(busqueda);

		}

		return null;

	}

	public static boolean comprobarSiEsDNI(String busqueda) {

		if (busqueda == null || busqueda.trim().isEmpty()) {

			return false;
		}

		busqueda = busqueda.trim();

		return busqueda.matches("[0-9]{8}[A-Za-z]");
	}

}
