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

		model.addAttribute("repartidoresList", repartidorService.findAll());
		model.addAttribute("enviosList", envioService.findAll());
		model.addAttribute("asignacion", new Asignacion());

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

	@GetMapping("/asignaciones/editar/{idAsignacion}")
	public String editarAsignacion(@PathVariable("idAsignacion") Long idAsignacion, Model model) {

		Optional<Asignacion> asignacion = asigService.findById(idAsignacion);

		if (asignacion.isPresent()) {

			model.addAttribute("asignacion", asignacion.get());

			model.addAttribute("asignacionList", asigService.findAll());

			model.addAttribute("repartidoresList", repartidorService.findAll());

			model.addAttribute("enviosList", envioService.findAll());

			model.addAttribute("modoEdicion", true);

			return "/logistica/asignaciones";

		} else {

			return "redirect:/logistica/asignaciones";

		}

	}

	@PostMapping("/asignaciones")
	public String asignaciones(@Valid @ModelAttribute("asignacion") Asignacion asignacion, BindingResult bindingResult,
			Model model) {

		Optional<Asignacion> asignacionAntigua;
		Envio envioAntiguo;

		if (bindingResult.hasErrors()) {

			model.addAttribute("enviosList", envioService.findAll());
			model.addAttribute("repartidoresList", repartidorService.findAll());
			model.addAttribute("asignacionList", asigService.findAll());

			return "/logistica/asignaciones";
		}

		if (asignacion.getIdAsignacion() != null) {

			asignacionAntigua = asigService.findById(asignacion.getIdAsignacion());

			if (asignacionAntigua.isPresent() && asignacionAntigua.get()
																  .getEnvio() != null) {

				envioAntiguo = asignacionAntigua.get()
											    .getEnvio();

				if (!envioAntiguo.getCodEnvio()
								 .equals(asignacion.getEnvio()
										 		   .getCodEnvio())) {

					envioAntiguo.setAsignacion(null);

					envioService.save(envioAntiguo);
				}
			}
		}

		if (asigService.asignarRepartidor(asignacion) && asigService.asignarEnvio(asignacion)) {
			asigService.save(asignacion);
		}

		return "redirect:/logistica/asignaciones";
	}

}
