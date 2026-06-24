package com.zaitec.gs02.domain.models;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import com.zaitec.gs02.domain.models.abstracts.IEntity;

public class Pay implements IEntity<Pay> {

	/**
	 * The id of the pay.
	 */
	public UUID id;

	/**
	 * The id of the user.
	 */
	public UUID studentId;

	/**
	 * The id of the enrollment.
	 */
	public UUID enrollmentId;

	/**
	 * The id of the payment method.
	 */
	public UUID paymentMethodId;

	/**
	 * The state of the pay.
	 */
	public Boolean state;

	/**
	 * The validating of the pay.
	 */
	public Boolean validate;

	/**
	 * The amount of the pay.
	 */
	public Double amount;

	/**
	 * The date of the payment.
	 */
	public Instant paymentDate;

	/**
	 * The date of the validate.
	 */
	public Instant validateDate;

	/**
	 * The date of the creation.
	 */
	public Instant created_at;

	/**
	 * The date of the last modification.
	 */
	public Instant updated_at;

	/**
	 * Compare the current pay with the object.
	 * @param object the object to compare
	 * @return true if are equals else false
	 */
	@Override
	public Boolean IsEqual(Pay object) {
		if (object == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	/**
	 * Update the current pay.
	 * @param object the object used to update
	 */
	@Override
	public void Update(Pay object) {
		if (object == null) {
			return;
		}
		this.paymentMethodId = Optional.ofNullable(object.paymentMethodId).orElse(this.paymentMethodId);
		this.state = Optional.ofNullable(object.state).orElse(this.state);
		this.validate = Optional.ofNullable(object.state).orElse(this.state);
		this.amount = Optional.ofNullable(object.amount).orElse(this.amount);
		this.paymentDate = Optional.ofNullable(object.paymentDate).orElse(this.paymentDate);
		this.validateDate = Optional.ofNullable(object.validateDate).orElse(this.validateDate);
		this.updated_at = Instant.now();
	}

}
