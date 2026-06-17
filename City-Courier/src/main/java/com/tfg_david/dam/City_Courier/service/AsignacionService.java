package com.tfg_david.dam.City_Courier.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.excepciones.CapacidadExcedidaException;
import com.tfg_david.dam.City_Courier.excepciones.RepartidorNoDisponibleException;
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
	
	// Inyección de variables globales desde application.properties
	@Value("${cc.logistica.coste-km}")
	private double costePorKm;

	@Value("${cc.logistica.recargo-peso}")
	private double recargoPorPeso;

	@Value("${cc.logistica.horas-riesgo}")
	private int horasRiesgo;

	@Value("${cc.logistica.co2-moto}")
	private double co2Moto;

	@Value("${cc.logistica.co2-furgoneta}")
	private double co2Furgoneta;
	
	// Buscan al repartidor para añadirlo a una asignacion
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

	// Buscan al envio para añadirlo a una asignación y comprueba que la zona sea la misma
	public boolean asignarEnvio(Asignacion asig) {

		Optional<Envio> envioOpt;
		Envio envio;
		Repartidor repartidor;
		String zonaEnvioStr;
		boolean zonaValida = false;

		if (asig.getEnvio() != null && asig.getEnvio().getCodEnvio() != null) {

			envioOpt = envioRepo.findById(asig.getEnvio().getCodEnvio());

			if (envioOpt.isPresent()) {

				envio = envioOpt.get();
				repartidor = asig.getRepartidor();
				zonaEnvioStr = envio.getZona().getDisplay();

				if (repartidor.getAsignacionesRepartidor().contains(envio.getAsignacion())) {
					throw new RepartidorNoDisponibleException("Ya está asignado");
				}

				if (repartidor.getRuta() == null) {
					throw new RepartidorNoDisponibleException("El repartidor no tiene ruta asignada");
				}

				if (repartidor != null && repartidor.getRuta() != null
						&& repartidor.getRuta().getPuntosEntregas() != null) {

					if (repartidor.getRuta().getPuntosEntregas().containsKey(zonaEnvioStr)) {
						zonaValida = true;
					}
				}

				if (zonaValida) {
					asig.setEnvio(envio);
					return true;
				}
			}
		}

		throw new RepartidorNoDisponibleException("El envio y el repartidor no tienen la misma zona");
	}

	public List<Asignacion> findByNombreRuta(String nombreRuta) {
		return repo.findByRepartidor_Ruta_NombreRutaContainingIgnoreCase(nombreRuta);
	}

	public List<Asignacion> findByIdAsignacionOrRepartidorId(Long codEnvio, Long idTrabajador) {
		return repo.findByEnvio_CodEnvioOrRepartidor_IdTrabajador(codEnvio, idTrabajador);
	}

	public Long countByEstadoPedido(boolean estado) {
		return repo.countByEstadoPedido(estado);
	}

	// Borra la asignación, pero para ello antes se desvincula del envio y el repartidor
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
		
		double precioBase;
		double costePeso;
		double costeDistancia;
		double costeTotalFinal;
		
		Envio envio;

		for (Asignacion asignacion : asig) {

			precioBase = 0;
			costePeso = 0;
			costeDistancia = 0;
			costeTotalFinal = 0;

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
			asignacion.setHuellaCarbono(calcularHuellaCarbono(asignacion));
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

			if (horasRestantes <= horasRiesgo) {
				return EstadoTiempo.EN_RIESGO;
			}
		}

		return EstadoTiempo.A_TIEMPO;
	}
	
	
	public double calcularHuellaCarbono(Asignacion asignacion) {
	    
		double distancia, factorEmision,emisionesBase,pesoPaquete;
		double penalizacionPeso;
		Optional<Double> distanciaOpt;
		
	    if (asignacion.getRepartidor() == null || asignacion.getRepartidor().getVehiculo() == null) {
	        return 0.0;
	    }

	    distancia = 0.0;
	    if (asignacion.getRepartidor().getRuta() != null && asignacion.getRepartidor().getRuta().getPuntosEntregas() != null) {
	         
	    	distanciaOpt = asignacion.getRepartidor().getRuta().getPuntosEntregas().values().stream().findFirst();
	        
	    	if (distanciaOpt.isPresent()) {
	        
	    		distancia = distanciaOpt.get();
	        
	    	}
	    }

	    factorEmision = 0.0;
	    
	    switch (asignacion.getRepartidor().getVehiculo()) {
	        
	    	case moto_ecologica:
	            factorEmision = 0.0; 
	            break;
	        
	        case Moto:
	            factorEmision = co2Moto;
	            break;
	        
	        case Furgoneta:
	            factorEmision = co2Furgoneta;
	            break;
	    }


	     emisionesBase = distancia * factorEmision;

	     pesoPaquete = (asignacion.getEnvio() != null) ? asignacion.getEnvio().getPeso() : 0.0;
	     penalizacionPeso = emisionesBase * (pesoPaquete * 0.05);

	    return emisionesBase + penalizacionPeso;
	}
	

	public void asignarAutomaticamente() {

		List<Envio> enviosSinAsignar;
		List<Repartidor> repartidoresDisponibles;
		
		enviosSinAsignar = envioService.findByAsignacionNull();
		repartidoresDisponibles = repartidorService.findByEstado(Disponibilidad.DISPONIBLE);

		for (Envio envio : enviosSinAsignar) {

			String zonaEnvioStr = envio.getZona().getDisplay();

			Optional<Repartidor> repartidorIdeal = repartidoresDisponibles.stream()

					.filter(repartidor -> repartidor.getRuta() != null
							&& repartidor.getRuta().getPuntosEntregas() != null
							&& repartidor.getRuta().getPuntosEntregas().containsKey(zonaEnvioStr))
					
					.filter(repartidor -> {
						double pesoTotalRepartidor = repartidorService.pesoTotalPaquetes(repartidor) + envio.getPeso();
						return pesoTotalRepartidor <= repartidor.getCargaMax();
					})
					
					.findFirst();

			if (repartidorIdeal.isPresent()) {

				Asignacion nuevaAsignacion = new Asignacion();

				nuevaAsignacion.setEstadoPedido(false);
				nuevaAsignacion.setEnvio(envio);
				nuevaAsignacion.setRepartidor(repartidorIdeal.get());

				monitorizarEntrega(nuevaAsignacion);

				save(nuevaAsignacion);
			}
		}
	}

}