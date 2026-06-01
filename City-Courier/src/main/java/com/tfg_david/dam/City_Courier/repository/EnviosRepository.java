package com.tfg_david.dam.City_Courier.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Zona;


public interface EnviosRepository extends JpaRepository<Envio, Long> {
	
	
	List<Envio> findByFechaEntregaLimiteBetween(LocalDateTime incioDia, LocalDateTime finalDia);
	
	List<Envio> findByZona(Zona zona);
	
	List<Envio> findByAsignacionNull();
	

}
