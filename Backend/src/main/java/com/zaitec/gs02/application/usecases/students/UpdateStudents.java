package com.zaitec.gs02.application.usecases.students;

import com.zaitec.gs02.application.dtos.students.UpdateStudentRequestDTO;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.students.abstracts.IUpdateStudents;
import com.zaitec.gs02.domain.models.Student;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IStudentRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateStudents implements IUpdateStudents {

	private final IUserRepository userRepository;

	private final IStudentRepository studentRepository;

	public UpdateStudents(IUserRepository userRepository, IStudentRepository studentRepository) {
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public IResult<String> execute(UpdateStudentRequestDTO updateStudentRequestDTO) {
		UUID studentID = updateStudentRequestDTO.studentID();

		var user = this.userRepository.getById(studentID);
		var student = this.studentRepository.getStudent(studentID);

		if (user == null || student == null) {
			return new Failure<>("Estudiante no encontrado");
		}
		User updateUser = new User();
		updateUser.id = user.id;
		updateUser.name = updateStudentRequestDTO.firstName();
		updateUser.lastname = updateStudentRequestDTO.lastName();
		updateUser.email = updateStudentRequestDTO.email();
		updateUser.phone = updateStudentRequestDTO.phoneNumber();
		updateUser.password = user.password;
		updateUser.role_id = user.role_id;
		updateUser.enabled = user.enabled;
		Boolean userUpdate = this.userRepository.updateUser(updateUser);
		if (!userUpdate) {
			return new Failure<>("No se puede actualizar el usuario");
		}
		Student updateStudent = new Student();
		updateStudent.id = student.id;
		updateStudent.birthDate = updateStudentRequestDTO.birthDate();
		updateStudent.address = updateStudentRequestDTO.address();
		Boolean studentUpdate = this.studentRepository.updateStudent(updateStudent);

		if (!userUpdate && !studentUpdate) {
			return new Failure<>("No se puede actualizar el estudiante");
		}
		if (userUpdate && studentUpdate) {
			return new Success<>("Estudiante actualizado correctamente");
		}
		return new Failure<>("No se puede actualizar");
	}

}
