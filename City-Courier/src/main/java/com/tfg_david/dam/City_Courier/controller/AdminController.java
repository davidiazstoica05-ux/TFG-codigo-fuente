package com.tfg_david.dam.City_Courier.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;
import com.tfg_david.dam.City_Courier.service.RepartidorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final EnviosService envioService;
	private final AsignacionService asigService;
	private final RepartidorService repartidorService;

	@GetMapping("/adminHome")
	public String home(Model model) {

		model.addAttribute("totalEnvios", envioService.countEnvios());

		model.addAttribute("enviosPendientes", asigService.countByEstadoPedido(false));

		model.addAttribute("enviosEntregados", asigService.countByEstadoPedido(true));

		model.addAttribute("ridersEnVacaciones", repartidorService.countByEstado(Disponibilidad.VACACIONES));

		model.addAttribute("ridersEnBaja", repartidorService.countByEstado(Disponibilidad.DE_BAJA));

		model.addAttribute("ridersDisponibles", repartidorService.countByEstado(Disponibilidad.DISPONIBLE));

		model.addAttribute("totalRiders", repartidorService.count());

		return "admin/adminHome";

	}

}
