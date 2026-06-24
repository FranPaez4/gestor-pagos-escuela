package com.zaitec.gs02.application.usecases.auth;

import com.zaitec.gs02.application.services.abstracts.IJwtService;
import com.zaitec.gs02.application.services.abstracts.IPasswordHasher;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.auth.abstracts.ILoginUseCase;
import com.zaitec.gs02.domain.models.Token;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.ITokenRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import com.zaitec.gs02.application.dtos.auth.LoginRequestDto;
import com.zaitec.gs02.application.dtos.auth.LoginResponseDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class LoginUseCase implements ILoginUseCase {

	private final IUserRepository userRepository;

	private final ITokenRepository tokenRepository;

	private final IPasswordHasher passwordHasher;

	private final IJwtService jwtService;

	public LoginUseCase(IUserRepository userRepository, ITokenRepository tokenRepository,
			IPasswordHasher passwordHasher, IJwtService jwtService) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.passwordHasher = passwordHasher;
		this.jwtService = jwtService;
	}

	@Override
	public IResult<LoginResponseDto<String, String>> execute(LoginRequestDto loginRequest) {

		User user = this.userRepository.getByEmail(loginRequest.email());

		if (user == null) {
			return new Failure<>(new LoginResponseDto("Usuario o contraseña incorrectos", ""));
		}

		if (!this.passwordHasher.checkPassword(loginRequest.password(), user.password)) {
			return new Failure<>(new LoginResponseDto("Usuario o contraseña incorrectos", ""));
		}

		if (!user.enabled) {
			return new Failure<>(new LoginResponseDto("Usuario no habilitado, hable con el administrador", ""));
		}

		var token = this.jwtService.generateToken(user);
		Token currentToken = new Token();
		currentToken.id = UUID.randomUUID();
		currentToken.user_id = user.id;
		currentToken.value = token;
		currentToken.revoked = false;
		currentToken.expired_at = Instant.now().plus(1, ChronoUnit.DAYS);

		revokeOtherTokensIfExist(user);

		this.tokenRepository.AddToken(currentToken);

		return new Success<>(new LoginResponseDto<>(token, user.id.toString()));
	}

	/**
	 * Revokes all other tokens associated with the given user if they exist. This method
	 * retrieves all tokens linked to the specified user, marks them as revoked, and
	 * updates their status in the repository.
	 * @param user the user for whom all other tokens are to be revoked
	 */
	private void revokeOtherTokensIfExist(User user) {
		var tokens = this.tokenRepository.GetTokensByUserId(user.id);
		if (!tokens.isEmpty()) {
			for (Token token : tokens) {
				token.revoked = true;
				this.tokenRepository.UpdateToken(token);
			}
		}
	}

}
