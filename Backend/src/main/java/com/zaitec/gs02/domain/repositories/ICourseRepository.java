package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Course;

import java.util.List;
import java.util.UUID;

public interface ICourseRepository {

	/**
	 * Add a new course to the repository.
	 * @param course the course to add.
	 * @return true if is added correctly or else if not.
	 */
	Boolean addCourse(Course course);

	/**
	 * Retrieve a course after look for id.
	 * @param id the id to look for.
	 * @return the course founded or null
	 */
	Course getById(UUID id);

	/**
	 * Retrieve a course after look for name.
	 * @param name the name to look for.
	 * @return the course founded or null.
	 */
	Course getByName(String name);

	/**
	 * Retrieve a list of courses in the repository.
	 * @return list of courses.
	 */
	List<Course> getAll();

	/**
	 * Update the course in the repository.
	 * @param course the course updated (must contain the id).
	 * @return true if is updated else false.
	 */
	Boolean updateCourse(Course course);

	/**
	 * Update the course in the repository.
	 * @param id the id to look for.
	 * @param name the name to update.
	 * @return true if it is updated ele false.
	 */
	Boolean updateCourse(UUID id, String name);

}
