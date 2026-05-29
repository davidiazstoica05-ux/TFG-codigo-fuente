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
		
		LocalDateTime ahora = LocalDateTime.now();
		LocalDateTime fechaEntrega1 = ahora.plusDays(1).withHour(14).withMinute(0); 
		LocalDateTime fechaEntrega2 = ahora.plusDays(1).withHour(10).withMinute(30);
		LocalDateTime fechaEntrega3 = ahora.plusDays(2).withHour(11).withMinute(15);
		
		LocalDateTime fechaAsignacion = ahora.minusHours(2);

		Map<String, Double> paradasRuta1 = new LinkedHashMap<>();
		paradasRuta1.put("Lantejuela", 20.0);
		paradasRuta1.put("Marchena", 20.0);

		Map<String, Double> paradasRuta2 = new LinkedHashMap<>();
		paradasRuta2.put("Osuna", 40.0);
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
		        .fechaAlta(LocalDate.now().minusMonths(5))
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
		        .nombre("Miguel Ángel")
		        .apellidos("Díaz Gallardo")
		        .dni("28934511Z")
		        .email("mdiaz.logistica@gmail.com")
		        .fechaAlta(LocalDate.now().minusYears(1))
		        .genero("Hombre")
		        .telefono("612345678")
		        .activo(true)
		        .usuario("miguel")
		        .passw(passwordEncoder.encode("1234"))
		        .cargaMax(10.5)
		        .zona("Osuna")
		        .estado(Disponibilidad.VACACIONES)
		        .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
		        .build();
		
		Repartidor r3 = Repartidor.builder()
		        .nombre("Claudia")
		        .apellidos("Piñero Pineda")
		        .dni("22997473A")
		        .email("claudia@gmail.com")
		        .fechaAlta(LocalDate.now().minusMonths(5))
		        .genero("Mujer")
		        .telefono("697386581")
		        .activo(true)
		        .usuario("repartidor")
		        .passw(passwordEncoder.encode("repartidor"))
		        .cargaMax(1.0)
		        .zona("Marchena")
		        .estado(Disponibilidad.DISPONIBLE)
		        .vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
		        .build();

		Admin admin = Admin.builder()
				.nombre("Alejandro")
				.apellidos("Ruiz Navarro")
				.dni("45678123A")
				.email("aruiz@citycourier.com")
				.telefono("655112233")
				.genero("Hombre")
				.activo(true)
				.fechaAlta(LocalDate.now().minusYears(3))
				.usuario("admin") 
				.passw(passwordEncoder.encode("admin"))         
				.build();

		RRHH rrhh = RRHH.builder()
				.nombre("Carmen")
				.apellidos("Velasco Silva")
				.dni("23456789B")
				.email("cvelasco@citycourier.com")
				.telefono("644998877")
				.genero("Mujer")
				.activo(true)
				.fechaAlta(LocalDate.now().minusYears(2))
				.usuario("rrhh") 
				.passw(passwordEncoder.encode("rrhh"))         
				.build();

		Logistica logistica = Logistica.builder()
				.nombre("Javier")
				.apellidos("García Montero")
				.dni("34567890C")
				.email("jgarcia@citycourier.com")
				.telefono("633445566")
				.genero("Hombre")
				.activo(true)
				.fechaAlta(LocalDate.now().minusMonths(8))
				.usuario("logistica") 
				.passw(passwordEncoder.encode("logistica"))         
				.build();

		Envio e = Envio.builder()
		        .destinatario("Manuel Díaz")
		        .direccion("Plaza Juan de Mesa, 4, Bajo C")
		        .codPostal("41640") 
		        .zona("Osuna")
		        .peso(1.7)
		        .fechaEntregaEstimada(fechaEntrega1) 
		        .build();

		Envio e2 = Envio.builder()
		        .destinatario("Laura Gómez")
		        .direccion("Calle Corredera, 45, 1ºA")
		        .codPostal("41640")
		        .peso(3.2)
		        .zona("Osuna")
		        .fechaEntregaEstimada(fechaEntrega2)
		        .build();

		Envio e3 = Envio.builder()
		        .destinatario("Elena Torres")
		        .direccion("Calle Betis, 52, 2º Izquierda")
		        .codPostal("41010")
		        .peso(0.8)
		        .zona("Sevilla Centro")
		        .fechaEntregaEstimada(fechaEntrega3)
		        .build();

		Asignacion a = Asignacion.builder()
		        .costePorKmYPeso(2.5)
		        .estadoPedido(false)
		        .fechaAsignacion(fechaAsignacion) 
		        .fechaEntrega(fechaEntrega1)      
		        .build();

		Asignacion a2 = Asignacion.builder()
		        .costePorKmYPeso(2.5)
		        .estadoPedido(false)
		        .fechaAsignacion(fechaAsignacion)
		        .fechaEntrega(fechaEntrega2)
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
		repoRepartidor.save(r3);
		
		repoAsig.save(a);
		repoAsig.save(a2);
		repoEnvio.save(e3);
	}
}