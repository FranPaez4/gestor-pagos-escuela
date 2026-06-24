package com.zaitec.gs02.application.usecases.auth;

import com.zaitec.gs02.application.services.abstracts.IJwtService;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.auth.abstracts.ILogOutUseCase;
import com.zaitec.gs02.domain.models.Token;
import com.zaitec.gs02.domain.repositories.ITokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogOutUseCase implements ILogOutUseCase {

	private final ITokenRepository tokenRepository;

	private final IJwtService jwtService;

	public LogOutUseCase(ITokenRepository tokenRepository, IJwtService jwtService) {
		this.tokenRepository = tokenRepository;
		this.jwtService = jwtService;
	}

	@Override
	public IResult<String> execute(String token, UUID userId) {
		if (userId == null) {
			return new Failure<>("Usuario no identificado");
		}
		if (token == null || token.isBlank()) {
			return new Failure<>("Token no proporcionado");
		}

		String rawToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
		var isTokenValid = this.jwtService.validateToken(rawToken);
		if (!isTokenValid) {
			return new Failure<>("Token inválido ");
		}

		Token currentToken = this.tokenRepository.GetAll()
			.stream()
			.filter((t) -> rawToken.equals(t.value))
			.findFirst()
			.orElse(null);

		if (currentToken == null) {
			return new Failure<>("Token inválido");
		}

		if (!currentToken.user_id.equals(userId)) {
			return new Failure<>("Token invalido");
		}

		if (Boolean.TRUE.equals(currentToken.revoked)) {
			return new Success<>("Sesión cerrada correctamente");
		}

		currentToken.revoked = true;
		this.tokenRepository.UpdateToken(currentToken);

		return new Success<>("Sesión cerrada correctamente");
	}

}
