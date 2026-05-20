package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.service.RutaService;

import lombok.RequiredArgsConstructor;

@Controller 
@RequiredArgsConstructor
@RequestMapping("/logistica")
public class RutaController {
	
	private final RutaService rutaService; 
	
	@GetMapping("/rutas")
	public String logisticaRuta(Long codigoRuta, Model model) {
		
		
		model.addAttribute("rutaList", rutaService.findAll());
		
		model.addAttribute("ruta", new Ruta());
		
		return "logistica/rutas"; 
	}
	
	@PostMapping("/rutas")
	public String submit(@ModelAttribute("ruta") Ruta ruta, Model model) {
		
		rutaService.save(ruta);
		
		return "redirect:/logistica/rutas";
		
	}
	
	
	

}
