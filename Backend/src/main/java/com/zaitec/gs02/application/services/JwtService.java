package com.zaitec.gs02.application.services;

import com.zaitec.gs02.application.services.abstracts.IJwtService;
import com.zaitec.gs02.domain.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements IJwtService {

	private static final String SECRET_STRING = "esta_es_una_clave_secreta_muy_larga_de_mas_de_32_caracteres_para_seguridad";

	private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

	// 2. Tiempo de vida: 1 día (en milisegundos)
	private static final long ONE_DAY_MS = 86_400_000;

	/**
	 * Validate token.
	 * @param token - token to validate
	 * @return - true if token is valid, false otherwise
	 */
	@Override
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(this.SECRET_KEY) // Verificamos que la firma sea nuestra
				.build()
				.parseSignedClaims(token); // Si esto falla, lanza excepción
			return true;
		}
		catch (Exception ex) {
			// El token es falso, ha expirado o está mal formado
			return false;
		}
	}

	/**
	 * Generate token.
	 * @param user - username to generate token for
	 * @return - generated token
	 */
	@Override
	public String generateToken(User user) {
		// Añadir la logica de generacion del token expiración de 1 dia
		// crear claim con el username, role, etc...
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.role_id);
		claims.put("email", user.email);
		claims.put("User_id", user.id);

		// Puedes añadir más: claims.put("tenant", "zaitec");

		Date ahora = new Date();
		Date expiracion = new Date(ahora.getTime() + ONE_DAY_MS);

		return Jwts.builder()
			.claims(claims) // Añadimos los datos extra
			.subject(user.name) // El nombre del usuario (el "dueño" del token)
			.issuedAt(ahora) // Fecha de creación
			.expiration(expiracion) // Fecha de caducidad (1 día)
			.signWith(this.SECRET_KEY) // Firma digital con nuestra llave
			.compact(); // Lo convierte todo en el String final

	}

	/**
	 * Extrae el nombre de usuario (subject) contenido en el token.
	 * @param token string que representa el JSON Web Token.
	 * @return el nombre de usuario (username) almacenado en el claim 'sub'.
	 * @throws io.jsonwebtoken.JwtException si el token es inválido o ha expirado.
	 */
	public String getUsernameFromToken(String token) {
		return Jwts.parser().verifyWith(this.SECRET_KEY).build().parseSignedClaims(token).getPayload().getSubject();
	}

	/**
	 * Extrae el identificador único del usuario del token.
	 * @param token string que representa el JSON Web Token.
	 * @return el ID del usuario extraído de los claims personalizados del token.
	 * @throws io.jsonwebtoken.JwtException si el token es inválido o ha expirado.
	 */
	public String getUserIdFromToken(String token) {
		return Jwts.parser()
			.verifyWith(this.SECRET_KEY)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("User_id", String.class);
	}

	/**
	 * Extrae el rol o autoridad asignada al usuario desde el token.
	 * @param token string que representa el JSON Web Token.
	 * @return el rol del usuario (ej. "ROLE_ADMIN") extraído del claim correspondiente.
	 * @throws io.jsonwebtoken.JwtException si el token es inválido o ha expirado.
	 */
	public String getUserRoleFromToken(String token) {
		return Jwts.parser()
			.verifyWith(this.SECRET_KEY)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);
	}

}
