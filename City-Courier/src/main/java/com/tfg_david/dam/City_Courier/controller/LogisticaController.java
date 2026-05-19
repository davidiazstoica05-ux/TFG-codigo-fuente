package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.model.Envio;
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
		
		model.addAttribute("envio", new Envio());

		return "logistica/logistica";

	}
	
	
	@PostMapping("/principal")
	public String submit (@ModelAttribute("envio") Envio envio, Model model) {
		
		envioService.save(envio);
		
		return "redirect:/logistica/principal";
		
	}

}
