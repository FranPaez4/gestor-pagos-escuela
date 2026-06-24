package com.zaitec.gs02.application.usecases.courses.abstracts;

import com.zaitec.gs02.application.dtos.courses.UpdateCourseRequestDto;
import com.zaitec.gs02.application.shared.abstracts.IResult;

public interface IUpdateCourse {

	IResult<String> execute(UpdateCourseRequestDto request);

}
