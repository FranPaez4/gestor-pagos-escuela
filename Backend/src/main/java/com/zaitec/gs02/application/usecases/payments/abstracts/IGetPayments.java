package com.zaitec.gs02.application.usecases.payments.abstracts;

import com.zaitec.gs02.application.dtos.pays.StudentGetPaysRequestDto;
import com.zaitec.gs02.application.usecases.abstracts.IUseCase;
import com.zaitec.gs02.domain.models.Pay;

import java.util.List;

public interface IGetPayments extends IUseCase<StudentGetPaysRequestDto, List<Pay>> {

}
