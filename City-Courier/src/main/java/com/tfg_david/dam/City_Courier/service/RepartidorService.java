package com.tfg_david.dam.City_Courier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.excepciones.RepartidorNoDisponibleException;
import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepartidorService extends BaseService<Repartidor, Long, RepartidorRepository> {

	private final AsignacionRepository repoAsig;

	public Long count() {

		return repo.count();

	}

	public Long countByEstado(Disponibilidad estado) {

		return repo.countByEstado(estado);

	}

	public List<Repartidor> findByNombreIgnoreCase(String nombre) {

		String limpiarNombre;

		limpiarNombre = nombre.trim();

		return repo.findByNombreIgnoreCase(limpiarNombre);

	}

	public Optional<Repartidor> findByDni(String dni) {

		return repo.findByDni(dni);

	}

	public double pesoTotalPaquetes(Repartidor repartidor) {

		List<Asignacion> asignacionesRepartidor = repartidor.getAsignacionesRepartidor();


		return asignacionesRepartidor.stream()
									 .mapToDouble(asig -> asig.getEnvio()
															  .getPeso())
															  .sum();



	}

	public List<Repartidor> findByEstado(Disponibilidad estado) {

		return repo.findByEstado(estado);
	}

	@Transactional
	public void deleteRepartidor(String dni) {

		Optional<Repartidor> repartidorOpt = repo.findByDni(dni);

		if (repartidorOpt.isPresent()) {
			Repartidor repartidor = repartidorOpt.get();
			List<Asignacion> asignacionesRepartidor = repoAsig.findByRepartidor(repartidor);
			
			asignacionesRepartidor.stream()
            .filter(asig -> asig.getEnvio() != null)
            .forEach(asig -> asig.getEnvio().setAsignacion(null));			


			repoAsig.deleteAll(asignacionesRepartidor);

			repo.deleteById(repartidor.getIdTrabajador());

		}

	}

}
