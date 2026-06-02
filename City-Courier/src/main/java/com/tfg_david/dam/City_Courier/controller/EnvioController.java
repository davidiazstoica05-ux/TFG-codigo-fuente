package com.tfg_david.dam.City_Courier.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
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
import com.tfg_david.dam.City_Courier.model.Zona;
import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica/envios")
public class EnvioController {

	private final EnviosService envioService;
	private final AsignacionService asigService;
	

	@GetMapping
	public String logisticaEnvio(@RequestParam(required = false) Zona zona,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaBusqueda,
			Model model) {

		List<Envio> listaResultado;
		LocalDate soloElDia;
		LocalDateTime finalDia;
		LocalDateTime inicioDia;

		if (zona != null && fechaBusqueda == null) {

			listaResultado = envioService.findByZona(zona);

		} else if (fechaBusqueda != null && zona == null) {

			soloElDia = fechaBusqueda.toLocalDate();

			inicioDia = soloElDia.atStartOfDay();
			
			finalDia = soloElDia.atTime(LocalTime.MAX);

			listaResultado = envioService.findByFechaEntregaLimiteBetween(inicioDia, finalDia);

		} else if (zona != null && fechaBusqueda != null) {

			listaResultado = envioService.findAll();

		} else {
			listaResultado = envioService.findAll();
		}

		model.addAttribute("envioList", listaResultado);

		return "logistica/envios";
	}

	@GetMapping("/nuevos")
	public String nuevoEnvio(Model model) {

		model.addAttribute("envio", new Envio());

		return "logistica/forms/envio-form";
	}

	@PostMapping("/guardar")
	public String submit(@Valid @ModelAttribute("envio") Envio envio, BindingResult bindingResult, Model model) {

		Asignacion asigid;

		if (bindingResult.hasErrors()) {

			return "logistica/forms/envio-form";

		}

		//Editar
		if (envio.getCodEnvio() != null) {
			
			Optional<Envio> envioRecibido = envioService.findById(envio.getCodEnvio());
			
			
			if (envioRecibido.isPresent()) {
			
				asigid = envioRecibido.get().getAsignacion();
				
				envio.setAsignacion(asigid);
			}
		}

		//La he liado un poco poniendo el estado del paquete en asignacion, pero cuando me di cuenta era demasiado tarde para cambiarlo. 
		//De todas formas lo he conseguido solucionar de una forma un poco mas enrevesada
		
		if (envio.getAsignacion() != null) {
		    asigService.monitorizarEntrega(envio.getAsignacion());
		}

		envioService.save(envio);

		return "redirect:/logistica/envios";
	}

	// Editar y borrar

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