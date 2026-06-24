package com.zaitec.gs02.domain.models;

import com.zaitec.gs02.domain.models.abstracts.IEntity;

import java.util.UUID;

public class Room implements IEntity<Room> {

	/**
	 * The identifier of the Room.
	 */
	public UUID id;

	/**
	 * The name of the Room.
	 */
	public String name;

	@Override
	public Boolean IsEqual(Room object) {
		if (object == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	@Override
	public void Update(Room object) {
		if (object == null) {
			return;
		}
		this.name = object.name;
	}

}
