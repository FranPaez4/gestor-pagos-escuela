package com.zaitec.gs02.application.shared.abstracts;

public interface IResult<T> {

	boolean isSuccess();

	T getValue();

	T getErrorMessage();

}
