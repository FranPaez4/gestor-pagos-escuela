package com.zaitec.gs02.application.usecases.users;

import com.zaitec.gs02.application.dtos.users.DisableUserRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.users.abstracts.IDisableUser;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class DisableUser implements IDisableUser {

	private final IUserRepository userRepository;

	public DisableUser(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public IResult<String> execute(DisableUserRequestDto request) {
		if (request.id() == null) {
			return new Failure<>("El ID del usuario es obligatorio");
		}

		User user = this.userRepository.getById(request.id());

		if (user == null) {
			return new Failure<>("Usuario no encontrado");
		}

		if (user.enabled == null || !user.enabled) {
			return new Failure<>("El usuario ya se encuentra desactivado");
		}

		Boolean isDeactivated = this.userRepository.disableUserById(request.id());

		if (isDeactivated) {
			return new Success<>("Usuario desactivado con éxito");
		}

		return new Failure<>("Error interno al desactivar el usuario");
	}

}
