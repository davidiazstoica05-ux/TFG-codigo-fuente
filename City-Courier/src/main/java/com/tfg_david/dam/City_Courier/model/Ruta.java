package com.tfg_david.dam.City_Courier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Ruta {
	
	@Id @GeneratedValue
	private Long codigoRuta; 
	
	private String nombreRuta; 
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFinal; 
	
	
	
	@ElementCollection 
	private Map<String, Double> puntosEntregas = new LinkedHashMap<>();
	
	@OneToMany (mappedBy = "ruta" , fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Repartidor> repartidores = new ArrayList<>();
	
	@OneToMany(mappedBy = "ruta", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Asignacion> asignacionesRuta = new ArrayList<>();
	
	public void addAsignacion (Asignacion asignacion) {
		
		this.asignacionesRuta.add(asignacion);
		
		asignacion.setRuta(this);
		
	}
	
	public void removeAsignacion(Asignacion asignacion) {
		
		
	this.asignacionesRuta.remove(asignacion); 
	
	asignacion.setRuta(null);
		
	}
	
	public void addRepartidor (Repartidor repartidor) {
		
		this.repartidores.add(repartidor);
		
		repartidor.setRuta(this);
		
	}
	
	public void removeRepartidor (Repartidor repartidor) {
		
		
	this.repartidores.remove(repartidor);
	
	repartidor.setRuta(null);
		
	}
	
	
}
