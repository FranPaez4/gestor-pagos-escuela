package com.zaitec.gs02.application.services.abstracts;

import com.zaitec.gs02.domain.models.User;

public interface IJwtService {

	/**
	 * Validate token.
	 * @param token - token to validate
	 * @return - true if token is valid, false otherwise
	 */
	boolean validateToken(String token);

	/**
	 * Generate token.
	 * @param user - username to generate token for
	 * @return - generated token
	 */
	String generateToken(User user);

	/**
	 * Extrae el nombre de usuario (subject) contenido en el token.
	 * @param token string que representa el JSON Web Token.
	 * @return el nombre de usuario (username) almacenado en el claim 'sub'.
	 * @throws io.jsonwebtoken.JwtException si el token es inválido o ha expirado.
	 */
	String getUsernameFromToken(String token);

	/**
	 * Extrae el identificador único del usuario del token.
	 * @param token string que representa el JSON Web Token.
	 * @return el ID del usuario extraído de los claims personalizados del token.
	 * @throws io.jsonwebtoken.JwtException si el token es inválido o ha expirado.
	 */
	String getUserIdFromToken(String token);

	/**
	 * Extrae el rol o autoridad asignada al usuario desde el token.
	 * @param token string que representa el JSON Web Token.
	 * @return el rol del usuario (ej. "ROLE_ADMIN") extraído del claim correspondiente.
	 * @throws io.jsonwebtoken.JwtException si el token es inválido o ha expirado.
	 */
	String getUserRoleFromToken(String token);

}
