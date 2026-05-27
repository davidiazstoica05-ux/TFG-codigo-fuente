package com.tfg_david.dam.City_Courier.model;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asignacion {

	@Id
	@GeneratedValue
	private Long idAsignacion;
	private boolean estadoPedido;
	
	@NotNull(message = "Es obligatorio")
	@Min(value = 1, message = "El valor no puede ser inferior a 1")
	private double coste;
	
	private LocalDateTime fechaAsignacion;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@FutureOrPresent(message = "La fecha planificada debe ser hoy o en el futuro.")
	private LocalDateTime fechaEntrega;

	private String motivoIncidencia;

	
	@ManyToOne
	@JoinColumn(name = "dni")
	private Repartidor repartidor;
	
	@OneToOne(mappedBy = "asignacion", cascade = CascadeType.ALL)
	private Envio envio;

	@DurationMin(minutes = 15, message = "El tiempo estimado tiene que ser mayor a 15 minutos")
	private Duration tiempoEstimado; // fechaInicio-fechaFinal; acordarse hacerla en service

	public void vincularEnvio(Envio envio) {

		this.envio = envio;

		if (envio != null) {

			envio.setAsignacion(this);

		}

	}

	@PrePersist
	public void configurarHoraActural() {
		
		
		if (fechaAsignacion == null) {
			
			
			fechaAsignacion = LocalDateTime.now();
			
		}
		
		
		
	}

}
