package com.tfg_david.dam.City_Courier.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;

public interface EnviosRepository extends JpaRepository<Envio, Long> {
	
	
	List<Envio> findByFechaEntregaLimite(LocalDateTime fecha);
	
	List<Envio> findByAsignacionNull();
	

}
