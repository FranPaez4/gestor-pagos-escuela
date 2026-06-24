package com.zaitec.gs02.application.shared;

import com.zaitec.gs02.application.shared.abstracts.IResult;

public class Success<T> implements IResult<T> {

	private final T value;

	public Success(T value) {
		this.value = value;
	}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public T getErrorMessage() {
		return null;
	}

}
