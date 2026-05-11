package com.tfg_david.dam.City_Courier.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Repartidor {

	@Id
	private String dni; 
	private String zona;
	private String nombre; 
	private String apellidos; 
	private LocalDateTime fechaInicioRuta; 
	private LocalDateTime fechaFinalRuta; 
	
	@ManyToOne 
	@JoinColumn( name = "codigoRuta")
	private Ruta ruta; 
	
	@OneToMany (mappedBy = "repartidor" , fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Asignacion> asignacionesRepartidor = new ArrayList<>();

}
