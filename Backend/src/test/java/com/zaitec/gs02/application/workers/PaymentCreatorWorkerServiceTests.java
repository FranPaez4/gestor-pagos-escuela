package com.zaitec.gs02.application.workers;

import com.zaitec.gs02.domain.models.AcademicCourse;
import com.zaitec.gs02.domain.models.Enrollment;
import com.zaitec.gs02.domain.models.Pay;
import com.zaitec.gs02.domain.repositories.IAcademicCourseRepository;
import com.zaitec.gs02.domain.repositories.IEnrollmentRepository;
import com.zaitec.gs02.domain.repositories.IPayRepository;
import com.zaitec.gs02.domain.shared.PaymentPeriodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.never;

@ExtendWith(MockitoExtension.class)
class PaymentCreatorWorkerServiceTests {

	@Mock
	private IAcademicCourseRepository academicCourseRepository;

	@Mock
	private IEnrollmentRepository enrollmentRepository;

	@Mock
	private IPayRepository payRepository;

	@InjectMocks
	private PaymenCreatorWorkerService service;

	@Captor
	private ArgumentCaptor<Pay> payCaptor;

	private AcademicCourse course;

	private Enrollment monthlyEnrollment;

	private Enrollment yearlyEnrollment;

	private Instant now;

	@BeforeEach
	void setUp() {
		this.now = Instant.now();
		// Base course: 112 days from 10 days ago to 102 days from now (now-10 to now+102)
		Instant start = this.now.minus(10, ChronoUnit.DAYS);
		Instant end = start.plus(112, ChronoUnit.DAYS);
		this.course = new AcademicCourse();
		this.course.id = UUID.randomUUID();
		this.course.start_date = start;
		this.course.end_date = end;
		this.course.price = 1000.0F;

		this.monthlyEnrollment = new Enrollment();
		this.monthlyEnrollment.id = UUID.randomUUID();
		this.monthlyEnrollment.student_id = UUID.randomUUID();
		this.monthlyEnrollment.type = PaymentPeriodType.MONTHLY;

		this.yearlyEnrollment = new Enrollment();
		this.yearlyEnrollment.id = UUID.randomUUID();
		this.yearlyEnrollment.student_id = UUID.randomUUID();
		this.yearlyEnrollment.type = PaymentPeriodType.YEARLY;
	}

	@Test
	void checkPays_noCourses() {
		// given
		given(this.academicCourseRepository.findActiveCourses()).willReturn(Collections.emptyList());

		// when
		this.service.CheckPays();

		// then
		then(this.enrollmentRepository).should(never()).findByAcademicCourseId(any());
		then(this.payRepository).should(never()).create(any());
	}

