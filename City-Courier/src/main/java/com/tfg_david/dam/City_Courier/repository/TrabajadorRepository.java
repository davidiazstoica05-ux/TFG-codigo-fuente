package com.tfg_david.dam.City_Courier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long>  {

	
	
	Optional<Trabajador> findByUsuario(String usuario);
	
	
}
