package com.tfg_david.dam.City_Courier.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = "/";

		for (GrantedAuthority rol : authentication.getAuthorities()) {

			if (rol.getAuthority().equals("ROLE_LOGISTICA")) {

				redirectUrl = "/logistica/asignaciones";

			} else if (rol.getAuthority().equals("ROLE_RRHH")) {

				redirectUrl = "/rrhh/rrhh";

			} else if (rol.getAuthority().equals("ROLE_ADMIN")) {

				redirectUrl = "/admin/admin";
			}

		}

		response.sendRedirect(redirectUrl);
	}
}
