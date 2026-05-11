package com.tfg_david.dam.City_Courier.service;

import org.springframework.stereotype.Service;

import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepartidorService {

	private final RepartidorRepository repartidorRepo; 
	
	public void create(Repartidor repartidor) {
		
		 repartidorRepo.save(repartidor);
						  
		
	}
	
	
}
