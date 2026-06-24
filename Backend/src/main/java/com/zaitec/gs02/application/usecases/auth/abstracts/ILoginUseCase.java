package com.zaitec.gs02.application.usecases.auth.abstracts;

import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.abstracts.IUseCase;
import com.zaitec.gs02.application.dtos.auth.LoginRequestDto;
import com.zaitec.gs02.application.dtos.auth.LoginResponseDto;

public interface ILoginUseCase extends IUseCase<LoginRequestDto, LoginResponseDto<String, String>> {

	// Este metodo recibe tu DTO y promete devolver un String (que más adelante será un
	// Token JWT)
	IResult<LoginResponseDto<String, String>> execute(LoginRequestDto loginRequest);

}
