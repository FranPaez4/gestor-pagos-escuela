package com.zaitec.gs02.application.shared;

import com.zaitec.gs02.application.shared.abstracts.IResult;

public class Failure<T> implements IResult<T> {

	private final T message;

	public Failure(T value) {
		this.message = value;
	}

	@Override
	public boolean isSuccess() {
		return false;
	}

	@Override
	public T getValue() {
		throw new IllegalStateException("No value for Failure");
	}

	@Override
	public T getErrorMessage() {
		return this.message;
	}

}
