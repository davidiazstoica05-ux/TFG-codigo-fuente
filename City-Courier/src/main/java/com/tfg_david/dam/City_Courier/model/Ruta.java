package com.tfg_david.dam.City_Courier.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Ruta {
	
	@Id 
	@GeneratedValue
	private Long codigoRuta; 
	private LocalDateTime fechaSalida;
	private double distancia;

}
