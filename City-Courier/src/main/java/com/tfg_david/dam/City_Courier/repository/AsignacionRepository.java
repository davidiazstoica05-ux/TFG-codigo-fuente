package com.tfg_david.dam.City_Courier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg_david.dam.City_Courier.model.Asignacion;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

	List<Asignacion> findByIdAsignacionOrRepartidorDni( Long idAsignacion, String dni);

}
