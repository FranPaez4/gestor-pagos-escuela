package com.zaitec.gs02.application.usecases.abstracts;

import com.zaitec.gs02.application.shared.abstracts.IResult;

public interface IUseCase<TRequest, TResponse> {

	IResult<TResponse> execute(TRequest request);

}
