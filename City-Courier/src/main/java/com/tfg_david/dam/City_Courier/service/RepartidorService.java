package com.tfg_david.dam.City_Courier.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
public class RepartidorService extends BaseService<Repartidor, String, RepartidorRepository> {

	
	private final AsignacionRepository repoAsig; 
	
	public Long count() {
		
		return repo.count();
		
	}
	
	public Long countByEstado (Disponibilidad estado) {
		
		return repo.countByEstado(estado);
		
	}
	
	
	@Transactional
	public void deleteRepartidor(String dni) {
		
		Optional<Repartidor> repartidor; 
		repartidor = repo.findById(dni);
		List<Asignacion> asignacionesRepartidor; 
		
		if (repartidor.isPresent()) {
			
			
		asignacionesRepartidor = repoAsig.findByRepartidor(repartidor.get());
		
		repoAsig.deleteAll(asignacionesRepartidor);
		
		repo.deleteById(dni);
		
		}
		
	}
	
	

}
