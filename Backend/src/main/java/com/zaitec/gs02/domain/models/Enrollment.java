package com.zaitec.gs02.domain.models;

import com.zaitec.gs02.domain.models.abstracts.IEntity;
import com.zaitec.gs02.domain.shared.PaymentPeriodType;

import java.time.Instant;
import java.util.UUID;

public class Enrollment implements IEntity<Enrollment> {

	/** The identifier of the enrollment. */
	public UUID id;

	/** The identifier of the student. */
	public UUID student_id;

	/** The identifier of the academic course. */
	public UUID academic_course_id;

	/** Snapshot of the price at the moment of enrollment. */
	public float price;

	/** Billing cycle inherited from AcademicCourse at enrollment time. */
	public PaymentPeriodType type;

	/** Whether the enrollment is currently active. */
	public Boolean active;

	/** When the enrollment was created. */
	public Instant created_at;

	@Override
	public Boolean IsEqual(Enrollment object) {
		if (object == null) {
			return false;
		}
		return this.id.equals(object.id);
	}

	@Override
	public void Update(Enrollment object) {
		if (object == null) {
			return;
		}
		this.active = object.active;
	}

}
