package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.service.EnviosService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica")
public class LogisticaController {

	private final EnviosService envioService; 

	@GetMapping("/principal")
	public String prueba( Model model) {
		
		model.addAttribute("envioList", envioService.findAll()); 
		

		return "logistica/logistica";

	}

}
