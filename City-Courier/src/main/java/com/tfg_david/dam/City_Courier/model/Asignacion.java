package com.tfg_david.dam.City_Courier.model;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	
	private LocalDateTime fechaAsignacion; 
	private LocalDateTime fechaEntrega; 
	
	private String motivoIncidencia;
	
	@ManyToOne 
	@JoinColumn(name = "dni")
	private Repartidor repartidor; 
	
	@ManyToOne
	@JoinColumn(name = "codigoRuta")
	private Ruta ruta;
	
	@OneToOne
	@JoinColumn(name = "codigoEnvio")
	private Envio envio;
	
	
	private Duration tiempoEstimado; // fechaInicio-fechaFinal; acordarse hacerla en service
	
	
	
}
