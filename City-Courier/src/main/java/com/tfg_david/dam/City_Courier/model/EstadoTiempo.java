package com.tfg_david.dam.City_Courier.model;

public enum EstadoTiempo {
	A_TIEMPO("A Tiempo"),
	EN_RIESGO("En Riesgo"),
	ATRASADO("Atrasado"),
	ENTREGADO("Entregado");

	private final String display;

	EstadoTiempo(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

}
