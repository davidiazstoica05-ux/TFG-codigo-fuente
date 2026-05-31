package com.tfg_david.dam.City_Courier.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.model.Ruta;

public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {
	
	Long countByEstado(Disponibilidad estado);
	
	List<Repartidor> findByRuta(Ruta ruta);
	
	Optional<Repartidor> findByDni(String dni);
	
	List<Repartidor> findByEstado(Disponibilidad estado);


	
}
