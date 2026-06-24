package com.zaitec.gs02.domain.models;

import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class Course implements IEntity<Course> {

	/**
	 * The identifier of the course.
	 */
	public UUID id;

	/**
	 * The name of the course.
	 */
	public String name;

	public Course(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public Boolean IsEqual(Course object) {
		if (object == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	@Override
	public void Update(Course object) {
		if (object == null) {
			return;
		}
		this.name = object.name;
	}

}
