package com.tfg_david.dam.City_Courier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public class Logistica extends Trabajador {

    @PrePersist 
    public void asignarRol() {
        this.setRol(Rol.LOGISTICA);
    }
}
