package com.zaitec.gs02.application.usecases.students;

import com.zaitec.gs02.application.dtos.students.CreateStudentRequestDto;
import com.zaitec.gs02.application.dtos.students.CreateStudentResposeDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.students.abstracts.ICreateStudent;
import com.zaitec.gs02.domain.models.Student;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IStudentRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CreateStudent implements ICreateStudent {

	private final IStudentRepository studentRepository;

	private final IUserRepository userRepository;

	public CreateStudent(IStudentRepository studentRepository, IUserRepository userRepository) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Create a new student in the database.
	 * @param createStudentRequestDto the DTO with the data to create the student.
	 * @return the student id if the student was created successfully, or an error message
	 * if the student could not be created.
	 */
	@Override
	public IResult<CreateStudentResposeDto> execute(CreateStudentRequestDto createStudentRequestDto) {

		UUID studentId = createStudentRequestDto.userID();
		// Check if the user exists on the database
		User user = this.userRepository.getById(studentId);
		if (user == null) {
			return new Failure<>(new CreateStudentResposeDto("El usuario no existe", null));
		}
		// Check if the student already exists on the database
		if (this.studentRepository.getStudent(studentId) != null) {
			return new Failure<>(new CreateStudentResposeDto("El estudiante ya existe", null));
		}
		// Create the student on the database
		Student student = new Student();
		student.id = studentId;
		student.birthDate = createStudentRequestDto.birthDate();
		student.address = createStudentRequestDto.address();
		student.created_at = Instant.now();
		student.updated_at = Instant.now();

		if (this.studentRepository.addStudent(student)) {
			var enabled = this.userRepository.enableUserById(studentId);
			return new Success<>(new CreateStudentResposeDto("studentId", studentId));
		}

		return new Failure<>(new CreateStudentResposeDto("No se pudo crear el estudiante", null));
	}

}
