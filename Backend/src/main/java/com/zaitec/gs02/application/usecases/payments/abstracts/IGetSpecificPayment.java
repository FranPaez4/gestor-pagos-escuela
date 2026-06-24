package com.zaitec.gs02.application.usecases.payments.abstracts;

import com.zaitec.gs02.application.dtos.pays.StudentGetSpecificPayRequestDto;
import com.zaitec.gs02.application.usecases.abstracts.IUseCase;
import com.zaitec.gs02.domain.models.Pay;

public interface IGetSpecificPayment extends IUseCase<StudentGetSpecificPayRequestDto, Pay> {

}
