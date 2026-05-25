package com.tfg_david.dam.City_Courier.service;


import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.repository.EnviosRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnviosService extends BaseService<Envio, Long, EnviosRepository> {

	
	public Long countEnvios() {
		
		return repo.count();
		
	}
	

	
	
}
