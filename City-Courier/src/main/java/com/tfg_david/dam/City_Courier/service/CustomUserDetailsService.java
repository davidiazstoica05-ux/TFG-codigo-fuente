package com.tfg_david.dam.City_Courier.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sun.security.auth.UserPrincipal;
import com.tfg_david.dam.City_Courier.model.Trabajador;
import com.tfg_david.dam.City_Courier.repository.TrabajadorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	private final TrabajadorRepository repoTrabajador; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Trabajador> trabajador; 
		
		trabajador = repoTrabajador.findByUsuario(username);
		
		if (trabajador.isPresent()) {
			
			
			return trabajador.get();
			
		}
		
		throw new UsernameNotFoundException("No se ha encontrado usuario");
	}

	
	
	
}
