package com.zaitec.gs02.domain.models.abstracts;

public interface IEntity<T> {

	Boolean IsEqual(T object);

	void Update(T object);

}
