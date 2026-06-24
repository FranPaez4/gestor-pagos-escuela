package com.zaitec.gs02.application.usecases.users;

import com.zaitec.gs02.application.dtos.users.ActivateUserRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.users.abstracts.IActivateUser;
import com.zaitec.gs02.domain.models.Role;
import com.zaitec.gs02.domain.models.Student;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IRoleRepository;
import com.zaitec.gs02.domain.repositories.IStudentRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ActivateUser implements IActivateUser {

	private final IUserRepository userRepository;

	private final IRoleRepository roleRepository;

	private final IStudentRepository studentRepository;

	public ActivateUser(IUserRepository userRepository, IRoleRepository roleRepository,
			IStudentRepository studentRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public IResult<String> execute(ActivateUserRequestDto request) {
		if (request.id() == null) {
			return new Failure<>("El ID del usuario es obligatorio");
		}

		User user = this.userRepository.getById(request.id());

		if (user == null) {
			return new Failure<>("Usuario no encontrado");
		}

		if (user.enabled != null && user.enabled) {
			return new Failure<>("El usuario ya se encuentra activo");
		}

		Boolean isActivated = this.userRepository.enableUserById(request.id());

		if (isActivated) {
			Role userRole = this.roleRepository.getById(user.role_id);

			if (userRole != null && "Student".equalsIgnoreCase(userRole.name)) {

				Student existingStudent = this.studentRepository.getStudent(user.id);

				if (existingStudent == null) {
					Student newStudent = new Student();
					newStudent.id = user.id;
					newStudent.created_at = Instant.now();
					newStudent.updated_at = Instant.now();

					this.studentRepository.addStudent(newStudent);
				}
			}
			return new Success<>("Usuario activado con éxito");
		}

		return new Failure<>("Error interno al activar el usuario");
	}

}
