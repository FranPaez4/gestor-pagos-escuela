package com.zaitec.gs02.domain.models;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class Student implements IEntity<Student> {

	/**
	 * Id of the Student.
	 */
	public UUID id;

	/**
	 * Birth Date of the Student.
	 */
	public Date birthDate;

	/**
	 * Address of the Student.
	 */
	public String address;

	/**
	 * Creation time of the Student.
	 */
	public Instant created_at;

	/**
	 * Last update time of the Student.
	 */
	public Instant updated_at;

	/**
	 * Compare if the objects are the same.
	 * @param object the object to compare
	 * @return true if are equals else false
	 */
	@Override
	public Boolean IsEqual(Student object) {
		if (object == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	/**
	 * Update the object with the other.
	 * @param object the object to update
	 */
	@Override
	public void Update(Student object) {
		if (object == null) {
			return;
		}
		this.birthDate = object.birthDate;
		this.address = object.address;
		this.updated_at = Instant.now();

	}

}
