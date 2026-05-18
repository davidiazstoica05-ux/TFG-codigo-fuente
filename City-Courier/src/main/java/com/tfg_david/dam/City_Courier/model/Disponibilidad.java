package com.tfg_david.dam.City_Courier.model;

public enum Disponibilidad {
	DISPONIBLE("Disponible"), EN_RUTA("En ruta"), DE_BAJA("De baja"), VACACIONES("Vacaciones");

	String descripcion;

	private Disponibilidad(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
