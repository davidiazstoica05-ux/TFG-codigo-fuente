package com.tfg_david.dam.City_Courier.model;

import java.time.Duration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Asignacion {

	@Id 
	@GeneratedValue
	private Long idAsignacion;
	private boolean estadoPedido; 
	private double coste; 
	
	@ManyToOne 
	@JoinColumn(name = "dni")
	private Repartidor repartidor; 
	
	@ManyToOne
	@JoinColumn(name = "codigoRuta")
	private Ruta ruta;
	
	@ManyToOne
	@JoinColumn(name = "codigoEnvio")
	private Envio envio;
	
	
	private Duration tiempoEstimado; // ruta.getDistancia()/envio.getVelocidad(); acordarse hacerla en service
	
	
	
}
