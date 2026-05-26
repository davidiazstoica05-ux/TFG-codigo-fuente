	package com.tfg_david.dam.City_Courier.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String logisticaEnvio(@RequestParam(required = false) Long codEnvio, Model model) {

		List<Envio> e = new ArrayList();

		if (codEnvio != null && codEnvio != 0) {

			Optional<Envio> envio = envioService.findById(codEnvio);

			if (envio.isPresent()) {

				e.add(envio.get());

				model.addAttribute("envioList", e);

			} else {

				return "redirect:/logistica/principal";
			}

		} else {

			model.addAttribute("envioList", envioService.findAll());

		}

		model.addAttribute("envio", new Envio());

		return "logistica/envios";

	}

	@PostMapping("/principal")
	public String submit(@ModelAttribute("envio") Envio envio, Model model) {

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
			model.addAttribute("modoEdicion", true);
			return "logistica/envios";

		} else {

			return "redirect:/logistica/principal";
		}

	}



}
