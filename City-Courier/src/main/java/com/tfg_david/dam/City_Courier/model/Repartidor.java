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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	
	@NotBlank(message = "La zona no puede estar en blanco")
	private String zona;
	
	@NotBlank(message = "Los apellidos no pueden estar en blanco")
	private String apellidos; 
	
	@NotBlank(message = "El nombre no puede estar en blanco")
	private String nombre; 
	
	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email no tiene un formato válido")	private String email; 
	
	@NotBlank(message = "El telefono es obligatorio")
	@Pattern(regexp = "^[6-9]\\d{8}$", message = "El teléfono debe tener 9 dígitos y empezar por 6, 7, 8 o 9")
	private String telefono; 
	
	@NotBlank(message = "El genero no puede estar en blanco")
	private String genero; 
		
	@Enumerated(EnumType.STRING)
	private Disponibilidad estado;
	
	@Enumerated(EnumType.STRING)
	private TipoVehiculo vehiculo;
	
	private LocalDate fechaAlta; 
	
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
	
	
public void addAsignacion (Asignacion asignacion) {
		
		this.asignacionesRepartidor.add(asignacion);
		
		asignacion.setRepartidor(this);
		
	}
	
	public void removeAsignacion(Asignacion asignacion) {
		
		
	this.asignacionesRepartidor.remove(asignacion); 
	
	asignacion.setRepartidor(null);
		
	}

}
