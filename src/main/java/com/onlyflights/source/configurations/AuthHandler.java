package com.onlyflights.source.configurations;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);

		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			if ("ADMIN".equals(grantedAuthority.getAuthority())) {
				response.sendRedirect("/feedback");
			}
		}
	}

}
