package com.tfg_david.dam.City_Courier.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
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
	
	@NotBlank(message = "La dirección del envío no puede estar vacia")
	private String direccion;
	
	@NotBlank(message = "La zona no puede estar vacia")
	private String zona;
	
	@NotBlank(message = "El codigo postal no puede estar vacio")
	@Size(max = 5, min = 5 )
	private String codPostal;
	
	@NotNull(message = "No puede estar incompleto")
	@DecimalMin(value = "0.5", message = "No puede ser menor a 0.5")
	private double peso;
	
	@FutureOrPresent( message = "la fecha de entrega debe de ser hoy o un futuro")
	private LocalDateTime fechaEntregaEstimada; 
	
	@NotBlank(message = "Tiene que haber un destinatario")
	private String destinatario; 
	
	@OneToOne
	@JoinColumn(name = "idAsignacion")
	private Asignacion asignacion; 
	
}
