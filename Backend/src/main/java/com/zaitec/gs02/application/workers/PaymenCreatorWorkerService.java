package com.zaitec.gs02.application.workers;

import com.zaitec.gs02.domain.models.AcademicCourse;
import com.zaitec.gs02.domain.models.Enrollment;
import com.zaitec.gs02.domain.models.Pay;
import com.zaitec.gs02.domain.repositories.IAcademicCourseRepository;
import com.zaitec.gs02.domain.repositories.IEnrollmentRepository;
import com.zaitec.gs02.domain.repositories.IPayRepository;
import com.zaitec.gs02.domain.shared.PaymentPeriodType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * Scheduled service that automatically generates pending {@link Pay} entities for active
 * {@link Enrollment enrollments} in {@link AcademicCourse academic courses}. It runs
 * daily at midnight (<code>0 0 0 * * *</code>).
 * </p>
 * <h3>Algorithm overview</h3>
 * <ol>
 * <li>Fetch all academic courses that are active.</li>
 * <li>For each course, retrieve all its enrollments.</li>
 * <li>For each enrollment, depending on its payment period type:
 * <ul>
 * <li><strong>MONTHLY (every 28 days)</strong>: build a list of due dates from the course
 * start date, advancing by 28 days while strictly before the end date.</li>
 * <li><strong>YEARLY (every 1 year)</strong>: same logic but advancing by 1 year. If the
 * course length is less than one year, a single payment at the start date is forced.</li>
 * </ul>
 * </li>
 * <li>The total course price is divided equally among the computed number of payments.
 * Any remaining cents are distributed one‑by‑one to the <em>last</em> payments,
 * guaranteeing that the sum of all instalments exactly equals the original price.</li>
 * <li>For each enrollment, the existing payments are loaded and the total already paid is
 * calculated.</li>
 * <li>If the number of existing payments is less than the number of due dates that have
 * already passed (or are today), and the total paid amount is still below the course
 * price, a new payment is created for the next instalment.</li>
 * <li>The last instalment is automatically trimmed so that the accumulated sum never
 * exceeds the course price.</li>
 * </ol>
 * <h3>Important notes</h3>
 * <ul>
 * <li>The due dates do not include the exact end date; payments are only generated for
 * dates strictly before the course end (e.g., a 112‑day course produces 4 payments of 28
 * days each).</li>
 * <li>The service is idempotent: running it multiple times the same day will not create
 * duplicate payments because it checks which dates are already covered.</li>
 * <li>For yearly payments, {@link java.time.Instant} is first converted to
 * {@link java.time.ZonedDateTime} (UTC) because {@code Instant} does not support
 * {@link java.time.temporal.ChronoUnit#YEARS} arithmetic.</li>
 * </ul>
 *
 * @author Sergio López Lobo
 * @see PaymentPeriodType
 * @see AcademicCourse
 * @see Enrollment
 * @see Pay
 */
@Service
public class PaymenCreatorWorkerService {

	private final IAcademicCourseRepository academicCourseRepository;

	private final IEnrollmentRepository enrollmentRepository;

	private final IPayRepository payRepository;

	public PaymenCreatorWorkerService(IAcademicCourseRepository academicCourseRepository,
			IEnrollmentRepository enrollmentRepository, IPayRepository payRepository) {
		this.academicCourseRepository = academicCourseRepository;
		this.enrollmentRepository = enrollmentRepository;
		this.payRepository = payRepository;
	}

	@Scheduled(cron = "0 0 0 * * *")
	void CheckPays() {
		// Get all academic courses that are (or were) active during this year
		var academicCourses = this.academicCourseRepository.findActiveCourses();

		for (AcademicCourse academicCourse : academicCourses) {
			var enrollmentsForAcademicCourse = this.enrollmentRepository.findByAcademicCourseId(academicCourse.id);

			enrollmentsForAcademicCourse.forEach((enrollment) -> {
				if (enrollment.type.equals(PaymentPeriodType.MONTHLY)) {
					monthlyCalc(academicCourse, enrollment);
				}
				else if (enrollment.type.equals(PaymentPeriodType.YEARLY)) {
					yearlyCalc(academicCourse, enrollment);
				}
			});
		}
	}

	private void monthlyCalc(AcademicCourse academicCourse, Enrollment enrollment) {
		int periodDays = 28;
		ChronoUnit unit = ChronoUnit.DAYS;

		List<Instant> dueDates = buildDueDates(academicCourse.start_date, academicCourse.end_date, periodDays, unit);
		int totalPays = dueDates.size();
		if (totalPays == 0) {
			return;
		}

		double[] amounts = distributePrice((double) academicCourse.price, totalPays);

		int payCountDueNow = calculatePaymentsDueCount(dueDates);

		List<Pay> payments = this.payRepository.getByEnrollmentId(enrollment.id);
		double totalPaid = payments.stream().mapToDouble((p) -> p.amount).sum();

		if (payments.size() < payCountDueNow && totalPaid < academicCourse.price) {
			int nextIndex = payments.size();
			double newAmount = amounts[nextIndex];

			if (totalPaid + newAmount > academicCourse.price) {
				newAmount = academicCourse.price - totalPaid;
			}

			generateNewPay(newAmount, enrollment.id, UUID.randomUUID(), enrollment.student_id);
		}
	}

	private void yearlyCalc(AcademicCourse academicCourse, Enrollment enrollment) {
		int periodYears = 1;
		ChronoUnit unit = ChronoUnit.YEARS;

		List<Instant> dueDates = buildDueDates(academicCourse.start_date, academicCourse.end_date, periodYears, unit);
		int totalPays = dueDates.size();
		if (totalPays == 0) {
			dueDates = List.of(academicCourse.start_date);
			totalPays = 1;
		}

		double[] amounts = distributePrice((double) academicCourse.price, totalPays);

		int payCountDueNow = calculatePaymentsDueCount(dueDates);

		List<Pay> payments = this.payRepository.getByEnrollmentId(enrollment.id);
		double totalPaid = payments.stream().mapToDouble((p) -> p.amount).sum();

		if (payments.size() < payCountDueNow && totalPaid < academicCourse.price) {
			int nextIndex = payments.size();
			double newAmount = amounts[nextIndex];

			if (totalPaid + newAmount > academicCourse.price) {
				newAmount = academicCourse.price - totalPaid;
			}

			generateNewPay(newAmount, enrollment.id, UUID.randomUUID(), enrollment.student_id);
		}
	}

	/**
	 * Builds a list of payment due dates from start (inclusive) while date <= end,
	 * advancing by {@code periodAmount} of {@code unit} each step.
	 * @param start the start date
	 * @param end the end date (inclusive)
	 * @param periodAmount the number of units to advance
	 * @param unit the unit of the date
	 * @return list of dates
	 */
	private List<Instant> buildDueDates(Instant start, Instant end, int periodAmount, ChronoUnit unit) {
		List<Instant> dates = new ArrayList<>();
		ZonedDateTime current = start.atZone(ZoneOffset.UTC);
		ZonedDateTime endZoned = end.atZone(ZoneOffset.UTC);
		while (current.isBefore(endZoned)) {
			dates.add(current.toInstant());
			current = current.plus(periodAmount, unit);
		}
		return dates;
	}

	/**
	 * Returns an array of amounts that sum exactly to totalPrice, distributing any
	 * remaining cents across the last installments.
	 * @param totalPrice the total price of installments
	 * @param installments the number of installments
	 * @return array of amounts
	 */
	private double[] distributePrice(Double totalPrice, int installments) {
		long totalCents = Math.round(totalPrice * 100);
		long baseCents = totalCents / installments;
		int remainder = (int) (totalCents % installments);

		double[] amounts = new double[installments];
		for (int i = 0; i < installments; i++) {
			if ((installments - i) > remainder) {
				amounts[i] = baseCents / 100.0;
			}
			else {
				amounts[i] = (baseCents + 1) / 100.0;
			}
		}
		return amounts;
	}

	/**
	 * Counts how many of the given due dates are before or equal to the current instant.
	 * @param dueDates list of dates to check, sorted by earliest first
	 * @return number of installments
	 */
	private int calculatePaymentsDueCount(List<Instant> dueDates) {
		Instant now = Instant.now();
		int count = 0;
		for (Instant due : dueDates) {
			if (!now.isBefore(due)) { // due <= now
				count++;
			}
			else {
				break; // future dates, stop counting
			}
		}
		return count;
	}

	private void generateNewPay(Double newPaymentAmount, UUID enrollmentId, UUID paymentMethodId, UUID studentId) {
		var now = Instant.now();
		Pay pendingPay = new Pay();
		pendingPay.id = UUID.randomUUID();
		pendingPay.studentId = studentId;
		pendingPay.paymentMethodId = paymentMethodId; // Change when implemented
		pendingPay.enrollmentId = enrollmentId;
		pendingPay.validate = false;
		pendingPay.state = false;
		pendingPay.amount = newPaymentAmount;
		pendingPay.updated_at = now;
		pendingPay.created_at = now;
		pendingPay.paymentDate = null;
		pendingPay.validateDate = null;

		this.payRepository.create(pendingPay);
	}

}
