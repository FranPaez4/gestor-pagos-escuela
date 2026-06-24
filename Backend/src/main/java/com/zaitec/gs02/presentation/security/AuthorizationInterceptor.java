package com.zaitec.gs02.presentation.security;

import com.zaitec.gs02.application.services.abstracts.IJwtService;
import com.zaitec.gs02.domain.models.Role;
import com.zaitec.gs02.domain.models.Token;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IRoleRepository;
import com.zaitec.gs02.domain.repositories.ITokenRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.UUID;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	private final IJwtService jwtService;

	private final ITokenRepository tokenRepository;

	private final IUserRepository userRepository;

	private final IRoleRepository roleRepository;

	public AuthorizationInterceptor(IJwtService jwtService, ITokenRepository tokenRepository,
			IUserRepository userRepository, IRoleRepository roleRepository) {
		this.jwtService = jwtService;
		this.tokenRepository = tokenRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!(handler instanceof HandlerMethod handlerMethod)) {
			return true;
		}

		Authorize authorize = handlerMethod.getMethodAnnotation(Authorize.class);
		if (authorize == null) {
			authorize = handlerMethod.getBeanType().getAnnotation(Authorize.class);
		}

		if (authorize == null) {
			return true;
		}

		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\":\"Token no proporcionado\"}");
			response.setContentType("application/json");
			return false;
		}

		String rawToken = header.substring(7).trim();

		if (!this.jwtService.validateToken(rawToken)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
			response.setContentType("application/json");
			return false;
		}

		Token storedToken = this.tokenRepository.GetAll()
			.stream()
			.filter((t) -> rawToken.equals(t.value))
			.findFirst()
			.orElse(null);

		if (storedToken == null || Boolean.TRUE.equals(storedToken.revoked)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"error\":\"Sesión cerrada o token revocado\"}");
			response.setContentType("application/json");
			return false;
		}

		String userIdString = this.jwtService.getUserIdFromToken(rawToken);
		User user = this.userRepository.getById(UUID.fromString(userIdString));
		if (user == null || Boolean.FALSE.equals(user.enabled)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("{\"error\":\"Usuario no habilitado\"}");
			response.setContentType("application/json");
			return false;
		}

		String[] requiredRoles = authorize.roles();
		if (requiredRoles.length > 0) {
			Role userRole = this.roleRepository.getById(user.role_id);
			if (userRole == null || Arrays.stream(requiredRoles).noneMatch((r) -> r.equalsIgnoreCase(userRole.name))) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("{\"error\":\"No tienes permisos suficientes\"}");
				response.setContentType("application/json");
				return false;
			}
		}

		request.setAttribute("authenticatedUser", user);

		return true;
	}

}
