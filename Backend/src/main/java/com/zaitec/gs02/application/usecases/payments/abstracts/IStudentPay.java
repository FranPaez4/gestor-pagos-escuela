package com.zaitec.gs02.application.usecases.payments.abstracts;

import com.zaitec.gs02.application.dtos.pays.StudentPayRequestDto;
import com.zaitec.gs02.application.dtos.pays.StudentPayResponseDto;
import com.zaitec.gs02.application.usecases.abstracts.IUseCase;

public interface IStudentPay extends IUseCase<StudentPayRequestDto, StudentPayResponseDto> {

}