	@Test
	void checkPays_courseWithNoEnrollments() {
		// given
		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id)).willReturn(Collections.emptyList());

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should(never()).create(any());
	}

	@Test
	void monthly_firstPaymentDueToday_shouldGeneratePay() {
		// given
		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.monthlyEnrollment));
		given(this.payRepository.getByEnrollmentId(this.monthlyEnrollment.id)).willReturn(Collections.emptyList());

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should().create(this.payCaptor.capture());
		Pay generated = this.payCaptor.getValue();

		assertThat(generated.studentId).isEqualTo(this.monthlyEnrollment.student_id);
		assertThat(generated.enrollmentId).isEqualTo(this.monthlyEnrollment.id);
		// 1000 / 4 = 250 since course lasts 112 days -> 4 periods of 28 days
		assertThat(generated.amount).isEqualTo(250.0);
	}

	@Test
	void monthly_courseEnded_allPaymentsAlreadyGenerated_noAction() {
		// given
		Instant start = this.now.minus(200, ChronoUnit.DAYS);
		Instant end = start.plus(112, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = end;

		// 4 payments already generated (all with some amount, doesn't matter)
		Pay p1 = new Pay();
		p1.amount = 250.0;
		Pay p2 = new Pay();
		p2.amount = 250.0;
		Pay p3 = new Pay();
		p3.amount = 250.0;
		Pay p4 = new Pay();
		p4.amount = 250.0;
		List<Pay> existing = List.of(p1, p2, p3, p4);

		given(this.payRepository.getByEnrollmentId(this.monthlyEnrollment.id)).willReturn(existing);
		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.monthlyEnrollment));

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should(never()).create(any());
	}

	@Test
	void monthly_courseEnded_missingPayments_shouldGenerate() {
		// given
		Instant start = this.now.minus(200, ChronoUnit.DAYS);
		Instant end = start.plus(112, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = end;

		// Only 1 payment generated so far
		Pay existingPay = new Pay();
		existingPay.amount = 250.0;
		List<Pay> existing = List.of(existingPay);

		given(this.payRepository.getByEnrollmentId(this.monthlyEnrollment.id)).willReturn(existing);
		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.monthlyEnrollment));

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should().create(this.payCaptor.capture());
		// Next payment should be 250 (second instalment)
		assertThat(this.payCaptor.getValue().amount).isEqualTo(250.0);
	}

	@Test
	void monthly_futureDueDates_noPaymentGenerated() {
		// given
		Instant start = this.now.plus(50, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = start.plus(112, ChronoUnit.DAYS);

		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.monthlyEnrollment));
		given(this.payRepository.getByEnrollmentId(this.monthlyEnrollment.id)).willReturn(Collections.emptyList());

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should(never()).create(any());
	}

	@Test
	void monthly_partiallyPaid_lastPaymentAdjusted() {
		// given: course started 100 days ago, duration 112 days -> 4 payments all due
		Instant start = this.now.minus(100, ChronoUnit.DAYS);
		Instant end = start.plus(112, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = end;
		this.course.price = 1000.0F;

		// 3 existing payments summing up to 900
		Pay p1 = new Pay();
		p1.amount = 250.0;
		Pay p2 = new Pay();
		p2.amount = 250.0;
		Pay p3 = new Pay();
		p3.amount = 400.0; // total 900
		List<Pay> existing = List.of(p1, p2, p3);

		given(this.payRepository.getByEnrollmentId(this.monthlyEnrollment.id)).willReturn(existing);
		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.monthlyEnrollment));

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should().create(this.payCaptor.capture());
		// Fourth payment originally 250, adjusted to 100 so total doesn't exceed 1000
		assertThat(this.payCaptor.getValue().amount).isEqualTo(100.0);
	}

	@Test
	void yearly_firstPaymentDueToday_shouldGeneratePay() {
		// given
		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.yearlyEnrollment));
		given(this.payRepository.getByEnrollmentId(this.yearlyEnrollment.id)).willReturn(Collections.emptyList());

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should().create(this.payCaptor.capture());
		Pay generated = this.payCaptor.getValue();
		assertThat(generated.studentId).isEqualTo(this.yearlyEnrollment.student_id);
		assertThat(generated.enrollmentId).isEqualTo(this.yearlyEnrollment.id);
		// course 112 days -> only 1 annual payment, whole price
		assertThat(generated.amount).isEqualTo(1000.0);
	}

	@Test
	void yearly_twoAnnualPayments_distributePriceEqually() {
		// given
		Instant start = this.now.minus(10, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = start.plus(400, ChronoUnit.DAYS);
		this.course.price = 1200.0F;

		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.yearlyEnrollment));
		given(this.payRepository.getByEnrollmentId(this.yearlyEnrollment.id)).willReturn(Collections.emptyList());

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should().create(this.payCaptor.capture());
		// First annual payment = 1200 / 2 = 600
		assertThat(this.payCaptor.getValue().amount).isEqualTo(600.0);
	}

	@Test
	void yearly_futureDueDate_noPayment() {
		// given
		Instant start = this.now.plus(50, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = start.plus(200, ChronoUnit.DAYS);

		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.yearlyEnrollment));

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should(never()).create(any());
	}

	@Test
	void yearly_allDueDatesPassed_missingPaymentGenerated() {
		// given
		Instant start = this.now.minus(2 * 365, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = start.plus(3 * 365, ChronoUnit.DAYS); // 3 years
		this.course.price = 1500.0F;

		Pay existingPay = new Pay();
		existingPay.amount = 500.0;
		List<Pay> existing = List.of(existingPay);

		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.yearlyEnrollment));
		given(this.payRepository.getByEnrollmentId(this.yearlyEnrollment.id)).willReturn(existing);

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should().create(this.payCaptor.capture());
		// Next payment = second instalment = 500
		assertThat(this.payCaptor.getValue().amount).isEqualTo(500.0);
	}

	@Test
	void yearly_alreadyFullyPaid_noAction() {
		// given
		Instant start = this.now.minus(2 * 365, ChronoUnit.DAYS);
		this.course.start_date = start;
		this.course.end_date = start.plus(3 * 365, ChronoUnit.DAYS);
		this.course.price = 1500.0F;

		Pay p1 = new Pay();
		p1.amount = 500.0;
		Pay p2 = new Pay();
		p2.amount = 500.0;
		Pay p3 = new Pay();
		p3.amount = 500.0;
		List<Pay> existing = List.of(p1, p2, p3);

		given(this.academicCourseRepository.findActiveCourses()).willReturn(List.of(this.course));
		given(this.enrollmentRepository.findByAcademicCourseId(this.course.id))
			.willReturn(List.of(this.yearlyEnrollment));
		given(this.payRepository.getByEnrollmentId(this.yearlyEnrollment.id)).willReturn(existing);

		// when
		this.service.CheckPays();

		// then
		then(this.payRepository).should(never()).create(any());
	}

}
