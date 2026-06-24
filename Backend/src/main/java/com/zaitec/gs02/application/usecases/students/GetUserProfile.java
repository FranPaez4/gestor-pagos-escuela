package com.zaitec.gs02.application.usecases.students;

import com.zaitec.gs02.application.dtos.students.StudentProfileResponseDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.students.abstracts.IGetStudentProfile;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IStudentRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserProfile implements IGetStudentProfile {

	private final IStudentRepository studentRepository;

	public GetUserProfile(IStudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	public IResult<StudentProfileResponseDto> execute(User user) {
		if (user.id == null) {
			return new Failure<>(null);
		}

		var student = this.studentRepository.getStudent(user.id);
		if (student == null) {
			return new Failure<>(null);
		}

		StudentProfileResponseDto response = new StudentProfileResponseDto(user.name, user.lastname, user.email,
				user.phone, student.birthDate, student.address);

		return new Success<>(response);
	}

}
