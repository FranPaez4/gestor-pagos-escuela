package com.zaitec.gs02.domain.models;

import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class Role implements IEntity<Role> {

	/**
	 * The id of the role.
	 */
	public UUID id;

	/**
	 * The name of the role.
	 */
	public String name;

	@Override
	public Boolean IsEqual(Role object) {
		if (this.id == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	@Override
	public void Update(Role object) {
		this.name = object.name;
	}

}
