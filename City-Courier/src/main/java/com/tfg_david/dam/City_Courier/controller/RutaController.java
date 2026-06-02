 package com.tfg_david.dam.City_Courier.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg_david.dam.City_Courier.model.Ruta;
import com.tfg_david.dam.City_Courier.service.RepartidorService;
import com.tfg_david.dam.City_Courier.service.RutaService;
import com.tfg_david.dam.City_Courier.utilidades.Utilidades;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/logistica")
public class RutaController {

	private final RutaService rutaService;

	private final RepartidorService repartidorService;
	
	@GetMapping("/rutas")
	public String logisticaRuta(@RequestParam(value = "criterio", required = false) String busqueda, Model model) {

		Long stringConvertido;
		
		List<Ruta> topRutas = rutaService.obtenerTop3RutasMasFrecuentes();

		if (busqueda != null && !busqueda.trim().isEmpty()) {
			stringConvertido = Utilidades.extraerCodigoSiEsNumerico(busqueda);
			model.addAttribute("rutaList", rutaService.findByIdOrNombreRuta(busqueda, stringConvertido));
			model.addAttribute("rutasFrecuentes", topRutas);

		} else {
			
			model.addAttribute("rutasFrecuentes", topRutas);
			
			model.addAttribute("rutaList", rutaService.findAll());

		}

		return "logistica/rutas";
	}

	@GetMapping("/rutas/nuevo")
	public String nuevaRuta(Model model) {

		model.addAttribute("ruta", new Ruta());

		model.addAttribute("listaRepartidores", repartidorService.findAll());

		
		return "logistica/forms/ruta-form";
	}

	@PostMapping("/rutas")
	public String submit(@Valid @ModelAttribute("ruta") Ruta ruta, BindingResult bindingResult,
			@RequestParam(value = "zonaSeleccionada", required = false) List<String> zonaSeleccionada,
			@RequestParam(value = "distancia", required = false) Double distancia, 
			@RequestParam(value = "idTrabajador", required = false) Long idTrabajador,
			Model model) {

		Map<String, Double> puntosEntregas = new LinkedHashMap<>();

		if (ruta.getFechaFinal() != null && ruta.getFechaInicio() != null) {
			if (ruta.getFechaFinal().isBefore(ruta.getFechaInicio())) {
				bindingResult.rejectValue("fechaFinal", "error", "La fecha final no puede ser anterior a la de inicio");
			}
		}

		if (bindingResult.hasErrors()) {

			model.addAttribute("rutaList", rutaService.findAll());
			
			return "logistica/forms/ruta-form";

		}

		puntosEntregas = rutaService.transformarString(zonaSeleccionada, distancia);
		ruta.setPuntosEntregas(puntosEntregas);
		
		if (idTrabajador != null) {
			
			rutaService.asignarRutaRepartidor(ruta, idTrabajador);
		
		}


		rutaService.save(ruta);

		return "redirect:/logistica/rutas";
	}

	// Editar y borrar
	
	
	
	@GetMapping("/rutas/borrar/{codigoRuta}")
	public String borrarEnvio(@PathVariable("codigoRuta") Long codigoRuta) {
		
		
		rutaService.deleteRuta(codigoRuta);
		
		return "redirect:/logistica/rutas";

		
		
	}

	@PostMapping("/rutas/editar")
	public String submitEdicion(@Valid @ModelAttribute("ruta") Ruta ruta, BindingResult bindingResult,
			@RequestParam(value = "zonaSeleccionada", required = false) List<String> zonaSeleccionada,
			@RequestParam(value = "distancia", required = false) Double distancia, 
			@RequestParam(value = "idTrabajador", required = false) Long idTrabajador, // NUEVO: Recibimos el ID
			Model model) {

		if (ruta.getFechaFinal() != null && ruta.getFechaInicio() != null) {
			if (ruta.getFechaFinal().isBefore(ruta.getFechaInicio())) {
				bindingResult.rejectValue("fechaFinal", "error", "La fecha final no puede ser anterior a la de inicio");
			}
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("listaRepartidores", repartidorService.findAll());
			return "logistica/forms/ruta-form-edit";
		}

		Map<String, Double> puntosEntregas = rutaService.transformarString(zonaSeleccionada, distancia);
		ruta.setPuntosEntregas(puntosEntregas);

		if (idTrabajador != null) {
			rutaService.asignarRutaRepartidor(ruta, idTrabajador);
		}

		rutaService.save(ruta);

		return "redirect:/logistica/rutas";
	}

	@GetMapping("/rutas/editar/{codigoRuta}")
	public String editarRuta(@PathVariable("codigoRuta") Long codigoRuta, Model model) {

		Optional<Ruta> ruta = rutaService.findById(codigoRuta);

		if (ruta.isPresent()) {
			model.addAttribute("ruta", ruta.get());
			
			model.addAttribute("listaRepartidores", repartidorService.findAll());

			return "logistica/forms/ruta-form-edit";
		} else {
			return "redirect:/logistica/rutas";
		}
	}
}