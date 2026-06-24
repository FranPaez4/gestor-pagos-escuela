package com.zaitec.gs02.domain.models;

import com.zaitec.gs02.domain.models.abstracts.IEntity;

import java.time.LocalTime;
import java.util.UUID;

/**
 * domain model representing the relationship between rooms and groups.
 *
 * @author Alex Lagares
 */
public class RoomPerGroup implements IEntity<RoomPerGroup> {

	/**
	 * the id of the group.
	 */
	public UUID group_id;

	/**
	 * the id of the room.
	 */
	public UUID room_id;

	/**
	 * the start schedule.
	 */
	public LocalTime schedule_init;

	/**
	 * the end schedule.
	 */
	public LocalTime schedule_end;

	/**
	 * checks if two objects are equal based on the composite key.
	 * @param object the object to compare.
	 * @return true if both group_id and room_id match.
	 */
	@Override
	public Boolean IsEqual(RoomPerGroup object) {
		if (object == null) {
			return false;
		}
		return this.group_id.equals(object.group_id) && this.room_id.equals(object.room_id);
	}

	/**
	 * updates the data from another object.
	 * @param object the source data.
	 */
	@Override
	public void Update(RoomPerGroup object) {
		if (object == null) {
			return;
		}
		this.schedule_init = object.schedule_init;
		this.schedule_end = object.schedule_end;
	}

}
