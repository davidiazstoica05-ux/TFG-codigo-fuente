package com.tfg_david.dam.City_Courier.model;

public enum TipoVehiculo {
	Bicicleta("Bicicleta"), Moto("Moto"), Furgoneta("Furgoneta"), patín_electrico ("Patín electrico"), moto_ecologica("Moto ecologica"); 

	String descripcion;

	private TipoVehiculo(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	
}
