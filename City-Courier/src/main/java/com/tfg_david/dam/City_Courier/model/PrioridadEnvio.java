package com.tfg_david.dam.City_Courier.model;

public enum PrioridadEnvio {
	
	AHORRO(2.0),
	NORMAL(5.0),
	URGENTE(10.0);

	private final double precioBase;

	PrioridadEnvio(double precioBase) {
		this.precioBase = precioBase;
	}

	public double getPrecioBase() {
		return precioBase;
	}
}