package com.zaitec.gs02.domain.models;

import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class Proof implements IEntity<Proof> {

	/**
	 * The id of the proof.
	 */
	public UUID id;

	/**
	 * The proof value.
	 */
	public byte[] proof;

	/**
	 * Compare the current proof with the object.
	 * @param object the object to compare
	 * @return true if are equals else false
	 */
	@Override
	public Boolean IsEqual(Proof object) {
		if (object == null) {
			return false;
		}

		return this.id.equals(object.id);
	}

	/**
	 * Update the current proof.
	 * @param object the object used to update
	 */
	@Override
	public void Update(Proof object) {
		if (object == null) {
			return;
		}
		this.proof = object.proof;
	}

}
