package com.tfg_david.dam.City_Courier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;

public interface RepartidorRepository extends JpaRepository<Repartidor, String> {
	
	Long countByEstado(Disponibilidad estado);
	
	List<Repartidor> findByRuta(Ruta ruta);


	
}
