package com.tfg_david.dam.City_Courier.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder

public class Envio {
	
	@Id 
	@GeneratedValue
	private Long codEnvio; 
	
	private String direccion;
	private String zona;
	private String codPostal;
	private double peso;
	private String destinatario; 
	
	@OneToOne
	@JoinColumn(name = "idAsignacion")
	private Asignacion asignacion; 
	
}
