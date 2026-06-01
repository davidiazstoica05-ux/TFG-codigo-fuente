package com.tfg_david.dam.City_Courier.config;

import java.time.Duration;
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

		LocalDateTime ahora = LocalDateTime.now();

		/* =====================================================================================
		 * 1. CREACIÓN DE RUTAS (Estrictamente 2 puntos por ruta)
		 * ===================================================================================== */
		
		Map<String, Double> pRuta1 = new LinkedHashMap<>();
		pRuta1.put("Utrera", 35.0);
		pRuta1.put("Arahal", 55.0);

		Map<String, Double> pRuta2 = new LinkedHashMap<>();
		pRuta2.put("Carmona", 30.0);
		pRuta2.put("Marchena", 38.0);

		Map<String, Double> pRuta3 = new LinkedHashMap<>();
		pRuta3.put("Osuna", 40.0);
		pRuta3.put("Morón de la Frontera", 60.0);

		Map<String, Double> pRuta4 = new LinkedHashMap<>();
		pRuta4.put("Écija", 65.0);
		pRuta4.put("Fuentes de Andalucía", 72.0);

		Map<String, Double> pRuta5 = new LinkedHashMap<>();
		pRuta5.put("La Lantejuela", 25.0);
		pRuta5.put("Paradas", 45.0);

		Ruta r1 = Ruta.builder()
				.nombreRuta("Ruta Sur: Arahal-Utrera")
				.puntosEntregas(pRuta1)
				.fechaInicio(LocalTime.of(8, 0))
				.fechaFinal(LocalTime.of(16, 0))
				.build();
				
		Ruta r2 = Ruta.builder()
				.nombreRuta("Ruta Campiña: Carmona-Marchena")
				.puntosEntregas(pRuta2)
				.fechaInicio(LocalTime.of(9, 0))
				.fechaFinal(LocalTime.of(17, 0))
				.build();
				
		Ruta r3 = Ruta.builder()
				.nombreRuta("Ruta Sierra: Morón-Osuna")
				.puntosEntregas(pRuta3)
				.fechaInicio(LocalTime.of(7, 30))
				.fechaFinal(LocalTime.of(15, 30))
				.build();
				
		Ruta r4 = Ruta.builder()
				.nombreRuta("Ruta Este: Fuentes-Écija")
				.puntosEntregas(pRuta4)
				.fechaInicio(LocalTime.of(8, 0))
				.fechaFinal(LocalTime.of(14, 0))
				.build();
				
		Ruta r5 = Ruta.builder()
				.nombreRuta("Ruta Centro: Lantejuela-Paradas")
				.puntosEntregas(pRuta5)
				.fechaInicio(LocalTime.of(10, 0))
				.fechaFinal(LocalTime.of(18, 0))
				.build();

		repoRuta.save(r1); 
		repoRuta.save(r2); 
		repoRuta.save(r3); 
		repoRuta.save(r4); 
		repoRuta.save(r5);

		/* =====================================================================================
		 * 2. CREACIÓN DE OPERADORES DEL SISTEMA
		 * ===================================================================================== */
		
		Admin admin = Admin.builder()
				.nombre("Alejandro")
				.apellidos("Ruiz")
				.dni("45678123A")
				.email("admin@citycourier.com")
				.telefono("655112233")
				.genero("Hombre")
				.activo(true)
				.fechaAlta(LocalDate.now().minusYears(3))
				.usuario("admin")
				.passw(passwordEncoder.encode("admin"))
				.build();

		RRHH rrhh = RRHH.builder()
				.nombre("Carmen")
				.apellidos("Velasco")
				.dni("23456789B")
				.email("rrhh@citycourier.com")
				.telefono("644998877")
				.genero("Mujer")
				.activo(true)
				.fechaAlta(LocalDate.now().minusYears(2))
				.usuario("rrhh")
				.passw(passwordEncoder.encode("rrhh"))
				.build();

		Logistica logistica = Logistica.builder()
				.nombre("Javier")
				.apellidos("García")
				.dni("34567890C")
				.email("logistica@citycourier.com")
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

		/* =====================================================================================
		 * 3. CREACIÓN DE REPARTIDORES
		 * ===================================================================================== */
		
		// Repartidores ASIGNADOS a una ruta (Para tener datos base)
		Repartidor rep1 = Repartidor.builder()
				.nombre("David")
				.apellidos("Díaz")
				.dni("31031909X")
				.email("daviddiaz@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(5))
				.genero("Hombre")
				.telefono("697386581")
				.activo(true)
				.cargaMax(120.5)
				.zona(Zona.UTRERA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
				.ruta(r1)
				.build();

		Repartidor rep2 = Repartidor.builder()
				.nombre("Claudia")
				.apellidos("Piñero")
				.dni("22997473A")
				.email("claudia@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(5))
				.genero("Mujer")
				.telefono("611223344")
				.activo(true)
				.cargaMax(30.0)
				.zona(Zona.CARMONA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
				.ruta(r2)
				.build();

		Repartidor rep3 = Repartidor.builder()
				.nombre("Antonio")
				.apellidos("Vargas")
				.dni("52369841C")
				.email("avargas@gmail.com")
				.fechaAlta(LocalDate.now().minusYears(2))
				.genero("Hombre")
				.telefono("666112233")
				.activo(true)
				.cargaMax(150.0)
				.zona(Zona.OSUNA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
				.ruta(r3)
				.build();
		
		

		// Repartidores SUELTOS (Sin ruta asignada) para pruebas del Panel
		Repartidor repSuelto1 = Repartidor.builder()
				.nombre("Lucía")
				.apellidos("Fernández")
				.dni("39274561D")
				.email("lfernandez@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(3))
				.genero("Mujer")
				.telefono("677334455")
				.activo(true)
				.cargaMax(35.0)
				.zona(Zona.ARAHAL)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
				.build();

		Repartidor repSuelto2 = Repartidor.builder()
				.nombre("Jorge")
				.apellidos("Mellado")
				.dni("75341892F")
				.email("jmellado@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(1))
				.genero("Hombre")
				.telefono("699102030")
				.activo(true)
				.cargaMax(140.0)
				.zona(Zona.ECIJA)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.Furgoneta)
				.build();

		Repartidor repSuelto3 = Repartidor.builder()
				.nombre("Oana Simona")
				.apellidos("Stoica")
				.dni("12345678P")
				.email("lfernandez@gmail.com")
				.fechaAlta(LocalDate.now().minusMonths(3))
				.genero("Mujer")
				.telefono("677334455")
				.activo(true)
				.cargaMax(35.0)
				.zona(Zona.ARAHAL)
				.estado(Disponibilidad.DISPONIBLE)
				.vehiculo(com.tfg_david.dam.City_Courier.model.TipoVehiculo.moto_ecologica)
				.build();
		
		
		repoRepartidor.save(rep1); 
		repoRepartidor.save(rep2); 
		repoRepartidor.save(rep3);
		repoRepartidor.save(repSuelto1); 
		repoRepartidor.save(repSuelto2);
		repoRepartidor.save(repSuelto3);

		/* =====================================================================================
		 * 4. CREACIÓN DE ENVÍOS
		 * ===================================================================================== */
		
		// Envíos ASIGNADOS (Conectados a un repartidor y asignación)
		Envio e1 = Envio.builder()
				.destinatario("Cliente Utrera")
				.direccion("Calle Utrera 1")
				.codPostal("41710")
				.zona(Zona.UTRERA)
				.peso(5.0)
				.prioridad(PrioridadEnvio.URGENTE)
				.fechaEntregaLimite(ahora.plusDays(1).withHour(10))
				.build();
				
		Envio e2 = Envio.builder()
				.destinatario("Cliente Carmona")
				.direccion("Calle Carmona 2")
				.codPostal("41410")
				.zona(Zona.CARMONA)
				.peso(1.2)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(ahora.plusDays(1).withHour(12))
				.build();
				
		Envio e3 = Envio.builder()
				.destinatario("Cliente Osuna")
				.direccion("Calle Osuna 3")
				.codPostal("41640")
				.zona(Zona.OSUNA)
				.peso(15.5)
				.prioridad(PrioridadEnvio.AHORRO)
				.fechaEntregaLimite(ahora.plusDays(2).withHour(9))
				.build();
		
		// Envíos SUELTOS (Pendientes para probar Auto-Asignación o Panel)
		Envio eSuelto1 = Envio.builder()
				.destinatario("María López")
				.direccion("Av. Arahal 5")
				.codPostal("41600")
				.zona(Zona.ARAHAL)
				.peso(2.5)
				.prioridad(PrioridadEnvio.URGENTE)
				.fechaEntregaLimite(ahora.plusDays(1).withHour(18))
				.build();
				
		Envio eSuelto2 = Envio.builder()
				.destinatario("Carlos Ruiz")
				.direccion("Plaza Marchena 6")
				.codPostal("41620")
				.zona(Zona.MARCHENA)
				.peso(12.0)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(ahora.plusDays(3).withHour(11))
				.build();
				
		Envio eSuelto3 = Envio.builder()
				.destinatario("Ana Gómez")
				.direccion("Calle Écija 9")
				.codPostal("41400")
				.zona(Zona.ECIJA)
				.peso(8.5)
				.prioridad(PrioridadEnvio.URGENTE)
				.fechaEntregaLimite(ahora.plusDays(2).withHour(14))
				.build();
				
		Envio eSuelto4 = Envio.builder()
				.destinatario("Pedro Sánchez")
				.direccion("Polígono Fuentes 2")
				.codPostal("41420")
				.zona(Zona.FUENTES_DE_ANDALUCIA)
				.peso(45.0)
				.prioridad(PrioridadEnvio.AHORRO)
				.fechaEntregaLimite(ahora.plusDays(4).withHour(10))
				.build();
				
		Envio eSuelto5 = Envio.builder()
				.destinatario("Luis Martínez")
				.direccion("Calle Paradas 7")
				.codPostal("41610")
				.zona(Zona.PARADAS)
				.peso(3.0)
				.prioridad(PrioridadEnvio.NORMAL)
				.fechaEntregaLimite(ahora.plusDays(3).withHour(16))
				.build();

		repoEnvio.save(e1); 
		repoEnvio.save(e2); 
		repoEnvio.save(e3);
		repoEnvio.save(eSuelto1); 
		repoEnvio.save(eSuelto2); 
		repoEnvio.save(eSuelto3); 
		repoEnvio.save(eSuelto4); 
		repoEnvio.save(eSuelto5);

		/* =====================================================================================
		 * 5. CREACIÓN DE ASIGNACIONES (Solo para los envíos iniciales)
		 * ===================================================================================== */
		
		Asignacion a1 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(ahora.minusHours(2))
				.fechaEntrega(ahora.plusDays(1).withHour(10))
				.tiempoEstimado(Duration.ofMinutes(45))
				.build();

		Asignacion a2 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(ahora.minusHours(1))
				.fechaEntrega(ahora.plusDays(1).withHour(12))
				.tiempoEstimado(Duration.ofMinutes(30))
				.build();

		Asignacion a3 = Asignacion.builder()
				.estadoPedido(false)
				.fechaAsignacion(ahora.minusMinutes(30))
				.fechaEntrega(ahora.plusDays(2).withHour(9))
				.tiempoEstimado(Duration.ofMinutes(60))
				.build();

		// Vinculamos Asignaciones con Envíos y Repartidores (Relaciones bidireccionales)
		a1.vincularEnvio(e1);
		rep1.addAsignacion(a1);

		a2.vincularEnvio(e2);
		rep2.addAsignacion(a2);

		a3.vincularEnvio(e3);
		rep3.addAsignacion(a3);

		// Guardamos las asignaciones generadas
		repoAsig.save(a1);
		repoAsig.save(a2);
		repoAsig.save(a3);
	}
}