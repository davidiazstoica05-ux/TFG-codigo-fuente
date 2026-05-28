package com.tfg_david.dam.City_Courier.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.service.RepartidorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rrhh")
public class RepartidorController {

	private final RepartidorService repartidorService;

	@GetMapping("/repartidores")
	public String rrhh(@RequestParam(required = false) String dni, Model model) {

		List<Repartidor> r = new ArrayList<>();

		model.addAttribute("ridersEnVacaciones", repartidorService.countByEstado(Disponibilidad.VACACIONES));
		model.addAttribute("ridersEnBaja", repartidorService.countByEstado(Disponibilidad.DE_BAJA));
		model.addAttribute("ridersDisponibles", repartidorService.countByEstado(Disponibilidad.DISPONIBLE));
		model.addAttribute("totalRiders", repartidorService.count());

		if (dni != null && !dni.trim().isEmpty()) {
			Optional<Repartidor> repartidor = repartidorService.findById(dni);

			if (repartidor.isPresent()) {
				r.add(repartidor.get());
				model.addAttribute("repartidorList", r);
			} else {
				return "redirect:/rrhh/repartidores";
			}
		} else {
			model.addAttribute("repartidorList", repartidorService.findAll());
		}

		return "rrhh/rrhh";
	}

	@GetMapping("/repartidores/nuevo")
	public String nuevoRepartidor(Model model) {

		model.addAttribute("repartidor", new Repartidor());

		return "rrhh/forms/repartidor-form";
	}
	
	
	@GetMapping("/borrar/{dni}")
	public String borrarRepartidor(@PathVariable("dni") String dni) {
		
		repartidorService.deleteRepartidor(dni);
		
		return "redirect:/rrhh/repartidores";
	
		
	}

	@GetMapping("/editar/{dni}")
	public String editarRepartidor(@PathVariable("dni") String dni, Model model) {

		Optional<Repartidor> repartidor = repartidorService.findById(dni);

		if (repartidor.isPresent()) {
			model.addAttribute("repartidor", repartidor.get());
			return "rrhh/forms/repartidor-form";
		} else {
			return "redirect:/rrhh/repartidores";
		}
	}

	@PostMapping("/repartidores")
	public String submit(@Valid @ModelAttribute("repartidor") Repartidor repartidorForm, BindingResult bindingResult,
			Model model) {

		Optional<Repartidor> repartidorExistente = repartidorService.findById(repartidorForm.getDni());
		Repartidor repGuardado;
		
		
		if (bindingResult.hasErrors()) {
			
			
			return "rrhh/forms/repartidor-form";
			
		}

		if (repartidorExistente.isPresent()) {

			repGuardado = repartidorExistente.get();

			repGuardado.setNombre(repartidorForm.getNombre());
			repGuardado.setApellidos(repartidorForm.getApellidos());
			repGuardado.setTelefono(repartidorForm.getTelefono());
			repGuardado.setEstado(repartidorForm.getEstado());
			repGuardado.setVehiculo(repartidorForm.getVehiculo());
			repGuardado.setCargaMax(repartidorForm.getCargaMax());
			repGuardado.setEmail(repartidorForm.getEmail());
			repGuardado.setGenero(repartidorForm.getGenero());
			repGuardado.setZona(repartidorForm.getZona());

			repartidorService.save(repGuardado);
		} else {
			repartidorService.save(repartidorForm);
		}

		return "redirect:/rrhh/repartidores";
	}
}