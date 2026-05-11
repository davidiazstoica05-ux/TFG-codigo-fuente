package com.tfg_david.dam.City_Courier.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
	private double direccion;
	private double peso;
	private double velocidad;
	
	@OneToOne(mappedBy = "envio")
	private Asignacion asignacion; 
	
}
