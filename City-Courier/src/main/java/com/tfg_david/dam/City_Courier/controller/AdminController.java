package com.tfg_david.dam.City_Courier.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg_david.dam.City_Courier.model.Admin;
import com.tfg_david.dam.City_Courier.model.Disponibilidad;
import com.tfg_david.dam.City_Courier.model.Logistica;
import com.tfg_david.dam.City_Courier.model.RRHH;
import com.tfg_david.dam.City_Courier.model.Repartidor;
import com.tfg_david.dam.City_Courier.service.AdminService;
import com.tfg_david.dam.City_Courier.service.AsignacionService;
import com.tfg_david.dam.City_Courier.service.EnviosService;
import com.tfg_david.dam.City_Courier.service.LogisticaService;
import com.tfg_david.dam.City_Courier.service.RepartidorService;
import com.tfg_david.dam.City_Courier.service.RrhhService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final EnviosService envioService;
	private final AsignacionService asigService;
	private final RepartidorService repartidorService;
	private final AdminService adminService;
	private final RrhhService rrhhService;
	private final LogisticaService logisticaService;
	
	
	private final PasswordEncoder passwEncoder;

	@GetMapping("/adminHome")
	public String home(Model model) {

		model.addAttribute("totalEnvios", envioService.countEnvios());

		model.addAttribute("enviosPendientes", asigService.countByEstadoPedido(false));

		model.addAttribute("enviosEntregados", asigService.countByEstadoPedido(true));

		model.addAttribute("ridersEnVacaciones", repartidorService.countByEstado(Disponibilidad.VACACIONES));

		model.addAttribute("ridersEnBaja", repartidorService.countByEstado(Disponibilidad.DE_BAJA));

		model.addAttribute("ridersDisponibles", repartidorService.countByEstado(Disponibilidad.DISPONIBLE));

		model.addAttribute("totalRiders", repartidorService.count());

		return "admin/adminHome";

	}

	//Crear nuevoAdmin
	@GetMapping("/nuevoAdmin")
	public String mostrarFormAdmin(Model model) {

		model.addAttribute("trabajador", new Admin());
		model.addAttribute("rutaPost", "/admin/nuevoAdmin/guardar");

		return "admin/forms/otrosUsuarios-form";

	}

	@PostMapping("/nuevoAdmin/guardar")
	public String submitAdmin(@ModelAttribute("trabajador") Admin adminForm, Model model) {

		adminForm.setPassw(passwEncoder.encode(adminForm.getPassw()));
		adminForm.setActivo(true);

		adminService.save(adminForm);

		return "redirect:/admin/adminHome";

	}
	
	//Crear nuevoRRHH
	@GetMapping("/nuevoRRHH")
	public String mostrarFormRRHH(Model model) {

		model.addAttribute("trabajador", new RRHH());
		model.addAttribute("rutaPost", "/admin/nuevoRRHH/guardar");

		return "admin/forms/otrosUsuarios-form";
	}

	@PostMapping("/nuevoRRHH/guardar")
	public String submitRRHH(@ModelAttribute("trabajador") RRHH rrhhForm, Model model) {

		rrhhForm.setPassw(passwEncoder.encode(rrhhForm.getPassw()));
		rrhhForm.setActivo(true);

		rrhhService.save(rrhhForm);

		return "redirect:/admin/adminHome";

	}
	
	// Crear nuevoLogistica
		@GetMapping("/nuevoLogistica")
		public String mostrarFormLogistica(Model model) {

			model.addAttribute("trabajador", new Logistica());
			model.addAttribute("rutaPost", "/admin/nuevoLogistica/guardar"); 

			return "admin/forms/otrosUsuarios-form";
		}

		@PostMapping("/nuevoLogistica/guardar")
		public String submitLogistica(@ModelAttribute("trabajador") Logistica logisticaForm, Model model) {

			logisticaForm.setPassw(passwEncoder.encode(logisticaForm.getPassw()));
			logisticaForm.setActivo(true);

			logisticaService.save(logisticaForm); 

			return "redirect:/admin/adminHome";
		}
	

		@GetMapping("/nuevoRepartidor")
		public String mostrarFormRepartidor(Model model) {
			
			model.addAttribute("repartidor", new Repartidor());

			return "admin/forms/repartidor-form"; 
		}

		@PostMapping("/nuevoRepartidor/guardar")
		public String submitRepartidor(@ModelAttribute("repartidor") Repartidor repartidorForm , Model model) {
			repartidorForm.setPassw(passwEncoder.encode(repartidorForm.getPassw()));
			repartidorForm.setActivo(true);
			repartidorService.save(repartidorForm);
			return "redirect:/admin/adminHome"; 
		}

}
