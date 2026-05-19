package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica")
public class LogisticaController {

	
	

	
	
	
	@GetMapping("/principal")
	public String prueba() {
		
		return "logistica/logistica";
		
	}
	
	
	
	
}
