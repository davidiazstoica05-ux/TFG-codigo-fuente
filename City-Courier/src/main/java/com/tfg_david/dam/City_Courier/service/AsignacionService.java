package com.tfg_david.dam.City_Courier.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.excepciones.CapacidadExcedidaException;
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
	
	private final RepartidorService repartidorService;

	public boolean asignarRepartidor(Asignacion asig) {

		Optional<Repartidor> repartidor;

		String dni = asig.getRepartidor().getDni();

		if (dni != null && !dni.isEmpty()) {

			repartidor = repartidorRepo.findByDni(dni);

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

	public List<Asignacion> findByIdAsignacionOrRepartidorDni(Long idAsignacion, String busqueda) {

		return repo.findByIdAsignacionOrRepartidorDni(idAsignacion, busqueda);

	}

	public Long countByEstadoPedido(boolean estado) {

		return repo.countByEstadoPedido(estado);

	}

	public void deleteAsignacion(Long idAsignacion) {

		Optional<Asignacion> asig;

		asig = repo.findById(idAsignacion);

		if (asig.isPresent()) {

			repo.deleteById(idAsignacion);

		}

	}

	// Calcular precio según distancia precio y tiempo
	public void calcularPrecioDistanciaTiempoKm(List<Asignacion> asig) {

		Collection<Double> rutaDistancia;
		Optional<Double> distanciaKmOpt;
		Long minutos;
		double costeTotal, costeHoras, convertirADouble = 60.0, minutosDouble;

		for (Asignacion asignacion : asig) {
			
			if (asignacion.getRepartidor() != null && 
				    asignacion.getRepartidor().getRuta() != null && 
				    asignacion.getRepartidor().getRuta().getPuntosEntregas() != null) {
				
				rutaDistancia = asignacion.getRepartidor().getRuta().getPuntosEntregas().values();

				distanciaKmOpt = rutaDistancia.stream().findFirst();

				if (distanciaKmOpt.isPresent()) {

					costeTotal = asignacion.getCostePorKmYPeso() * distanciaKmOpt.get();

					asignacion.setCosteTotal(costeTotal);

				} 
					
				} else {
					
					asignacion.setCostePorKmYPeso(0);
			}

			

		}
		
		
		

	}
	
	
	

	public boolean validarCargaPeso(Asignacion asigForm) {
		
		Repartidor repartidor = asigForm.getRepartidor();
		double pesoTotalRepartidor, capacidadRestante;
	
		pesoTotalRepartidor = repartidorService.pesoTotalPaquetes(repartidor) + asigForm.getEnvio().getPeso();
	
		
		if (pesoTotalRepartidor >= repartidor.getCargaMax()) {
						
			return true;

		}else {
			
			capacidadRestante = repartidor.getCargaMax() - pesoTotalRepartidor ;
			
			throw new CapacidadExcedidaException(String.format("La capacidad restante del repartidor es de: %.2f kg", capacidadRestante));
		} 
		
		
		
		
		
		
		
		
	}

}
