package com.tfg_david.dam.City_Courier.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.repository.RutaRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

@Service
public class RutaService extends BaseService<Ruta, Long, RutaRepository> {

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

	public Long ExtraerCodigoSiEsNumerico(String busqueda) {

		if (busqueda == null || busqueda.trim().isEmpty()) {

			return null;

		}

		String limpiar = busqueda.trim();
		char caracter = limpiar.charAt(0);

		if (Character.isDigit(caracter)) {

			return Long.valueOf(busqueda);

		}

		return null;

	}

}
