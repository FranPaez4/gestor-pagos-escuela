package com.zaitec.gs02.application.usecases.courses;

import com.zaitec.gs02.application.dtos.courses.UpdateCourseRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.courses.abstracts.IUpdateCourse;
import com.zaitec.gs02.domain.models.Course;
import com.zaitec.gs02.domain.repositories.ICourseRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourse implements IUpdateCourse {

	private final ICourseRepository courseRepository;

	public UpdateCourse(ICourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public IResult<String> execute(UpdateCourseRequestDto request) {

		Course existingCourse = this.courseRepository.getById(request.id());

		if (existingCourse == null) {
			return new Failure<>("Curso no encontrado");
		}

		existingCourse.name = request.name();

		Boolean isUpdate = this.courseRepository.updateCourse(existingCourse);

		if (isUpdate) {
			return new Success<>("Curso actualizado en la base de datos con éxito");
		}
		else {
			return new Failure<>("No se pudo actualizar el curso en la base de datos");
		}

	}

}
