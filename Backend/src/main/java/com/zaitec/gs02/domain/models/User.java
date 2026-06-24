package com.zaitec.gs02.domain.models;

import java.time.Instant;
import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class User implements IEntity<User> {

	/**
	 * The Id of the user.
	 */
	public UUID id;

	/**
	 * The Id of the role of the User.
	 */
	public UUID role_id;

	/**
	 * The name of the User.
	 */
	public String name;

	/**
	 * The lastname of the user.
	 */
	public String lastname;

	/**
	 * The phone of the user.
	 */
	public String phone;

	/**
	 * The email of the user.
	 */
	public String email;

	/**
	 * The hashed password of the user.
	 */
	public String password;

	/**
	 * Indicate if the user is enabled.
	 */
	public Boolean enabled;

	/**
	 * When the user is created.
	 */
	public Instant created_at;

	/**
	 * The last time that is modified.
	 */
	public Instant updated_at;

	/**
	 * Match if the users are the same.
	 * @param object an object of type Entity
	 * @return true if is equal or false if not
	 */
	@Override
	public Boolean IsEqual(User object) {
		if (object == null) {
			return false;
		}
		return this.id == object.id;
	}

	/**
	 * Update the current user.
	 * @param object the object used to update
	 */
	@Override
	public void Update(User object) {

		this.role_id = object.role_id;
		this.name = object.name;
		this.lastname = object.lastname;
		this.phone = object.phone;
		this.email = object.email;
		this.password = object.password;
		this.enabled = object.enabled;
		this.updated_at = Instant.now();
	}

}
