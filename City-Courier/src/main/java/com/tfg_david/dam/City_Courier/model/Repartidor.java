package com.tfg_david.dam.City_Courier.model;


import java.time.LocalDate;
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
	private String apellidos; 
	private String nombre; 
	private String email; 
	private int telefono; 
	private String genero; 
	private enum estado {DISPONIBLE,EN_RUTA,DE_BAJA,VACACIONES}
	private enum tipoVehiculo {Bicicleta, moto, furgoneta, patín_electrico, moto_ecologica}
	private LocalDate fechaAlta; 
	private Double PesoTotal;
	
	@ManyToOne 
	@JoinColumn( name = "codigoRuta")
	private Ruta ruta; 
	
	
	
	@OneToMany (mappedBy = "repartidor" , fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Asignacion> asignacionesRepartidor = new ArrayList<>();

}
