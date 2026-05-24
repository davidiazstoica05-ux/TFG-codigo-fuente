package com.tfg_david.dam.City_Courier.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionService extends BaseService<Asignacion, Long, AsignacionRepository> {

	private final RepartidorRepository repartidorRepo;

	private final EnviosRepository envioRepo;

	public boolean asignarRepartidor(Asignacion asig) {

		Optional<Repartidor> repartidor;

		String dni = asig.getRepartidor().getDni();

		if (dni != null && !dni.isEmpty()) {

			repartidor = repartidorRepo.findById(dni.toLowerCase());

			if (repartidor.isPresent()) {

				asig.setRepartidor(repartidor.get());

				return true;

			}

		}

		return false;
	}

	public boolean asignarEnvio(Asignacion asig) {

		Optional<Envio> envio;

		Long idEnvio = asig.getEnvio().getCodEnvio();

		if (idEnvio != null && idEnvio != 0) {

			envio = envioRepo.findById(idEnvio);

			if (envio.isPresent()) {

				asig.setEnvio(envio.get());
				envio.get().setAsignacion(asig);

				return true;

			}

		}

		return false;

	}

}
