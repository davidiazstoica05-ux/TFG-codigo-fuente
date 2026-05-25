package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica")
public class LogisticaController {
	
	
	private final EnviosService envioService;
	private final AsignacionService asigService;
	
	
	@GetMapping("/logisticaHome")
	public String home(Model model) {

		model.addAttribute("totalEnvios", envioService.countEnvios());

		model.addAttribute("enviosPendientes", asigService.countByEstadoPedido(false));

		model.addAttribute("enviosEntregados", asigService.countByEstadoPedido(true));
		
		return "logistica/logisticaHome";

	}

}
