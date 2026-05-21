package com.tfg_david.dam.City_Courier.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.service.RutaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica")
public class RutaController {

	private final RutaService rutaService;

	@GetMapping("/rutas")
	public String logisticaRuta(@RequestParam(value = "criterio", required = false) String busqueda, Model model) {

		Long stringConvertido;

		if (busqueda != null && !busqueda.trim().isEmpty()) {


			stringConvertido = rutaService.ExtraerCodigoSiEsNumerico(busqueda);

			model.addAttribute("rutaList", rutaService.findByIdOrNombreRuta(busqueda, stringConvertido));

		} else {
			
			model.addAttribute("rutaList", rutaService.findAll());
		}

		model.addAttribute("ruta", new Ruta());

		return "logistica/rutas";
	}

	@PostMapping("/rutas")
	public String submit(@ModelAttribute("ruta") Ruta ruta,
			@RequestParam("zonaSeleccionada") List<String> zonaSeleccionada,
			@RequestParam("distancia") Double distancia, Model model) {

		Map<String, Double> puntosEntregas = new LinkedHashMap<>();

		puntosEntregas = rutaService.transformarString(zonaSeleccionada, distancia);

		ruta.setPuntosEntregas(puntosEntregas);

		rutaService.save(ruta);

		return "redirect:/logistica/rutas";

	}

	@GetMapping("/rutas/{codigoRuta}")
	public String editarRuta(@PathVariable("codigoRuta") Long codigoRuta, Model model) {

		Optional<Ruta> ruta = rutaService.findById(codigoRuta);

		if (ruta.isPresent()) {

			model.addAttribute("ruta", ruta.get());
			model.addAttribute("rutaList", rutaService.findAll());
			model.addAttribute("modoEdicion", true);
			return "logistica/rutas";

		} else {

			return "redirect:/logistica/rutas";
		}

	}

}
