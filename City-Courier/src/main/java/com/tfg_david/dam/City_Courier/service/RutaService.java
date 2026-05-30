package com.tfg_david.dam.City_Courier.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.excepciones.RepartidorNoDisponibleException;
import com.tfg_david.dam.City_Courier.excepciones.RutaInvalidaException;
import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.repository.RutaRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RutaService extends BaseService<Ruta, Long, RutaRepository> {

	private final RepartidorRepository repoRepartidor;

	private final RepartidorService repartidorService;

	public Map<String, Double> transformarString(List<String> zonaSeleccionada, Double distancia) {

		Map<String, Double> ruta = new LinkedHashMap<String, Double>();

		for (String zona : zonaSeleccionada) {

			ruta.put(zona, distancia);

		}

		return ruta;

	}

	public List<Ruta> findByIdOrNombreRuta(String nombreRuta, Long codigoRuta) {

		return repo.findByNombreRutaOrCodigoRuta(nombreRuta, codigoRuta);

	}

	public List<Ruta> obtenerTop3RutasMasFrecuentes() {

		return repo.rutasMasFrecuentes().stream().limit(3).toList();

	}

	// He decidido que la primera entrada de la key del map será la zona que se
	// validará al repartidor
	// V1 sin stream
	public boolean asignarRutaRepartidor(Ruta rutaForm, Long idTrabajador) {

		Map<String, Double> puntosEntregas;

		Optional<Repartidor> repartidorOpt = repartidorService.findById(idTrabajador);

		Repartidor repartidor;

		String zonaRuta;

		puntosEntregas = rutaForm.getPuntosEntregas();

		zonaRuta = puntosEntregas.keySet().stream().findFirst().get();

		if (repartidorOpt.isPresent()) {

			repartidor = repartidorOpt.get();

			if (repartidor.getZona() == null
					|| repartidor.getZona().equals(zonaRuta) && validarRepartidorEstaDisponible(repartidor)) {

				repartidor.setRuta(rutaForm);

				return true;
			}

		}

		throw new RutaInvalidaException("El repartidor no pertenece a está ruta");

	}
	

	public boolean validarRepartidorEstaDisponible(Repartidor repartidor) {

		if (repartidor.getEstado() == Disponibilidad.DISPONIBLE) {

			return true;

		}

		throw new RepartidorNoDisponibleException("El repartidor no está disponible");

	}

	// Delete y Save
	@Transactional
	public void deleteRuta(Long codRuta) {

		Optional<Ruta> ruta;
		ruta = repo.findById(codRuta);
		List<Repartidor> repartidoresEnRuta;

		if (ruta.isPresent()) {

			repartidoresEnRuta = repoRepartidor.findByRuta(ruta.get());

			for (Repartidor repartidor : repartidoresEnRuta) {

				repartidor.setRuta(null);

			}

			repo.deleteById(codRuta);

		}

	}

	@Override
	public Ruta save(Ruta ruta) {

		if (ruta.getPuntosEntregas() == null || ruta.getPuntosEntregas().isEmpty()) {

			throw new RutaInvalidaException("Los puntos de entregas no puedes estár vacíos");

		}

		if (ruta.getFechaFinal() != null && ruta.getFechaInicio() != null) {
			if (ruta.getFechaFinal().isBefore(ruta.getFechaInicio())) {
				throw new RutaInvalidaException("Error de planificación: La hora de finalización ("
						+ ruta.getFechaFinal() + ") no puede ser anterior a la hora de inicio.");
			}
		}

		return super.save(ruta);
	}

}
