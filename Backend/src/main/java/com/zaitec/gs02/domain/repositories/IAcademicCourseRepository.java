package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.AcademicCourse;
import com.zaitec.gs02.domain.shared.AcademicCourseType;

import java.util.List;
import java.util.UUID;

public interface IAcademicCourseRepository {

	/**
	 * Add academic course.
	 * @param academicCourse academic course to add.
	 * @return true if academic course is added else false.
	 */
	Boolean addAcademicCourse(AcademicCourse academicCourse);

	/**
	 * Get academic course by id.
	 * @param id id of academic course.
	 * @return academic course.
	 */
	AcademicCourse getById(UUID id);

	/**
	 * Get academic courses by start year.
	 * @param year year of academic courses.
	 * @return list of academic courses.
	 */
	List<AcademicCourse> getByStartYear(int year);

	/**
	 * Get academic courses by end year.
	 * @param year year of academic courses.
	 * @return list of academic courses.
	 */
	List<AcademicCourse> getByEndYear(int year);

	/**
	 * Get active academic courses by year.
	 * @return list of active academic courses.
	 */
	List<AcademicCourse> findActiveCourses();

	/**
	 * Get academic courses by type.
	 * @param type type of academic courses.
	 * @return list of academic courses.
	 */
	List<AcademicCourse> getByType(AcademicCourseType type);

	/**
	 * Get academic courses by course.
	 * @param courseId course id
	 * @return list of academic courses.
	 */
	List<AcademicCourse> getByCourse(UUID courseId);

	/**
	 * Update academic course.
	 * @param academicCourse academic course to update.
	 * @return true if academic course is updated else false.
	 */
	Boolean updateAcademicCourse(AcademicCourse academicCourse);

}
