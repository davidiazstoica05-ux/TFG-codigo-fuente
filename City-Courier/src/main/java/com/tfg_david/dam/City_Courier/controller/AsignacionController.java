package com.tfg_david.dam.City_Courier.controller;

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

import com.tfg_david.dam.City_Courier.model.Asignacion;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;
import com.tfg_david.dam.City_Courier.service.RepartidorService;
import com.tfg_david.dam.City_Courier.utilidades.Utilidades;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/logistica")
public class AsignacionController {

	private final AsignacionService asigService;
	private final EnviosService envioService;
	private final RepartidorService repartidorService;

	@GetMapping("/asignaciones")
	public String asignacion(@RequestParam(value = "criterio", required = false) String busqueda, Model model) {

		List<Asignacion> listaResultados;
		Long stringConvertido = null;

		if (busqueda != null && !busqueda.trim().isEmpty()) {
			if (Utilidades.comprobarSiEsDNI(busqueda)) {
				listaResultados = asigService.findByIdAsignacionOrRepartidorDni(stringConvertido, busqueda);
			} else {
				stringConvertido = Utilidades.extraerCodigoSiEsNumerico(busqueda);
				listaResultados = asigService.findByIdAsignacionOrRepartidorDni(stringConvertido, busqueda);
			}
		} else {
			listaResultados = asigService.findAll();
		}

		model.addAttribute("asignacionList", listaResultados);

		return "logistica/asignaciones";
	}

	@GetMapping("/asignaciones/nuevo")
	public String nuevaAsignacion(Model model) {

		model.addAttribute("asignacion", new Asignacion());

		model.addAttribute("repartidoresList", repartidorService.findAll());
		model.addAttribute("enviosList", envioService.findAll());

		return "logistica/forms/asignacion-form";
	}

	@GetMapping("/asignaciones/editar/{idAsignacion}")
	public String editarAsignacion(@PathVariable("idAsignacion") Long idAsignacion, Model model) {

		Optional<Asignacion> asignacion = asigService.findById(idAsignacion);

		if (asignacion.isPresent()) {
			model.addAttribute("asignacion", asignacion.get());

			model.addAttribute("repartidoresList", repartidorService.findAll());
			model.addAttribute("enviosList", envioService.findAll());

			return "logistica/forms/asignacion-form";
		} else {
			return "redirect:/logistica/asignaciones";
		}
	}

	@PostMapping("/asignaciones")
	public String asignaciones(@Valid @ModelAttribute("asignacion") Asignacion asignacionForm,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return "logistica/forms/asignacion-form";

		}

		if (asignacionForm.getIdAsignacion() != null) {

			Optional<Asignacion> asignacionOpt = asigService.findById(asignacionForm.getIdAsignacion());
			Envio envioAntiguo;

			if (asignacionOpt.isPresent()) {
				Asignacion asigGuardada = asignacionOpt.get();

				if (asigGuardada.getEnvio() != null
						&& !asigGuardada.getEnvio().getCodEnvio().equals(asignacionForm.getEnvio().getCodEnvio())) {

					envioAntiguo = asigGuardada.getEnvio();
					envioAntiguo.setAsignacion(null);
					envioService.save(envioAntiguo);
				}

				// Hecho para no tener que tener todos los campos en el form
				asigGuardada.setCoste(asignacionForm.getCoste());
				asigGuardada.setEstadoPedido(asignacionForm.isEstadoPedido());
				asigGuardada.setFechaEntrega(asignacionForm.getFechaEntrega());
				asigGuardada.setMotivoIncidencia(asignacionForm.getMotivoIncidencia());
				asigGuardada.setRepartidor(asignacionForm.getRepartidor());
				asigGuardada.setEnvio(asignacionForm.getEnvio());

				if (asigService.asignarRepartidor(asigGuardada) && asigService.asignarEnvio(asigGuardada)) {
					asigService.save(asigGuardada);
				}
			}
		}

		else {
			if (asigService.asignarRepartidor(asignacionForm) && asigService.asignarEnvio(asignacionForm)) {
				asigService.save(asignacionForm);
			}
		}

		return "redirect:/logistica/asignaciones";
	}
}