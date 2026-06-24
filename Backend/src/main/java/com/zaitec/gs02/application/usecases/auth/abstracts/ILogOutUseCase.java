package com.zaitec.gs02.application.usecases.auth.abstracts;

import com.zaitec.gs02.application.shared.abstracts.IResult;

import java.util.UUID;

public interface ILogOutUseCase {

	IResult<String> execute(String token, UUID userId);

}
