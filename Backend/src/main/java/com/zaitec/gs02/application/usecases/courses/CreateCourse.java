package com.zaitec.gs02.application.usecases.courses;

import com.zaitec.gs02.application.dtos.courses.CreateCoursesRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.courses.abstracts.ICreateCourse;
import com.zaitec.gs02.domain.models.Course;
import com.zaitec.gs02.domain.repositories.ICourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCourse implements ICreateCourse {

	private final ICourseRepository courseRepository;

	public CreateCourse(ICourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public IResult<String> execute(CreateCoursesRequestDto request) {
		Course newCourse = new Course(request.id(), request.name());

		Boolean isAdded = this.courseRepository.addCourse(newCourse);
		if (isAdded) {
			return new Success<>("Curso guardado en la base de datos con éxito");
		}
		else {
			return new Failure<>("No se pudo guardar el curso en la base de datos");
		}
	}

}
