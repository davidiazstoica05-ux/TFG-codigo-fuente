package com.tfg_david.dam.City_Courier.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	
	@NotBlank(message = "La dirección no puede esta vacía")
	private String direccion;
	
	private String zona;
	
	@NotBlank
	@Size(min = 5, max = 5, message = "Debe contener exactamente 5 caracteres")
	private String codPostal;
	
	
	@Max(value = 50, message = "El peso no puede ser mayor a 50")
	@DecimalMin( value = "0.5" , message = "El peso no puede ser menor a 0,5kg" )
	private double peso;
	
	@NotBlank
	private String destinatario; 
	
	@OneToOne
	@JoinColumn(name = "idAsignacion")
	private Asignacion asignacion; 
	
}
