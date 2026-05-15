package com.tfg_david.dam.City_Courier.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private Long dni; 
	private String zona;
	private String apellidos; 
	private String nombre; 
	private String email; 
	private int telefono; 
	private String genero; 
	
	public enum Disponibilidad {
		DISPONIBLE,EN_RUTA,DE_BAJA,VACACIONES
		}
	
	@Enumerated(EnumType.STRING)
	private Disponibilidad estado;
	
	public enum TipoVehiculo {
		Bicicleta, moto, furgoneta, patín_electrico, moto_ecologica
		}
	
	@Enumerated(EnumType.STRING)
	private TipoVehiculo vehiculo;
	
	private LocalDate fechaAlta; 
	private Double cargaMax;
	
	@ManyToOne 
	@JoinColumn( name = "codigoRuta")
	private Ruta ruta; 
	
	
	
	@OneToMany (mappedBy = "repartidor" , fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Asignacion> asignacionesRepartidor = new ArrayList<>();
	
	
	//Metodo getter Disponible
	public boolean isDisponible() {
		
		if (getEstado().equals(Disponibilidad.DISPONIBLE)) {
			
			return true; 
			
		}
		
		return false; 
		
	}

}
