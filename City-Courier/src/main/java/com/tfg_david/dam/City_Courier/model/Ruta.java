package com.tfg_david.dam.City_Courier.model;

import java.time.LocalTime;
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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ruta {

	@Id
	@GeneratedValue
	private Long codigoRuta;

	@NotBlank(message = "El nombre de la ruta no puede estar en blanco")
	private String nombreRuta;
	
	@NotNull(message = "La hora de inicio es obligatoria")
	private LocalTime fechaInicio;

	@NotNull(message = "La hora final es obligatoria")
	private LocalTime fechaFinal;

	@NotNull
	@ElementCollection
	private Map<String, Double> puntosEntregas = new LinkedHashMap<>();

	@OneToMany(mappedBy = "ruta", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Repartidor> repartidores = new ArrayList<>();

	

	public void addRepartidor(Repartidor repartidor) {

		this.repartidores.add(repartidor);

		repartidor.setRuta(this);

	}

	public void removeRepartidor(Repartidor repartidor) {

		this.repartidores.remove(repartidor);

		repartidor.setRuta(null);

	}

}
