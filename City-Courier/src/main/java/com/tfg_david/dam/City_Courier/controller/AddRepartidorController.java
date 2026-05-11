package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.repository.RepartidorRepository;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AddRepartidorController {

	private final RepartidorRepository repoRepartidor;
	
	@GetMapping("/RepartidorForm")
	public String showForm( Model model) {
		
		Repartidor repartidor = new Repartidor();
		model.addAttribute("repartidorAdd", repartidor);
		
		return "empleadoForm";
		
	}
	
	@PostMapping("/addRepartiodor")
	public Repartidor submit (@ModelAttribute("repartidorForm") Repartidor repartidor, Model model ) {
		
	model.addAttribute("repartidor", repartidor);
	
	return repoRepartidor.save(repartidor);
		
		
	}
	
	
}
