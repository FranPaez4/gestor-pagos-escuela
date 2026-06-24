package com.zaitec.gs02.application.usecases.auth;

import com.zaitec.gs02.application.services.abstracts.IPasswordHasher;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.auth.abstracts.IRegisterUseCase;
import com.zaitec.gs02.domain.models.Role;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IRoleRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import com.zaitec.gs02.application.dtos.auth.RegisterRequestDto;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCase implements IRegisterUseCase {

	private final IPasswordHasher passwordHasher;

	private final IUserRepository userRepository;

	private final IRoleRepository roleRepository;

	public RegisterUseCase(IPasswordHasher passwordHasher, IUserRepository userRepository,
			IRoleRepository roleRepository) {
		this.passwordHasher = passwordHasher;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public IResult<String> execute(RegisterRequestDto request) {
		if (request == null) {
			return new Failure<>("Usuario no valido");
		}
		if (!isValidUser(request)) {
			return new Failure<>("Usuario no valido");
		}
		Role currentRole = this.roleRepository.getByName("Student");
		User user = request.toUser(this.passwordHasher.hashPassword(request.password()), currentRole.id);

		if (!this.userRepository.addUser(user)) {
			return new Failure<>("No se pudo registrar el usuario");
		}

		return new Success<>("Usuario " + request.username() + " registrado correctamente" + user.id);
	}

	private Boolean isValidUser(RegisterRequestDto request) {
		return request != null && request.username() != null && !request.username().isBlank() && request.email() != null
				&& !request.email().isBlank() && request.password() != null && !request.password().isBlank();
	}

}
