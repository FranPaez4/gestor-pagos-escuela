package com.zaitec.gs02.application.usecases.courses;

import com.zaitec.gs02.application.dtos.courses.CreateCourseAcademicRequestDto;
import com.zaitec.gs02.application.usecases.courses.abstracts.ICreateAcademicCourse;
import com.zaitec.gs02.domain.models.AcademicCourse;
import com.zaitec.gs02.domain.repositories.IAcademicCourseRepository;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.Failure;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateAcademicCourse implements ICreateAcademicCourse {

	private final IAcademicCourseRepository academicCourseRepository;

	public CreateAcademicCourse(IAcademicCourseRepository academicCourseRepository) {
		this.academicCourseRepository = academicCourseRepository;
	}

	@Override
	public IResult<String> execute(CreateCourseAcademicRequestDto request) {

		if (request.course_id() == null || request.start_date() == null || request.end_date() == null
				|| request.type() == null) {
			return new Failure<>("Faltan campos obligatorios");
		}

		if (request.start_date().isAfter(request.end_date())) {
			return new Failure<>("La fecha de inicio no puede ser posterior a la fecha de fin");
		}

		if (request.price() < 0) {
			return new Failure<>("El precio no puede ser negativo");
		}

		AcademicCourse newAcademicCourse = new AcademicCourse();
		newAcademicCourse.id = UUID.randomUUID();
		newAcademicCourse.course_id = request.course_id();
		newAcademicCourse.start_date = request.start_date();
		newAcademicCourse.end_date = request.end_date();
		newAcademicCourse.price = request.price();
		newAcademicCourse.type = request.type();

		Boolean isAdded = this.academicCourseRepository.addAcademicCourse(newAcademicCourse);

		if (isAdded) {

			return new Success<>("Curso académico guardado en la base de datos con éxito");
		}
		else {
			return new Failure<>("No se pudo guardar el curso académico en la base de datos");
		}
	}

}
