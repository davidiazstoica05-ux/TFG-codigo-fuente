package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.service.AsignacionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/logistica")
public class AsignacionController {
	
	private final AsignacionService asigService; 

	@GetMapping("/asignaciones")
	public String asignacion( Model model) {
		
		model.addAttribute("asignacionList", asigService.findAll());
		
		
		return "logistica/asignaciones"; 
		
	}
	
	
	
	
}
