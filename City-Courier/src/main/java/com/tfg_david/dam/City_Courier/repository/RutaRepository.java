package com.tfg_david.dam.City_Courier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Ruta;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

	List<Ruta> findByNombreRutaOrCodigoRuta(String nombreRuta, Long codigoRuta);

}
