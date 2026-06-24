package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Pay;
import com.zaitec.gs02.domain.repositories.IPayRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryPayRepository implements IPayRepository {

	private final ConcurrentLinkedDeque<Pay> pays = new ConcurrentLinkedDeque<>();

	/**
	 * Create pay.
	 * @param pay pay to create.
	 * @return true if pay is created else false.
	 */
	@Override
	public Boolean create(Pay pay) {
		if (pay == null) {
			return false;
		}
		this.pays.add(pay);
		return true;
	}

	/**
	 * Get all pays.
	 * @return list of pays.
	 */
	@Override
	public List<Pay> getAll() {
		return List.copyOf(this.pays.stream().toList());
	}

	/**
	 * Get all pays paginated.
	 * @param page page number.
	 * @param pageSize page size.
	 * @return list of pays.
	 */
	@Override
	public List<Pay> getAllPaginated(int page, int pageSize) {
		return List.copyOf(this.pays.stream().skip((long) (page - 1) * pageSize).limit(pageSize).toList());
	}

	/**
	 * Get pay by id.
	 * @param id id of pay.
	 * @return pay.
	 */
	@Override
	public Pay getById(UUID id) {
		return this.pays.stream().filter((pay) -> pay.id.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Get pay by student id.
	 * @param studentId id of a student.
	 * @return list of pays.
	 */
	@Override
	public List<Pay> getByStudentId(UUID studentId) {
		return List.copyOf(this.pays.stream().filter((pay) -> pay.studentId.equals(studentId)).toList());
	}

	/**
	 * Get pay by enrollment id.
	 * @param enrollmentId id of an enrollment.
	 * @return list of pays.
	 */
	@Override
	public List<Pay> getByEnrollmentId(UUID enrollmentId) {
		return List.copyOf(this.pays.stream().filter((pay) -> pay.enrollmentId.equals(enrollmentId)).toList());
	}

	/**
	 * Update pay.
	 * @param pay pay to update.
	 * @return true if pay is updated else false.
	 */
	@Override
	public Boolean update(Pay pay) {
		if (pay == null) {
			return false;
		}
		var currentPay = this.pays.stream().filter((p) -> p.id.equals(pay.id)).findFirst();
		if (currentPay.isEmpty()) {
			return false;
		}
		currentPay.get().Update(pay);
		currentPay.get().updated_at = Instant.now();
		return true;
	}

	/**
	 * Update pay state.
	 * @param pay pay to update. 0: Pendant 1: Payed
	 * @return true if pay is updated else false.
	 */
	@Override
	public Boolean updateState(Pay pay) {
		if (pay == null) {
			return false;
		}
		var currentPay = this.pays.stream().filter((p) -> p.id.equals(pay.id)).findFirst();
		if (currentPay.isEmpty()) {
			return false;
		}
		currentPay.get().state = pay.state;
		currentPay.get().paymentMethodId = pay.paymentMethodId;
		currentPay.get().updated_at = Instant.now();
		return true;
	}

	/**
	 * Update pay validate.
	 * @param pay pay to update. 0: Not validated 1: Validated
	 * @return true if pay is updated else false.
	 */
	@Override
	public Boolean updateValidate(Pay pay) {
		if (pay == null) {
			return false;
		}
		var currentPay = this.pays.stream().filter((p) -> p.id.equals(pay.id)).findFirst();
		if (currentPay.isEmpty()) {
			return false;
		}
		currentPay.get().validate = pay.validate;
		currentPay.get().updated_at = Instant.now();
		return true;
	}

}
