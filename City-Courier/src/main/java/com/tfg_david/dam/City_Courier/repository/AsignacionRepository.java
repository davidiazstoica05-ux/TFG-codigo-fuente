package com.tfg_david.dam.City_Courier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.AsignacionPk;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Repartidor;

public interface AsignacionRepository extends JpaRepository<Asignacion, AsignacionPk> {

	List<Asignacion> findByEnvio_CodEnvioOrRepartidor_IdTrabajador(Long codEnvio, Long idTrabajador);
	
	
	Long countByEstadoPedido(boolean estado);
	
	List<Asignacion> findByRepartidor(Repartidor repartidor);
	
	List<Asignacion> findByEnvio(Envio envio);
	
}