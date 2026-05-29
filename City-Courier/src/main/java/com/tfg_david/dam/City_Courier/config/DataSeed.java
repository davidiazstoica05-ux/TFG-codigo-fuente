package com.tfg_david.dam.City_Courier.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tfg_david.dam.City_Courier.model.Admin;
import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Logistica;
import com.tfg_david.dam.City_Courier.model.RRHH;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.model.Trabajador;
import com.tfg_david.dam.City_Courier.repository.AsignacionRepository;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.repository.RutaRepository;
import com.tfg_david.dam.City_Courier.repository.TrabajadorRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeed {

	private final RepartidorRepository repoRepartidor;
	private final RutaRepository repoRuta;
	private final AsignacionRepository repoAsig;
	private final EnviosRepository repoEnvio; 
	private final TrabajadorRepository trabajdorRepo;
	private final PasswordEncoder passwordEncoder;

	@PostConstruct
	public void run() {

		Map<String, Double> paradasRuta1 = new LinkedHashMap<>();
		paradasRuta1.put("Lantejuela", 120.0);
		paradasRuta1.put("Marchena", 40.0);

		Map<String, Double> paradasRuta2 = new LinkedHashMap<>();
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
		        .dni("31031909X")
		        .email("daviddiaz@gmail.com")
		        .fechaAlta(LocalDate.of(2026, 1, 1))
		        .genero("Hombre")
		        .telefono("697386581")
		        .activo(true)
		        .usuario("repartidor")
		        .passw(passwordEncoder.encode("repartidor"))
		        .cargaMax(10.5)
		        .zona("Osuna")
		        .estado(Disponibilidad.DISPONIBLE)
		        .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
		        .build();

		Repartidor r2 = Repartidor.builder()
		        .nombre("Miguel Angél")
		        .apellidos("Díaz Gallardo")
		        .dni("21590009X")
		        .email("migeldiaz80@gmail.com")
		        .fechaAlta(LocalDate.of(2026, 7, 1))
		        .genero("Hombre")
		        .telefono("697386582")
		        .activo(true)
		        .usuario("miguel")
		        .passw(passwordEncoder.encode("1234"))
		        .cargaMax(10.5)
		        .zona("Osuna")
		        .estado(Disponibilidad.VACACIONES)
		        .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
		        .build();

		Admin admin = Admin.builder()
				.nombre("Jefe")
				.apellidos("Administración")
				.dni("11111111A")
				.email("admin@citycourier.com")
				.telefono("600000001")
				.genero("Hombre")
				.activo(true)
				.fechaAlta(LocalDate.now())
				.usuario("admin") 
				.passw(passwordEncoder.encode("admin"))         
				.build();

		RRHH rrhh = RRHH.builder()
				.nombre("Laura")
				.apellidos("Recursos Humanos")
				.dni("22222222B")
				.email("rrhh@citycourier.com")
				.telefono("600000002")
				.genero("Mujer")
				.activo(true)
				.fechaAlta(LocalDate.now())
				.usuario("rrhh") 
				.passw(passwordEncoder.encode("rrhh"))         
				.build();

		Logistica logistica = Logistica.builder()
				.nombre("Carlos")
				.apellidos("Operaciones")
				.dni("33333333C")
				.email("logistica@citycourier.com")
				.telefono("600000003")
				.genero("Hombre")
				.activo(true)
				.fechaAlta(LocalDate.now())
				.usuario("logistica") 
				.passw(passwordEncoder.encode("logistica"))         
				.build();

		Envio e = Envio.builder()
		        .destinatario("Manuel Díaz")
		        .direccion("Plaza Juan de mesa")
		        .codPostal("41640")
		        .zona("Osuna")
		        .peso(1.7)
		        .fechaEntregaEstimada(LocalDateTime.of(2027, 2, 1, 14, 00))
		        .build();

		Envio e2 = Envio.builder()
		        .destinatario("Laura Gómez")
		        .direccion("Calle Corredera 45")
		        .codPostal("47902")
		        .peso(3.2)
		        .zona("Osuna")
		        .fechaEntregaEstimada(LocalDateTime.of(2027, 2, 1, 10, 30))
		        .build();

		Envio e3 = Envio.builder()
		        .destinatario("Elena Torres")
		        .direccion("Avenida de la Constitución 15")
		        .codPostal("42321")
		        .peso(0.8)
		        .zona("Sevilla Centro")
		        .fechaEntregaEstimada(LocalDateTime.of(2027, 2, 2, 11, 15))
		        .build();

		Asignacion a = Asignacion.builder()
		        .coste(2.5)
		        .estadoPedido(false)
		        .fechaEntrega(LocalDateTime.of(2027, 2, 1, 14, 00))
		        .build();

		Asignacion a2 = Asignacion.builder()
		        .coste(4.0)
		        .estadoPedido(false)
		        .fechaEntrega(LocalDateTime.of(2027, 2, 1, 10, 30))
		        .build();
		
		a.vincularEnvio(e);
		r.addAsignacion(a);
		r.setRuta(ruta);

		a2.vincularEnvio(e2);
		r2.setRuta(ruta2);
		r2.addAsignacion(a2);

		repoRuta.save(ruta);
		repoRuta.save(ruta2);

		trabajdorRepo.save(admin);
		trabajdorRepo.save(rrhh);
		trabajdorRepo.save(logistica);
		
		repoRepartidor.save(r);
		repoRepartidor.save(r2);

		repoAsig.save(a);
		repoAsig.save(a2);
		repoEnvio.save(e3);
	}
}