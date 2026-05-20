package com.tfg_david.dam.City_Courier.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.service.EnviosService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica")
public class EnvioController {

	private final EnviosService envioService;

	@GetMapping("/principal")
	public String prueba(Model model) {

		model.addAttribute("envioList", envioService.findAll());

		model.addAttribute("envio", new Envio());
		
		return "logistica/logistica";

	}

	@PostMapping("/principal")
	public String submit(@ModelAttribute("envio")Envio envio, Model model) {
		
		Asignacion asigid; 
		
		if (envio.getCodEnvio() != null) {
			
			Optional<Envio> envioRecibido = envioService.findById(envio.getCodEnvio()); 
			
			
			if (envioRecibido.isPresent()) {
				
				asigid = envioRecibido.get().getAsignacion();
				
				envio.setAsignacion(asigid);

				
			}

		}
		
		envioService.save(envio);


		return "redirect:/logistica/principal";

	}
	
	@GetMapping("/editar/{codEnvio}")
	public String editarEnvio(@PathVariable("codEnvio") Long codEnvio, Model model) {
		
		Optional<Envio> envio = envioService.findById(codEnvio); 
		
		if (envio.isPresent()) {
			
			model.addAttribute("envio", envio.get()); 
			model.addAttribute("envioList", envioService.findAll());  
			model.addAttribute("modoEdicion",true); 
			return "logistica/logistica";
			
		}else {
			
			return "redirect:/logistica/principal";
		}
				
	}
	

	
	
	
}
