package com.tfg_david.dam.City_Courier.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.excepciones.CapacidadExcedidaException;
import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.AsignacionPk;
import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.EstadoTiempo;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionService extends BaseService<Asignacion, AsignacionPk, AsignacionRepository> {

	private final RepartidorRepository repartidorRepo;
	private final EnviosRepository envioRepo;
	private final RepartidorService repartidorService;
	private final EnviosService envioService;
	
	
	//Buscan al repartidor para añadirlo a una asignacion
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

	//Buscan al envio para añadirlo a una asignación
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

	// Borra la asignación , pero para ello antes se desvincula del envio y el
	// repartidor

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

	// CalcularPrecioDistancia
	public void calcularPrecioDistanciaTiempoKm(List<Asignacion> asig) {
		Collection<Double> rutaDistancia;
		Optional<Double> distanciaKmOpt;
		double costeTotal, precioBase = 0, costePeso = 0, recargoPorPeso = 0.25, costeDistancia = 0;
		double costePorKm = 0.20, costeTotalFinal = 0;
		Envio envio;

		for (Asignacion asignacion : asig) {

			envio = asignacion.getEnvio();

			if (envio != null) {

				precioBase = envio.getPrioridad().getPrecioBase();

				costePeso = envio.getPeso() * recargoPorPeso;

			}

			if (asignacion.getRepartidor() != null && asignacion.getRepartidor().getRuta() != null
					&& asignacion.getRepartidor().getRuta().getPuntosEntregas() != null) {

				rutaDistancia = asignacion.getRepartidor().getRuta().getPuntosEntregas().values();
				distanciaKmOpt = rutaDistancia.stream().findFirst();

				if (distanciaKmOpt.isPresent()) {

					costeDistancia = distanciaKmOpt.get() * costePorKm;

				}

				costeTotalFinal = precioBase + costePeso + costeDistancia;
			}

			asignacion.setCosteTotal(costeTotalFinal);

		}

	}

	// ValidarCargaPeso

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
			throw new CapacidadExcedidaException(
					String.format("La capacidad restante del repartidor es de: %.2f kg", capacidadRestante));
		}
	}

	// MonitorizarEntrega
	public EstadoTiempo monitorizarEntrega(Asignacion asignacion) {

		LocalDateTime ahora;
		LocalDateTime limite;
		Long horasRestantes;

		ahora = LocalDateTime.now();

		if (asignacion.isEstadoPedido()) {

			return EstadoTiempo.ENTREGADO;

		}

		if (asignacion.getEnvio() != null && asignacion.getEnvio().getFechaEntregaLimite() != null) {
			limite = asignacion.getEnvio().getFechaEntregaLimite();

			if (ahora.isAfter(limite)) {
				return EstadoTiempo.ATRASADO;
			}

			horasRestantes = Duration.between(ahora, limite).toHours();

			if (horasRestantes <= 24) {
				return EstadoTiempo.EN_RIESGO;
			}
		}

		return EstadoTiempo.A_TIEMPO;

	}
	



	// Asginación automatica
	// Para Ángel: Despues de la pechá que me he pegado en este proyecto quería hacer la joya de
	// la corona. Así que hice esto, que en verdad es una bobería pero me parece divertido. 
	
	//Como se que te gusta ver las dos versiones te pongo las dos :p
	
	// Versión "Nueva"
	
	public void asignarAutomaticamente() {

		List<Envio> enviosSinAsignar;
		List<Repartidor> repartidoresDisponibles;
		
		Asignacion nuevaAsignacion = null; 
			
		double pesoTotalRepartidor;
		
	
		// Envios sin asignar
		
		enviosSinAsignar = envioService.findByAsignacionNull();
		
		//Repartidores disponibles
		repartidoresDisponibles = repartidorService.findByEstado(Disponibilidad.DISPONIBLE);
				

		for (Envio envio : enviosSinAsignar) {

			for (Repartidor repartidor : repartidoresDisponibles) {

				if (envio.getZona().equals(repartidor.getZona())) {
					
					pesoTotalRepartidor = repartidorService.pesoTotalPaquetes(repartidor) + envio.getPeso();
					
					if (pesoTotalRepartidor <= repartidor.getCargaMax() ) {
						
						nuevaAsignacion = new Asignacion();
						
						nuevaAsignacion.setEstadoPedido(false);
						nuevaAsignacion.setEnvio(envio);
						nuevaAsignacion.setRepartidor(repartidor);
						
						save(nuevaAsignacion);
						
						break;
						
					}
					

				}

			}

		}

	}
	
	
	
	//Version antigua
	/*
	 * 
	 * 	public void asignarAutomaticamente() {

		List<Envio> envios = new ArrayList<>();
		List<Repartidor> repartidores = new ArrayList<>();
		List<Envio> enviosSinAsignar = new ArrayList<>();
		List<Repartidor> repartidoresDisponibles = new ArrayList<>();
		
		Asignacion nuevaAsignacion = null; 
		
		envios = envioRepo.findAll();
		repartidores = repartidorRepo.findAll();
		
		double pesoTotalRepartidor;
		
		// Envios sin asignar
		for (Envio envio : envios) {

			if (envio != null && envio.getAsignacion() == null) {

				enviosSinAsignar.add(envio);

			}

		}

		// Repartidores disponibles
		for (Repartidor repartidor : repartidores) {

			if (repartidor != null && repartidor.getEstado() == Disponibilidad.DISPONIBLE) {

				repartidoresDisponibles.add(repartidor);

			}

		}

		for (Envio envio : enviosSinAsignar) {

			for (Repartidor repartidor : repartidoresDisponibles) {

				if (envio.getZona().equals(repartidor.getZona())) {
					
					pesoTotalRepartidor = repartidorService.pesoTotalPaquetes(repartidor) + envio.getPeso();
					
					if (pesoTotalRepartidor <= repartidor.getCargaMax() ) {
						
					
						nuevaAsignacion = new Asignacion();
						
						nuevaAsignacion.setEnvio(envio);
						nuevaAsignacion.setRepartidor(repartidor);
						
						save(nuevaAsignacion);
						
						break;
						
					}
					

				}

			}

		}

	}
	 * */

}