package com.tfg_david.dam.City_Courier.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.EstadoTiempo;
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
		List<Asignacion> asignacionList;
		int countATiempo;
		int countEnRiesgo;
		int countAtrasado;
		EstadoTiempo estadoActual;

		countATiempo = 0;
		countEnRiesgo = 0;
		countAtrasado = 0;
		asignacionList = asigService.findAll();

		for (Asignacion asignacion : asignacionList) {

			estadoActual = asigService.monitorizarEntrega(asignacion);

			asignacion.setEstadoTiempo(estadoActual);

			if (estadoActual == EstadoTiempo.ATRASADO) {
				countAtrasado++;

			} else if (estadoActual == EstadoTiempo.EN_RIESGO) {
				countEnRiesgo++;
			}

			else if (estadoActual == EstadoTiempo.A_TIEMPO) {
				countATiempo++;
			}
		}

		model.addAttribute("totalEnvios", envioService.countEnvios());
		model.addAttribute("enviosPendientes", asigService.countByEstadoPedido(false));
		model.addAttribute("enviosEntregados", asigService.countByEstadoPedido(true));

		model.addAttribute("paquetesATiempo", countATiempo);
		model.addAttribute("paquetesEnRiesgo", countEnRiesgo);
		model.addAttribute("paquetesAtrasados", countAtrasado);

		return "logistica/logisticaHome";
	}

}
