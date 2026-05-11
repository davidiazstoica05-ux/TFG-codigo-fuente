package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.service.RepartidorService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class RepartidorController {

	private final RepartidorService repoService;
	
	@GetMapping("/createRider")
	public String showForm( Model model) {
		

		model.addAttribute("repartidor", new Repartidor());
		
		return "riderForm";
		
	}
	
	@PostMapping("/createRider/submit")
	public String  submit (@ModelAttribute("repartidorForm") Repartidor repartidor, Model model ) {
		
	model.addAttribute("repartidor", repartidor);
	repoService.create(repartidor);
	return "redirect:/createRider";
		
		
	}
	
	
}
