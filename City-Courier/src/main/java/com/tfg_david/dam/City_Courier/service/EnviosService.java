package com.tfg_david.dam.City_Courier.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnviosService extends BaseService<Envio, Long, EnviosRepository> {

	
	private final AsignacionRepository repoAsig;
	
	public Long countEnvios() {
		
		return repo.count();
		
	}
	
	@Transactional
	public void deleteEnvio(Long codEnvio) {
		
		Optional<Envio> envio; 
		envio = repo.findById(codEnvio);
		List<Asignacion> asignacionesRepartidor; 
		
		if (envio.isPresent()) {
			
			
		asignacionesRepartidor = repoAsig.findByEnvio(envio.get());
		
		repoAsig.deleteAll(asignacionesRepartidor);
		
		repo.deleteById(codEnvio);
		
		}
		
	}
	
	
}
