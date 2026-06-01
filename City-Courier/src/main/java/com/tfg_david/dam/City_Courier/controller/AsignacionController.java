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
import com.tfg_david.dam.City_Courier.model.AsignacionPk;
import com.tfg_david.dam.City_Courier.model.Envio;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;
import com.tfg_david.dam.City_Courier.service.RepartidorService;
import com.tfg_david.dam.City_Courier.utilidades.Utilidades;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/logistica/asignaciones")
public class AsignacionController {

	private final AsignacionService asigService;
	private final EnviosService envioService;
	private final RepartidorService repartidorService;

	@GetMapping
	public String asignacion(@RequestParam(value = "criterio", required = false) String busqueda, Model model) {
		List<Asignacion> listaResultados; 
		Long numeroBusqueda; 
		
		
		if (busqueda != null && !busqueda.trim().isEmpty()) {
			numeroBusqueda = Utilidades.extraerCodigoSiEsNumerico(busqueda);
			listaResultados = asigService.findByIdAsignacionOrRepartidorId(numeroBusqueda, numeroBusqueda);
		} else {
			listaResultados = asigService.findAll();
		}

		asigService.calcularPrecioDistanciaTiempoKm(listaResultados);
		model.addAttribute("asignacionList", listaResultados);

		return "logistica/asignaciones";
	}

	@GetMapping("/nuevo")
	public String nuevaAsignacion(Model model) {
		model.addAttribute("asignacion", new Asignacion());
		model.addAttribute("repartidoresList", repartidorService.findAll());
		model.addAttribute("enviosList", envioService.findAll());

		return "logistica/forms/asignacion-form";
	}
	@PostMapping("/guardar")
	public String asignaciones(@Valid @ModelAttribute("asignacion") Asignacion asignacionForm,
			BindingResult bindingResult, Model model) {

		Envio envioForm;
		Repartidor repartidorForm;
		Long codEnvio;
		Long idRepartidorNuevo;
		Optional<Envio> envioOpt;
		Asignacion asigGuardada;
		
		//Errores
		if (bindingResult.hasErrors()) {
			model.addAttribute("repartidoresList", repartidorService.findAll());
			model.addAttribute("enviosList", envioService.findAll());
			return "logistica/forms/asignacion-form";
		}

		envioForm = asignacionForm.getEnvio();
		repartidorForm = asignacionForm.getRepartidor();

		
		//Si todo es nulo
		if (envioForm == null || envioForm.getCodEnvio() == null ||
			repartidorForm == null || repartidorForm.getIdTrabajador() == null) {
			return "redirect:/logistica/asignaciones";
		}

		codEnvio = envioForm.getCodEnvio();
		idRepartidorNuevo = repartidorForm.getIdTrabajador();
		envioOpt = envioService.findById(codEnvio);

		
		if (envioOpt.isEmpty()) {
			return "redirect:/logistica/asignaciones";
		}

		asigGuardada = envioOpt.get().getAsignacion();

		//Editar
		if (asigGuardada != null && asigGuardada.getRepartidor().getIdTrabajador().equals(idRepartidorNuevo)) {

			asigGuardada.setEstadoPedido(asignacionForm.isEstadoPedido());
			asigGuardada.setFechaEntrega(asignacionForm.getFechaEntrega());
			asigGuardada.setMotivoIncidencia(asignacionForm.getMotivoIncidencia());
			envioOpt.get().setFechaEntregaLimite(asignacionForm.getFechaEntrega());

			if (asigService.validarCargaPeso(asigGuardada)) {
				
				//Antes del save aplicamos el cambio del monitor
				asigService.monitorizarEntrega(asigGuardada);
				asigService.save(asigGuardada);
				envioService.save(envioOpt.get());
			}

		} else {

			if (asigGuardada != null) {
				asigService.deleteAsignacion(codEnvio, asigGuardada.getRepartidor().getIdTrabajador());
			}

			if (asigService.asignarRepartidor(asignacionForm) && asigService.asignarEnvio(asignacionForm)) {
				if (asigService.validarCargaPeso(asignacionForm)) {
					
					asigService.monitorizarEntrega(asignacionForm);
					asigService.save(asignacionForm);
				}
			}
		}

		return "redirect:/logistica/asignaciones";
	}

	@GetMapping("/borrar/{codEnvio}/{idTrabajador}")
	public String borrarRepartidor(@PathVariable("codEnvio") Long codEnvio, @PathVariable("idTrabajador") Long idTrabajador) {
		asigService.deleteAsignacion(codEnvio, idTrabajador);
		
		return "redirect:/logistica/asignaciones";
	}

	@GetMapping("/editar/{codEnvio}/{idTrabajador}")
	public String editarAsignacion(@PathVariable("codEnvio") Long codEnvio, @PathVariable("idTrabajador") Long idTrabajador, Model model) {
		
		AsignacionPk pk; 
		Optional<Asignacion> asignacion; 
		
		pk = new AsignacionPk(codEnvio, idTrabajador);
		asignacion = asigService.findById(pk);

		if (asignacion.isPresent()) {
			model.addAttribute("asignacion", asignacion.get());
			model.addAttribute("repartidoresList", repartidorService.findAll());
			model.addAttribute("enviosList", envioService.findAll());
			
			return "logistica/forms/asignacion-form";
		} else {
			return "redirect:/logistica/asignaciones";
		}
	}
	
	
	@GetMapping("/autoAsignar")
	public String asignarAutomaticamente() {
		
		
		asigService.asignarAutomaticamente();
		
		
		return "redirect:/logistica/asignaciones";
		
	}
	
}