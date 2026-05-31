package com.tfg_david.dam.City_Courier.model;


import java.util.ArrayList;
import java.util.List;

import com.tfg_david.dam.City_Courier.model.Trabajador.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class Repartidor extends Trabajador{

	@Enumerated(EnumType.STRING)
	private Zona zona;
	
	
	@Enumerated(EnumType.STRING)
	private Disponibilidad estado;
	
	@Enumerated(EnumType.STRING)
	private TipoVehiculo vehiculo;
	
	
	@NotNull
	@DecimalMin(value = "0.5", message = "El valor minimo es 0,5")
	private Double cargaMax;
	
	@ManyToOne 
	@JoinColumn( name = "codigoRuta")
	private Ruta ruta; 
	
	
	
	@OneToMany (mappedBy = "repartidor" , fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Asignacion> asignacionesRepartidor = new ArrayList<>();
	
	
	//Metodos helper
	
	public void addAsignacion (Asignacion asignacion) {
		
		this.asignacionesRepartidor.add(asignacion);
		
		asignacion.setRepartidor(this);
		
	}
	
	public void removeAsignacion(Asignacion asignacion) {
		
		
	this.asignacionesRepartidor.remove(asignacion); 
	
	asignacion.setRepartidor(null);
		
	
	}
	
	
	//Asignar rol nada más crearse
	  @PrePersist 
	    public void asignarRol() {
	        this.setRol(Rol.REPARTIDOR);
	    }

}
