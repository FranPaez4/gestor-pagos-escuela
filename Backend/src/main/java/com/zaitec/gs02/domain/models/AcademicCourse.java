package com.zaitec.gs02.domain.models;

import com.zaitec.gs02.domain.shared.AcademicCourseType;

import java.time.Instant;
import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class AcademicCourse implements IEntity<AcademicCourse> {

	/**
	 * The id of the academic course.
	 */
	public UUID id;

	/**
	 * The id of the course.
	 */
	public UUID course_id;

	/**
	 * The start date of the academic course.
	 */
	public Instant start_date;

	/**
	 * The end date of the academic course.
	 */
	public Instant end_date;

	/**
	 * The price of the academic course.
	 */
	public float price;

	/**
	 * The type of the academic course.
	 */
	public AcademicCourseType type;

	@Override
	public Boolean IsEqual(AcademicCourse object) {
		if (object == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	@Override
	public void Update(AcademicCourse object) {
		if (object == null) {
			return;
		}
		this.course_id = object.course_id;
		this.start_date = object.start_date;
		this.end_date = object.end_date;
		this.price = object.price;
		this.type = object.type;
	}

}
