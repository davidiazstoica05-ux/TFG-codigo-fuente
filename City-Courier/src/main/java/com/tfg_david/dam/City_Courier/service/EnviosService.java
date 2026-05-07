package com.tfg_david.dam.City_Courier.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnviosService {

	private final EnviosRepository repo; 
	
	public List<Envio> getLista(){
	
		return repo.findAll();
		
	}
	
	
}
