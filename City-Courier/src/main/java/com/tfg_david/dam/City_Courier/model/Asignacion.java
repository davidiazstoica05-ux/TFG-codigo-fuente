package com.tfg_david.dam.City_Courier.model;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(AsignacionPk.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asignacion {

	
	
	private boolean estadoPedido;
	
	
	private double costeTotal;
	
	private LocalDateTime fechaAsignacion;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@FutureOrPresent(message = "La fecha planificada debe ser hoy o en el futuro.")
	private LocalDateTime fechaEntrega;

	private String motivoIncidencia;

	@Id
	@ManyToOne
	@JoinColumn(name = "idTrabajador")
	private Repartidor repartidor;
	
	@Id
	@OneToOne
	@JoinColumn(name = "codEnvio")
	private Envio envio;

	@DurationMin(minutes = 15, message = "El tiempo estimado tiene que ser mayor a 15 minutos")
	private Duration tiempoEstimado; 

	public void vincularEnvio(Envio envio) {

		this.envio = envio;

		if (envio != null) {

			envio.setAsignacion(this);

		}

	}
	
	@Transient //Para que no lo guarde en la bbdd
	private EstadoTiempo estadoTiempo;

	@Transient 
	private double huellaCarbono;
	
	
	@PrePersist
	public void configurarHoraActural() {
		
		
		if (fechaAsignacion == null) {
			
			
			fechaAsignacion = LocalDateTime.now();
			
		}
		
		
		
	}

}
