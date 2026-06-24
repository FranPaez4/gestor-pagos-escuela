package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Pay;

import java.util.List;
import java.util.UUID;

public interface IPayRepository {

	/**
	 * Create pay.
	 * @param pay pay to create.
	 * @return true if pay is created else false.
	 */
	Boolean create(Pay pay);

	/**
	 * Get all pays.
	 * @return list of pays.
	 */
	List<Pay> getAll();

	/**
	 * Get all pays paginated.
	 * @param page page number.
	 * @param pageSize page size.
	 * @return list of pays.
	 */
	List<Pay> getAllPaginated(int page, int pageSize);

	/**
	 * Get pay by id.
	 * @param id id of pay.
	 * @return pay.
	 */
	Pay getById(UUID id);

	/**
	 * Get pay by student id.
	 * @param studentId id of a student.
	 * @return list of pays.
	 */
	List<Pay> getByStudentId(UUID studentId);

	/**
	 * Get pay by enrollment id.
	 * @param enrollmentId id of an enrollment.
	 * @return list of pays.
	 */
	List<Pay> getByEnrollmentId(UUID enrollmentId);

	/**
	 * Update pay.
	 * @param pay pay to update.
	 * @return true if pay is updated else false.
	 */
	Boolean update(Pay pay);

	/**
	 * Update pay state.
	 * @param pay pay to update. 0: Pendant 1: Payed
	 * @return true if pay is updated else false.
	 */
	Boolean updateState(Pay pay);

	/**
	 * Update pay validate.
	 * @param pay pay to update. 0: Not validated 1: Validated
	 * @return true if pay is updated else false.
	 */
	Boolean updateValidate(Pay pay);

}
