package com.tfg_david.dam.City_Courier.service;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;
import com.tfg_david.dam.City_Courier.service.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepartidorService extends BaseService<Repartidor, String, RepartidorRepository>{

	private final RepartidorRepository repartidorRepo; 
	
	
	
	
	
	
}
