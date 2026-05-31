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
import com.tfg_david.dam.City_Courier.model.PrioridadEnvio;
import com.tfg_david.dam.City_Courier.model.RRHH;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.model.Zona;
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

		LocalDateTime ahora, fechaEntrega1, fechaEntrega2, fechaEntrega3, fechaEntrega4, 
					  fechaEntrega5, fechaEntrega6, fechaEntrega7, fechaEntrega8, 
					  fechaEntrega9, fechaEntrega10, fechaEntrega11, fechaEntrega12, fechaAsignacion;

		Map<String, Double> paradasRuta1, paradasRuta2, paradasRuta3, paradasRuta4, paradasRuta5, 
							paradasRuta6, paradasRuta7, paradasRuta8, paradasRuta9, paradasRuta10;

		Ruta ruta1, ruta2, ruta3, ruta4, ruta5, ruta6, ruta7, ruta8, ruta9, ruta10;

		Repartidor r1, r2, r3, r4, r5, r6, r7, r8;

		Admin admin;
		RRHH rrhh;
		Logistica logistica;

		Envio e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12;

		Asignacion a1, a2, a3, a4, a5;

		ahora = LocalDateTime.now();

		fechaEntrega1  = ahora.plusDays(1).withHour(9).withMinute(0);
		fechaEntrega2  = ahora.plusDays(1).withHour(11).withMinute(30);
		fechaEntrega3  = ahora.plusDays(1).withHour(14).withMinute(0);
		fechaEntrega4  = ahora.plusDays(2).withHour(9).withMinute(45);
		fechaEntrega5  = ahora.plusDays(2).withHour(12).withMinute(0);
		fechaEntrega6  = ahora.plusDays(2).withHour(15).withMinute(30);
		fechaEntrega7  = ahora.plusDays(3).withHour(10).withMinute(0);
		fechaEntrega8  = ahora.plusDays(3).withHour(13).withMinute(15);
		fechaEntrega9  = ahora.plusDays(4).withHour(9).withMinute(0);
		fechaEntrega10 = ahora.plusDays(4).withHour(11).withMinute(0);
		fechaEntrega11 = ahora.plusDays(5).withHour(10).withMinute(30);
		fechaEntrega12 = ahora.plusDays(5).withHour(16).withMinute(0);

		fechaAsignacion = ahora.minusHours(3);

		paradasRuta1 = new LinkedHashMap<>();
		paradasRuta1.put("Lantejuela", 20.0);
		paradasRuta1.put("Marchena", 38.0);

		paradasRuta2 = new LinkedHashMap<>();
		paradasRuta2.put("Osuna", 40.0);
		paradasRuta2.put("Arahal", 55.0);

		paradasRuta3 = new LinkedHashMap<>();
		paradasRuta3.put("Écija", 65.0);
		paradasRuta3.put("Fuentes de Andalucía", 72.0);
		paradasRuta3.put("La Campana", 80.0);

		paradasRuta4 = new LinkedHashMap<>();
		paradasRuta4.put("Carmona", 30.0);
		paradasRuta4.put("Mairena del Alcor", 22.0);

		paradasRuta5 = new LinkedHashMap<>();
		paradasRuta5.put("Utrera", 35.0);
		paradasRuta5.put("Los Palacios y Villafranca", 28.0);

		paradasRuta6 = new LinkedHashMap<>();
		paradasRuta6.put("Estepa", 90.0);
		paradasRuta6.put("Herrera", 85.0);
		paradasRuta6.put("Pedrera", 78.0);

		paradasRuta7 = new LinkedHashMap<>();
		paradasRuta7.put("Morón de la Frontera", 60.0);
		paradasRuta7.put("El Saucejo", 75.0);

		paradasRuta8 = new LinkedHashMap<>();
		paradasRuta8.put("Dos Hermanas", 15.0);
		paradasRuta8.put("Alcalá de Guadaíra", 18.0);

		paradasRuta9 = new LinkedHashMap<>();
		paradasRuta9.put("Lebrija", 50.0);
		paradasRuta9.put("Las Cabezas de San Juan", 42.0);

		paradasRuta10 = new LinkedHashMap<>();
		paradasRuta10.put("Sevilla Centro", 5.0);
		paradasRuta10.put("Sevilla Este", 8.0);
		paradasRuta10.put("Triana", 6.0);

		ruta1 = Ruta.builder()
				.nombreRuta("Lantejuela-Marchena")
				.puntosEntregas(paradasRuta1)
				.fechaInicio(LocalTime.of(8, 0))
				.fechaFinal(LocalTime.of(16, 0))
				.build();

		ruta2 = Ruta.builder()
				.nombreRuta("Arahal-Osuna")
				.puntosEntregas(paradasRuta2)
				.fechaInicio(LocalTime.of(8, 0))
				.fechaFinal(LocalTime.of(16, 0))
				.build();

		ruta3 = Ruta.builder()
				.nombreRuta("Écija-Campana")
				.puntosEntregas(paradasRuta3)
				.fechaInicio(LocalTime.of(7, 30))
				.fechaFinal(LocalTime.of(15, 30))
				.build();

		ruta4 = Ruta.builder()
				.nombreRuta("Carmona-Mairena")
				.puntosEntregas(paradasRuta4)
				.fechaInicio(LocalTime.of(8, 0))
				.fechaFinal(LocalTime.of(14, 0))
				.build();

		ruta5 = Ruta.builder()
				.nombreRuta("Utrera-Los Palacios")
				.puntosEntregas(paradasRuta5)
				.fechaInicio(LocalTime.of(9, 0))
				.fechaFinal(LocalTime.of(17, 0))
				.build();

		ruta6 = Ruta.builder()
				.nombreRuta("Estepa-Pedrera")
				.puntosEntregas(paradasRuta6)
				.fechaInicio(LocalTime.of(7, 0))
				.fechaFinal(LocalTime.of(15, 0))
				.build();

		ruta7 = Ruta.builder()
				.nombreRuta("Morón-El Saucejo")
				.puntosEntregas(paradasRuta7)
				.fechaInicio(LocalTime.of(8, 30))
				.fechaFinal(LocalTime.of(16, 30))
				.build();

		ruta8 = Ruta.builder()
				.nombreRuta("Dos Hermanas-Alcalá")
				.puntosEntregas(paradasRuta8)
				.fechaInicio(LocalTime.of(8, 0))
				.fechaFinal(LocalTime.of(14, 0))
				.build();

		ruta9 = Ruta.builder()
				.nombreRuta("Lebrija-Las Cabezas")
				.puntosEntregas(paradasRuta9)
				.fechaInicio(LocalTime.of(9, 0))
				.fechaFinal(LocalTime.of(17, 0))
				.build();

		ruta10 = Ruta.builder()
				.nombreRuta("Sevilla Urbana")
				.puntosEntregas(paradasRuta10)
				.fechaInicio(LocalTime.of(8, 0))
				.fechaFinal(LocalTime.of(20, 0))
				.build();

		repoRuta.save(ruta1);
		repoRuta.save(ruta2);
		repoRuta.save(ruta3);
		repoRuta.save(ruta4);
		repoRuta.save(ruta5);
		repoRuta.save(ruta6);
		repoRuta.save(ruta7);
		repoRuta.save(ruta8);
		repoRuta.save(ruta9);
		repoRuta.save(ruta10);

		admin = Admin.builder()
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

		rrhh = RRHH.builder()
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

		logistica = Logistica.builder()
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

		trabajdorRepo.save(admin);
		trabajdorRepo.save(rrhh);
		trabajdorRepo.save(logistica);

		r1 = Repartidor.builder()
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
				.zona(Zona.OSUNA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
				.ruta(ruta2)
				.build();

		r2 = Repartidor.builder()
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
				.zona(Zona.OSUNA)
				.estado(Disponibilidad.VACACIONES)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
				.ruta(ruta2)
				.build();

		r3 = Repartidor.builder()
				.nombre("Claudia")
				.apellidos("Piñero Pineda")
				.dni("22997473A")
				.email("claudia@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(5))
				.genero("Mujer")
				.telefono("611223344")
				.activo(true)
				.usuario("claudia")
				.passw(passwordEncoder.encode("claudia"))
				.cargaMax(8.0)
				.zona(Zona.MARCHENA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
				.ruta(ruta1)
				.build();

		r4 = Repartidor.builder()
				.nombre("Sofía")
				.apellidos("Romero Castillo")
				.dni("47812635B")
				.email("sromero@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(10))
				.genero("Mujer")
				.telefono("655778899")
				.activo(true)
				.usuario("sofia")
				.passw(passwordEncoder.encode("sofia123"))
				.cargaMax(6.0)
				.zona(Zona.CARMONA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
				.ruta(ruta4)
				.build();

		r5 = Repartidor.builder()
				.nombre("Antonio")
				.apellidos("Vargas Leal")
				.dni("52369841C")
				.email("avargas@gmail.com")
				.fechaAlta(LocalDate.now().minusYears(2))
				.genero("Hombre")
				.telefono("666112233")
				.activo(true)
				.usuario("antonio")
				.passw(passwordEncoder.encode("antonio123"))
				.cargaMax(12.0)
				.zona(Zona.ECIJA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
				.ruta(ruta3)
				.build();

		r6 = Repartidor.builder()
				.nombre("Lucía")
				.apellidos("Fernández Mora")
				.dni("39274561D")
				.email("lfernandez@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(3))
				.genero("Mujer")
				.telefono("677334455")
				.activo(true)
				.usuario("lucia")
				.passw(passwordEncoder.encode("lucia123"))
				.cargaMax(5.0)
				.zona(Zona.PARADAS)
				.estado(Disponibilidad.DE_BAJA)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
				.build();

		r7 = Repartidor.builder()
				.nombre("Carlos")
				.apellidos("Herrera Santos")
				.dni("61423857E")
				.email("cherrera@gmail.com")
				.fechaAlta(LocalDate.now().minusYears(3))
				.genero("Hombre")
				.telefono("688556677")
				.activo(true)
				.usuario("carlos")
				.passw(passwordEncoder.encode("carlos123"))
				.cargaMax(15.0)
				.zona(Zona.UTRERA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
				.ruta(ruta5)
				.build();

		r8 = Repartidor.builder()
				.nombre("Jorge")
				.apellidos("Mellado Fuentes")
				.dni("75341892F")
				.email("jmellado@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(1))
				.genero("Hombre")
				.telefono("699102030")
				.activo(true)
				.usuario("jorge")
				.passw(passwordEncoder.encode("jorge123"))
				.cargaMax(9.0)
				.zona(Zona.LA_LANTEJUELA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
				.build();

		repoRepartidor.save(r1);
		repoRepartidor.save(r2);
		repoRepartidor.save(r3);
		repoRepartidor.save(r4);
		repoRepartidor.save(r5);
		repoRepartidor.save(r6);
		repoRepartidor.save(r7);
		repoRepartidor.save(r8);

		e1 = Envio.builder()
				.destinatario("Manuel Díaz")
				.direccion("Plaza Juan de Mesa, 4, Bajo C")
				.codPostal("41640")
				.zona("Osuna")
				.peso(1.7)
				.prioridad(PrioridadEnvio.URGENTE)
				.fechaEntregaLimite(fechaEntrega1)
				.build();

		e2 = Envio.builder()
				.destinatario("Laura Gómez")
				.direccion("Calle Corredera, 45, 1ºA")
				.codPostal("41640")
				.zona("Osuna")
				.peso(3.2)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(fechaEntrega2)
				.build();

		e3 = Envio.builder()
				.destinatario("Fernando Ruiz")
				.direccion("Avenida de la Constitución, 12, 3ºB")
				.codPostal("41300")
				.zona("Carmona")
				.peso(2.5)
				.prioridad(PrioridadEnvio.AHORRO)
				.fechaEntregaLimite(fechaEntrega3)
				.build();

		e4 = Envio.builder()
				.destinatario("Patricia Morales")
				.direccion("Calle Real, 8, 2ºA")
				.codPostal("41400")
				.zona("Écija")
				.peso(4.8)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(fechaEntrega4)
				.build();

		e5 = Envio.builder()
				.destinatario("Jesús Navarro")
				.direccion("Calle Larga, 33, Bajo D")
				.codPostal("41710")
				.zona("Utrera")
				.peso(6.1)
				.prioridad(PrioridadEnvio.URGENTE)
				.fechaEntregaLimite(fechaEntrega5)
				.build();

		e6 = Envio.builder()
				.destinatario("Elena Torres")
				.direccion("Calle Betis, 52, 2º Izquierda")
				.codPostal("41010")
				.zona("Sevilla Centro")
				.peso(0.8)
				.prioridad(PrioridadEnvio.AHORRO)
				.fechaEntregaLimite(fechaEntrega6)
				.build();

		e7 = Envio.builder()
				.destinatario("Raúl Jiménez")
				.direccion("Avenida de Europa, 7, 1ºC")
				.codPostal("41700")
				.zona("Dos Hermanas")
				.peso(2.0)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(fechaEntrega7)
				.build();

		e8 = Envio.builder()
				.destinatario("Isabel Castillo")
				.direccion("Calle Granada, 21, Entresuelo")
				.codPostal("41500")
				.zona("Marchena")
				.peso(1.3)
				.prioridad(PrioridadEnvio.URGENTE)
				.fechaEntregaLimite(fechaEntrega8)
				.build();

		e9 = Envio.builder()
				.destinatario("Pablo Serrano")
				.direccion("Calle Nueva, 3, Bajo B")
				.codPostal("41200")
				.zona("Alcalá de Guadaíra")
				.peso(5.5)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(fechaEntrega9)
				.build();

		e10 = Envio.builder()
				.destinatario("María Luisa Herrera")
				.direccion("Plaza España, 1, 4ºA")
				.codPostal("41740")
				.zona("Lebrija")
				.peso(3.7)
				.prioridad(PrioridadEnvio.AHORRO)
				.fechaEntregaLimite(fechaEntrega10)
				.build();

		e11 = Envio.builder()
				.destinatario("Tomás Aguilar")
				.direccion("Calle Feria, 17, 1ºD")
				.codPostal("41003")
				.zona("Sevilla Centro")
				.peso(0.5)
				.prioridad(PrioridadEnvio.URGENTE)
				.fechaEntregaLimite(fechaEntrega11)
				.build();

		e12 = Envio.builder()
				.destinatario("Nuria Blanco")
				.direccion("Avenida Andalucía, 89, 3ºC")
				.codPostal("41600")
				.zona("Arahal")
				.peso(4.2)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(fechaEntrega12)
				.build();

		repoEnvio.save(e1);
		repoEnvio.save(e2);
		repoEnvio.save(e3);
		repoEnvio.save(e4);
		repoEnvio.save(e5);
		repoEnvio.save(e6);
		repoEnvio.save(e7);
		repoEnvio.save(e8);
		repoEnvio.save(e9);
		repoEnvio.save(e10);
		repoEnvio.save(e11);
		repoEnvio.save(e12);

		a1 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(fechaAsignacion)
				.fechaEntrega(fechaEntrega1)
				.build();

		a2 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(fechaAsignacion)
				.fechaEntrega(fechaEntrega2)
				.build();

		a3 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(fechaAsignacion)
				.fechaEntrega(fechaEntrega3)
				.build();

		a4 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(fechaAsignacion)
				.fechaEntrega(fechaEntrega4)
				.build();

		a5 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(fechaAsignacion)
				.fechaEntrega(fechaEntrega5)
				.build();

		a1.vincularEnvio(e1);
		r1.addAsignacion(a1);

		a2.vincularEnvio(e2);
		r2.addAsignacion(a2);

		a3.vincularEnvio(e3);
		r4.addAsignacion(a3);

		a4.vincularEnvio(e4);
		r5.addAsignacion(a4);

		a5.vincularEnvio(e5);
		r7.addAsignacion(a5);

		repoAsig.save(a1);
		repoAsig.save(a2);
		repoAsig.save(a3);
		repoAsig.save(a4);
		repoAsig.save(a5);
	}
}