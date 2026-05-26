package com.tfg_david.dam.City_Courier.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.repository.RutaRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeed {

	private final RepartidorRepository repoRepartidor;
	private final RutaRepository repoRuta;
	private final AsignacionRepository repoAsig;
	private final EnviosRepository repoEnvio; //

	@PostConstruct
	public void run() {

		Map<String, Double> rutaParadas1 = new LinkedHashMap<>();
		rutaParadas1.put("Lantejuela", 120.0);
		rutaParadas1.put("Marchena", 40.0);

		Map<String, Double> paradasRuta1 = new LinkedHashMap();
		paradasRuta1.put("Lantejuela", 120.0);
		paradasRuta1.put("Marchena", 40.0);

		Map<String, Double> paradasRuta2 = new LinkedHashMap();
		paradasRuta2.put("Osuna", 120.0);
		paradasRuta2.put("Arahal", 40.0);

		Ruta ruta = Ruta.builder()
		        .fechaFinal(LocalTime.of(16, 00))
		        .fechaInicio(LocalTime.of(8, 00))
		        .nombreRuta("Marchena-Lantejuela")
		        .puntosEntregas(paradasRuta1)
		        .build();

		Ruta ruta2 = Ruta.builder()
		        .fechaFinal(LocalTime.of(16, 00))
		        .fechaInicio(LocalTime.of(8, 00))
		        .nombreRuta("Arahal-Osuna")
		        .puntosEntregas(paradasRuta2)
		        .build();

		Repartidor r = Repartidor.builder()
		        .nombre("David")
		        .apellidos("Díaz Stoica")
		        .dni("31031909x")
		        .cargaMax(10.5)
		        .email("daviddiaz@gmail.com")
		        .fechaAlta(LocalDate.of(2026, 1, 1))
		        .genero("Male")
		        .zona("Osuna")
		        .telefono(697386581)
		        .estado(Disponibilidad.DISPONIBLE)
		        .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
		        .build();

		Repartidor r2 = Repartidor.builder()
		        .nombre("Miguel Angél")
		        .apellidos("Díaz Gallardo")
		        .dni("21590009x")
		        .cargaMax(10.5)
		        .email("migeldiaz80@gmail.com")
		        .fechaAlta(LocalDate.of(2026, 7, 1))
		        .genero("Male")
		        .zona("Osuna")
		        .telefono(697386581)
		        .estado(Disponibilidad.VACACIONES)
		        .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
		        .build();

		Envio e = Envio.builder()
		        .destinatario("Manuel Díaz")
		        .direccion("Plaza Juan de mesa")
		        .zona("Osuna")
		        .peso(1.7)
		        .build();

		Envio e2 = Envio.builder()
		        .destinatario("Laura Gómez")
		        .direccion("Calle Corredera 45")
		        .peso(3.2)
		        .zona("Osuna")
		        .build();

		Envio e3 = Envio.builder()
		        .destinatario("Elena Torres")
		        .direccion("Avenida de la Constitución 15")
		        .peso(0.8)
		        .zona("Sevilla Centro")
		        .build();

		Asignacion a = Asignacion.builder()
		        .coste(2.5)
		        .estadoPedido(false)
		        .fechaAsignacion(LocalDateTime.of(2026, 2, 1, 16, 00))
		        .fechaEntrega(LocalDateTime.of(2027, 3, 2, 14, 00))
		        .motivoIncidencia(" ")
		        .tiempoEstimado(null)
		        .build();

		Asignacion a2 = Asignacion.builder()
		        .coste(4.0)
		        .estadoPedido(false)
		        .fechaAsignacion(LocalDateTime.of(2026, 2, 1, 8, 00))
		        .fechaEntrega(LocalDateTime.of(2027, 3, 3, 10, 30))
		        .motivoIncidencia(" ")
		        .tiempoEstimado(null)
		        .build();

		a.vincularEnvio(e);

		r.addAsignacion(a);

		r.setRuta(ruta);

		a2.vincularEnvio(e2);

		r2.setRuta(ruta2);
		r2.addAsignacion(a2);

		repoRuta.save(ruta);
		repoRuta.save(ruta2);

		repoRepartidor.save(r);
		repoRepartidor.save(r2);

		repoAsig.save(a);
		repoAsig.save(a2);
		repoEnvio.save(e3);
	}
}