package com.tfg_david.dam.City_Courier.model;

import com.tfg_david.dam.City_Courier.model.Trabajador.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class RRHH  extends Trabajador{

	
	//Se añade el rol por defecto antes de hacer el insert
	  @PrePersist 
	    public void asignarRol() {
	        this.setRol(Rol.RRHH);
	    }
	
	
	
	
}
