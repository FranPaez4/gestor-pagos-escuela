package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.AcademicCourse;
import com.zaitec.gs02.domain.repositories.IAcademicCourseRepository;
import com.zaitec.gs02.domain.shared.AcademicCourseType;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryAcademicCourseRepository implements IAcademicCourseRepository {

	private final ConcurrentLinkedDeque<AcademicCourse> academicCourses = new ConcurrentLinkedDeque<>();

	public InMemoryAcademicCourseRepository() {
	}

	/**
	 * Add academic course.
	 * @param academicCourse academic course to add.
	 * @return true if academic course is added else false.
	 */
	@Override
	public Boolean addAcademicCourse(AcademicCourse academicCourse) {
		if (academicCourse == null) {
			return false;
		}
		this.academicCourses.add(academicCourse);
		return true;
	}

	/**
	 * Get academic course by id.
	 * @param id id of academic course.
	 * @return academic course.
	 */
	@Override
	public AcademicCourse getById(UUID id) {
		if (id == null) {
			return null;
		}
		return this.academicCourses.stream()
			.filter((academicCourse) -> academicCourse.id.equals(id))
			.findFirst()
			.orElse(null);
	}

	/**
	 * Get active courses by year.
	 * @return list of active academic courses
	 */
	@Override
	public List<AcademicCourse> findActiveCourses() {
		Instant now = Instant.now();
		return List.copyOf(
				this.academicCourses.stream().filter((c) -> c.end_date != null && c.end_date.isAfter(now)).toList());
	}

	/**
	 * Get academic courses by year.
	 * @param year year of academic courses.
	 * @return list of academic courses.
	 */
	@Override
	public List<AcademicCourse> getByEndYear(int year) {
		return List.copyOf(this.academicCourses.stream()
			.filter((academicCourse) -> academicCourse.end_date
				.isBefore(ZonedDateTime.of(year + 1, 0, 0, 0, 0, 0, 0, ZoneId.of("Europe/Spain")).toInstant()))
			.toList());
	}

	/**
	 * Get academic courses by year.
	 * @param year year of academic courses.
	 * @return list of academic courses.
	 */
	@Override
	public List<AcademicCourse> getByStartYear(int year) {
		return List.copyOf(this.academicCourses.stream()
			.filter((academicCourse) -> academicCourse.start_date
				.isAfter(ZonedDateTime.of(year - 1, 12, 24, 0, 0, 0, 0, ZoneId.of("Europe/Spain")).toInstant()))
			.toList());
	}

	/**
	 * Get academic courses by type.
	 * @param type type of academic courses.
	 * @return list of academic courses.
	 */
	@Override
	public List<AcademicCourse> getByType(AcademicCourseType type) {
		return List.copyOf(
				this.academicCourses.stream().filter((academicCourse) -> academicCourse.type.equals(type)).toList());
	}

	/**
	 * Get academic courses by course.
	 * @param courseId course id
	 * @return list of academic courses.
	 */
	@Override
	public List<AcademicCourse> getByCourse(UUID courseId) {
		return List.copyOf(this.academicCourses.stream()
			.filter((academicCourse) -> academicCourse.course_id.equals(courseId))
			.toList());
	}

	/**
	 * Update academic course.
	 * @param academicCourse academic course to update.
	 * @return true if academic course is updated else false.
	 */
	@Override
	public Boolean updateAcademicCourse(AcademicCourse academicCourse) {
		if (academicCourse == null) {
			return false;
		}
		var currentAcademicCourse = this.academicCourses.stream()
			.filter((academicCourse1) -> academicCourse.id.equals(academicCourse1.id))
			.findFirst();
		if (currentAcademicCourse.isEmpty()) {
			return false;
		}
		currentAcademicCourse.get().Update(academicCourse);
		return true;
	}

}
