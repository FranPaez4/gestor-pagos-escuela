package com.zaitec.gs02.application.usecases.students.abstracts;

import com.zaitec.gs02.application.dtos.students.StudentProfileResponseDto;
import com.zaitec.gs02.application.usecases.abstracts.IUseCase;
import com.zaitec.gs02.domain.models.User;

public interface IGetStudentProfile extends IUseCase<User, StudentProfileResponseDto> {

}
