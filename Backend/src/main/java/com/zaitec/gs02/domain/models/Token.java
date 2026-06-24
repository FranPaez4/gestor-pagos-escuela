package com.zaitec.gs02.domain.models;

import java.time.Instant;
import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class Token implements IEntity<Token> {

	/**
	 * Id that represent the token.
	 */
	public UUID id;

	/**
	 * Id that represents the user belongs the token.
	 */
	public UUID user_id;

	/**
	 * The token value.
	 */
	public String value;

	/**
	 * Indicate if the token is revoked.
	 */
	public Boolean revoked;

	/**
	 * Represents when the token should be expired.
	 */
	public Instant expired_at;

	/**
	 * Check if the tokens are equals.
	 * @param object the token to compare
	 * @return true if are equals else false
	 */
	@Override
	public Boolean IsEqual(Token object) {
		if (object == null) {
			return false;
		}
		return (this.id.equals(object.id) && this.user_id.equals(object.user_id));
	}

	/**
	 * Update the token with the other.
	 * @param object the object with changes
	 */
	@Override
	public void Update(Token object) {
		if (object == null) {
			return;
		}
		this.id = object.id;
		this.user_id = object.user_id;
		this.value = object.value;
		this.revoked = object.revoked;
		this.expired_at = object.expired_at;
	}

}
