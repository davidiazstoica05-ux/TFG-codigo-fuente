package com.tfg_david.dam.City_Courier.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.service.EnviosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica/envios")
public class EnvioController {

	private final EnviosService envioService;

	@GetMapping
	public String logisticaEnvio(@RequestParam(required = false) Long codEnvio, Model model) {

		List<Envio> e = new ArrayList<>();

		if (codEnvio != null && codEnvio != 0) {
			Optional<Envio> envio = envioService.findById(codEnvio);
			if (envio.isPresent()) {
				e.add(envio.get());
				model.addAttribute("envioList", e);
			} else {
				return "redirect:/logistica/envios";
			}
		} else {
			model.addAttribute("envioList", envioService.findAll());
		}

		return "logistica/envios";
	}

	@GetMapping("/nuevo")
	public String nuevoEnvio(Model model) {
		
		model.addAttribute("envio", new Envio());
		
		return "logistica/forms/envio-form";
	}

	@PostMapping
	public String submit( @Valid @ModelAttribute("envio") Envio envio, BindingResult bindingResult,  Model model) {

		Asignacion asigid;

		
		if (bindingResult.hasErrors()) {
			
			return "logistica/forms/envio-form";
			
		}
		
		if (envio.getCodEnvio() != null) {
			Optional<Envio> envioRecibido = envioService.findById(envio.getCodEnvio());
			if (envioRecibido.isPresent()) {
				asigid = envioRecibido.get().getAsignacion();
				envio.setAsignacion(asigid);
			}
		}

		envioService.save(envio);

		return "redirect:/logistica/envios";
	}
	
	

	//Editar y borrar
	
	@GetMapping("/borrar/{codEnvio}")
	public String borrarEnvio(@PathVariable("codEnvio") Long codEnvio) {
		
		
		envioService.deleteEnvio(codEnvio); 
		
		return "redirect:/logistica/envios";

		
		
	}
	
	
	@GetMapping("/editar/{codEnvio}")
	public String editarEnvio(@PathVariable("codEnvio") Long codEnvio, Model model) {

		Optional<Envio> envio = envioService.findById(codEnvio);

		
		
		if (envio.isPresent()) {
			model.addAttribute("envio", envio.get());
			
			return "logistica/forms/envio-form";
		} else {
			return "redirect:/logistica/envios";
		}
	}
}