package com.zaitec.gs02.application.usecases.courses;

import com.zaitec.gs02.application.dtos.courses.UpdateAcademicCourseRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.courses.abstracts.IUpdateAcademicCourse;
import com.zaitec.gs02.domain.models.AcademicCourse;
import com.zaitec.gs02.domain.repositories.IAcademicCourseRepository;
import org.springframework.stereotype.Service;

@Service

public class UpdateAcademicCourse implements IUpdateAcademicCourse {

	private final IAcademicCourseRepository academicCourseRepository;

	public UpdateAcademicCourse(IAcademicCourseRepository academicCourseRepository) {
		this.academicCourseRepository = academicCourseRepository;
	}

	@Override
	public IResult<String> execute(UpdateAcademicCourseRequestDto request) {
		if (request.id() == null || request.course_id() == null || request.start_date() == null
				|| request.end_date() == null || request.type() == null) {
			return new Failure<>("Faltan campos obligatorios");
		}

		if (request.start_date().isAfter(request.end_date())) {
			return new Failure<>("La fecha de inicio no puede ser posterior a la fecha de fin");
		}

		if (request.price() < 0) {
			return new Failure<>("El precio no puede ser negativo");
		}

		AcademicCourse courseToUpdate = this.academicCourseRepository.getById(request.id());

		if (courseToUpdate == null) {
			return new Failure<>("Curso académico no encontrado");
		}

		courseToUpdate.course_id = request.course_id();
		courseToUpdate.start_date = request.start_date();
		courseToUpdate.end_date = request.end_date();
		courseToUpdate.price = request.price();
		courseToUpdate.type = request.type();

		Boolean isUpdated = this.academicCourseRepository.updateAcademicCourse(courseToUpdate);

		if (isUpdated) {
			return new Success<>("Curso académico actualizado con éxito");
		}
		else {
			return new Failure<>("No se pudo actualizar el curso académico en la base de datos");
		}
	}

}
