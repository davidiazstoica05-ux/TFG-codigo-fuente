package com.tfg_david.dam.City_Courier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFinal; 
	private double distancia;
	
	@ElementCollection //Valorar el cambio a enum 
	private List<String> puntosEntregas = new ArrayList<>(); 
	
	private String codigoPostal; 
	
	@OneToMany (mappedBy = "ruta" , fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Repartidor> repartidor = new ArrayList<>();
	
	@OneToMany(mappedBy = "ruta", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Asignacion> asignacionesRuta = new ArrayList<>();

}
