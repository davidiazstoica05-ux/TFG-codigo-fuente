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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
	@NotBlank(message = "El DNI no puede estar vacío")
	@Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "El DNI debe tener 8 números seguidos de una letra")
	private String dni; 
	
	@NotBlank(message = "La zona es obligatoria")
	private String zona;
	
	@NotBlank(message = "Los apellidos son obligatorios")
	private String apellidos; 
	
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre; 
	
	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El formato del correo electrónico no es válido")
	private String email; 
	
	@NotBlank(message = "El teléfono es obligatorio")
	@Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe contener exactamente 9 dígitos numéricos")
	private String telefono;
	@NotBlank(message = "El género es obligatorio")
	private String genero; 
		
	@NotNull(message = "Debe seleccionar una disponibilidad")
	@Enumerated(EnumType.STRING)
	private Disponibilidad estado;
	
	@NotNull(message = "Debe seleccionar un tipo de vehículo")
	@Enumerated(EnumType.STRING)
	private TipoVehiculo vehiculo;
	
	@NotNull(message = "La fecha de alta es obligatoria")
	@PastOrPresent(message = "La fecha de alta no puede ser en el futuro")
	private LocalDate fechaAlta; 
	
	@NotNull(message = "La carga máxima es obligatoria")
	@Positive(message = "La carga máxima debe ser un valor positivo")
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