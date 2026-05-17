package com.tfg_david.dam.City_Courier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.service.RepartidorService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/rrhh") 
public class RepartidorController {

    private final RepartidorService repartidorService;
	
    @GetMapping("/repartidores")
    public String rrhh(Model model) {
        model.addAttribute("repartidorList", repartidorService.findAll());
        
        model.addAttribute("repartidor", new Repartidor());
        
        return "rrhh"; 
    }

	
	
	
	
	@PostMapping("/createRider/submit")
    public String submit (@ModelAttribute("repartidor") Repartidor repartidor, Model model ) {
        repartidorService.save(repartidor);
        return "redirect:/rrhh/repartidores";
    }
	
	

	
	
}
