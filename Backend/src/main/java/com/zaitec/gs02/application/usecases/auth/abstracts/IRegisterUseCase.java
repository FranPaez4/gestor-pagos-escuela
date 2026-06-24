package com.zaitec.gs02.application.usecases.auth.abstracts;

import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.dtos.auth.RegisterRequestDto;

public interface IRegisterUseCase {

	IResult<String> execute(RegisterRequestDto registerRequest);

}
