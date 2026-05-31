package com.tfg_david.dam.City_Courier.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.excepciones.CapacidadExcedidaException;
import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.AsignacionPk;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionService extends BaseService<Asignacion, AsignacionPk , AsignacionRepository> {

	private final RepartidorRepository repartidorRepo;
	private final EnviosRepository envioRepo;
	private final RepartidorService repartidorService;

	public boolean asignarRepartidor(Asignacion asig) {
		Optional<Repartidor> repartidor; 
		
		if (asig.getRepartidor() != null && asig.getRepartidor().getIdTrabajador() != null) {
			repartidor = repartidorRepo.findById(asig.getRepartidor().getIdTrabajador());
			if (repartidor.isPresent()) {
				asig.setRepartidor(repartidor.get());
				return true;
			}
		}
		return false;
	}
	

	public boolean asignarEnvio(Asignacion asig) {
		Optional<Envio> envio; 
		
		if (asig.getEnvio() != null && asig.getEnvio().getCodEnvio() != null) {
			envio = envioRepo.findById(asig.getEnvio().getCodEnvio());
			if (envio.isPresent()) {
				asig.setEnvio(envio.get());
				return true;
			}
		}
		return false;
	}

	public List<Asignacion> findByIdAsignacionOrRepartidorId(Long codEnvio, Long idTrabajador) {
		return repo.findByEnvio_CodEnvioOrRepartidor_IdTrabajador(codEnvio, idTrabajador);
	}

	public Long countByEstadoPedido(boolean estado) {
		return repo.countByEstadoPedido(estado);
	}

	
	
	public void deleteAsignacion(Long codEnvio, Long idTrabajador) {
		AsignacionPk pk; 
		Optional<Asignacion> asigOpt; 
		Asignacion asig; 
		Envio envio; 
		Repartidor repartidor; 
		
		pk = new AsignacionPk(codEnvio, idTrabajador);
		asigOpt = repo.findById(pk);

		if (asigOpt.isPresent()) {
			asig = asigOpt.get();
			envio = asig.getEnvio();
			repartidor = asig.getRepartidor();
			
			if (envio != null) {
				envio.setAsignacion(null);
			}
			
			if (repartidor != null) {
				repartidor.getAsignacionesRepartidor().remove(asig);
			}
			
			repo.delete(asig);
		}
	}

	public void calcularPrecioDistanciaTiempoKm(List<Asignacion> asig) {
		Collection<Double> rutaDistancia; 
		Optional<Double> distanciaKmOpt; 
		double costeTotal; 
		
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
		Repartidor repartidor; 
		double pesoTotalRepartidor; 
		double capacidadRestante; 
		repartidor = asigForm.getRepartidor();
		pesoTotalRepartidor = repartidorService.pesoTotalPaquetes(repartidor) + asigForm.getEnvio().getPeso();
	
		if (pesoTotalRepartidor <= repartidor.getCargaMax()) {
			return true;
			
		} else {
			
			capacidadRestante = repartidor.getCargaMax() - (pesoTotalRepartidor - asigForm.getEnvio().getPeso());
			throw new CapacidadExcedidaException(String.format("La capacidad restante del repartidor es de: %.2f kg", capacidadRestante));
		} 
	}
}