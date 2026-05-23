package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;
import com.tfg_david.dam.City_Courier.service.RepartidorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/logistica")
public class AsignacionController {

	private final AsignacionService asigService;
	private final EnviosService envioService; 
	private final RepartidorService repartidorService;

	@GetMapping("/asignaciones")
	public String asignacion(Model model) {
		

		model.addAttribute("asignacionList", asigService.findAll());

		model.addAttribute("asignacion", new Asignacion());
		
		model.addAttribute("repartidoresList", repartidorService.findAll());

		model.addAttribute("enviosList",envioService.findAll());

		
		return "logistica/asignaciones";

	}

	@PostMapping("/asignaciones")
	public String asignaciones(@ModelAttribute("asignacion") Asignacion asignacion, Model model) {

		if (asigService.asignarRepartidor(asignacion) && asigService.asignarEnvio(asignacion)) {
						
			
			asigService.save(asignacion);

		}

		return "redirect:/logistica/asignaciones";

	}

}
