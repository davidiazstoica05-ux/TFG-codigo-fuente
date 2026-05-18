package com.tfg_david.dam.City_Courier.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.service.RepartidorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rrhh")
public class RepartidorController {

	private final RepartidorService repartidorService;

	@GetMapping("/repartidores")
	public String rrhh(@RequestParam(required = false) String dni, Model model) {

		if (dni != null && !dni.isEmpty() ) {

			Optional<Repartidor> repartidor = repartidorService.findById(dni);

			if (repartidor.isPresent()) {
				
				Set<Repartidor> r = new HashSet<>();
				r.add(repartidor.get()); 

				model.addAttribute("repartidorList", r);
				

			} else {

				return "redirect: /rrhh/repartidores";

			}

		} else {

			model.addAttribute("repartidorList", repartidorService.findAll());


		}
		
		
		model.addAttribute("repartidor", new Repartidor());
		
		return "rrhh";


	}

	@GetMapping("/editar/{dni}")
	public String editarRepartidor(@PathVariable("dni") String dni, Model model) {

		Optional<Repartidor> repartidor = repartidorService.findById(dni);

		if (repartidor.isPresent()) {

			model.addAttribute("repartidor", repartidor.get());

			model.addAttribute("repartidorList", repartidorService.findAll());

			model.addAttribute("modoEdicion", true);

			return "rrhh";

		} else {

			return "redirect: /rrhh/repartidores";

		}
	}



	@PostMapping("/repartidores")
	public String submit(@ModelAttribute("repartidor") Repartidor repartidor, Model model) {
		repartidorService.save(repartidor);
		return "redirect: /rrhh/repartidores";
	}

}
