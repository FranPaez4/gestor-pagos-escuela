package com.zaitec.gs02.application.usecases.students.abstracts;

import com.zaitec.gs02.application.dtos.students.CreateStudentRequestDto;
import com.zaitec.gs02.application.dtos.students.CreateStudentResposeDto;
import com.zaitec.gs02.application.usecases.abstracts.IUseCase;

public interface ICreateStudent extends IUseCase<CreateStudentRequestDto, CreateStudentResposeDto> {

}
