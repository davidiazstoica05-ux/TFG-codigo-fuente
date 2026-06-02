package com.tfg_david.dam.City_Courier.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class Trabajador implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTrabajador;

	@NotBlank(message = "Los apellidos no pueden estar en blanco")
	@Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$", message = "Los apellidos solo pueden contener letras y espacios")
	private String apellidos;

	@NotBlank(message = "El nombre no puede estar en blanco")
	@Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$", message = "El nombre solo puede contener letras y espacios")
	private String nombre;

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email no tiene un formato válido")
	private String email;

	@NotBlank(message = "El telefono es obligatorio")
	@Pattern(regexp = "^[6-9]\\d{8}$", message = "El teléfono debe tener 9 dígitos y empezar por 6, 7, 8 o 9")
	private String telefono;

	@NotBlank(message = "El genero no puede estar en blanco")
	private String genero;

	@NotBlank
	@Column(unique = true)
	private String dni;

	private LocalDate fechaAlta;

	public enum Rol {
		RRHH, ADMIN, REPARTIDOR, LOGISTICA
	}

	private String passw;

	private String usuario;

	@Enumerated(EnumType.STRING) 
	private Rol rol;

	private boolean activo;

	// Metodos implementados

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

	    return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+rol.name()));
	}

	@Override
	public @Nullable String getPassword() {

		return passw;
	}

	@Override
	public String getUsername() {

		return usuario;
	}

	@Override
	public boolean isEnabled() {
		return this.activo; 
	}

}
