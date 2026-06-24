package com.zaitec.gs02.domain.models;

import com.zaitec.gs02.domain.models.abstracts.IEntity;
import com.zaitec.gs02.domain.shared.Level;

import java.util.UUID;

/**
 * domain model representing a Group.
 *
 * @author Alex Lagares
 */
public class Group implements IEntity<Group> {

	/**
	 * the id of the group.
	 */
	public UUID id;

	/**
	 * the id of the academic course.
	 */
	public UUID academic_course_id;

	/**
	 * the name of the group.
	 */
	public String name;

	/**
	 * the level of the group.
	 */
	public Level level;

	/**
	 * the start time of the group.
	 */
	public String start_time;

	/**
	 * the end time of the group.
	 */
	public String end_time;

	/**
	 * checks if two groups are equal based on their ID.
	 * @param object the group to compare.
	 * @return true if IDs match.
	 */
	@Override
	public Boolean IsEqual(Group object) {
		if (object == null || this.id == null || object.id == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	/**
	 * updates the group data from another object.
	 * @param object the source group data.
	 */
	@Override
	public void Update(Group object) {
		if (object == null) {
			return;
		}
		this.academic_course_id = object.academic_course_id;
		this.name = object.name;
		this.level = object.level;
		this.start_time = object.start_time;
		this.end_time = object.end_time;
	}

}
