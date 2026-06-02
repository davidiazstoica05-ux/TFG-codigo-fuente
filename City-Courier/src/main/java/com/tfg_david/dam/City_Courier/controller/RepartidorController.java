package com.tfg_david.dam.City_Courier.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	public String rrhh(@RequestParam(required = false) String nombre, Model model) {

		List<Repartidor> repartidorList;

		model.addAttribute("ridersEnVacaciones", repartidorService.countByEstado(Disponibilidad.VACACIONES));
		model.addAttribute("ridersEnBaja", repartidorService.countByEstado(Disponibilidad.DE_BAJA));
		model.addAttribute("ridersDisponibles", repartidorService.countByEstado(Disponibilidad.DISPONIBLE));
		model.addAttribute("totalRiders", repartidorService.count());

		if (nombre != null && !nombre.trim().isEmpty()) {
			repartidorList = repartidorService.findByNombreIgnoreCase(nombre.trim());
		} else {
			repartidorList = repartidorService.findAll();
		}

		model.addAttribute("repartidorList", repartidorList);

		return "rrhh/rrhh";
	}

	@GetMapping("/repartidores/nuevo")
	public String nuevoRepartidor(Model model) {

		model.addAttribute("repartidor", new Repartidor());

		return "rrhh/forms/repartidor-form";
	}

	@PostMapping("/repartidores")
	public String submit(@Valid @ModelAttribute("repartidor") Repartidor repartidorForm, BindingResult bindingResult,
			Model model) {

		Optional<Repartidor> repartidorExistente = repartidorService.findByDni(repartidorForm.getDni());
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


			repartidorForm.setActivo(true);

			repartidorService.save(repartidorForm);
		}

		return "redirect:/rrhh/repartidores";
	}

	// Editar y Borrar
	@GetMapping("/repartidores/borrar/{dni}")
	public String borrarRepartidor(@PathVariable("dni") String dni) {
		
		repartidorService.deleteRepartidor(dni);
		
		return "redirect:/rrhh/repartidores";
	}

	// ARREGLADO: Añadido "/repartidores" a la ruta
	@GetMapping("/repartidores/editar/{dni}")
	public String editarRepartidor(@PathVariable("dni") String dni, Model model) {

		Optional<Repartidor> repartidor = repartidorService.findByDni(dni);

		if (repartidor.isPresent()) {
			model.addAttribute("repartidor", repartidor.get());
			return "rrhh/forms/repartidor-form";
		} else {
			return "redirect:/rrhh/repartidores";
		}
	}

}