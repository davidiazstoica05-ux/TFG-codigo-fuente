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

	private final RepartidorService repartidorService;
	
	@GetMapping("/createRider")
	public String showForm( Model model) {
		

		model.addAttribute("repartidor", new Repartidor());
		
		return "riderFormTest";
		
	}
	
	@PostMapping("/createRider/submit")
	public String  submit (@ModelAttribute("repartidor") Repartidor repartidor, Model model ) {
		
	model.addAttribute("repartidor", repartidor);
	repartidorService.save(repartidor);
	
	return "redirect:/createRider";
		
		
	}
	
	@GetMapping("/showRiders")
	public String showListRiders(Model model) {
		
		model.addAttribute("repartidorList", repartidorService.findAll());  
		
		return "riderShowAll"; 
		
	}
	
	
}
