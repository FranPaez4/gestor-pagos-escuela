package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Course;
import com.zaitec.gs02.domain.repositories.ICourseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryCourseRepository implements ICourseRepository {

	private final ConcurrentLinkedDeque<Course> courses = new ConcurrentLinkedDeque();

	public InMemoryCourseRepository() {

	}

	/**
	 * Add a new course to the repository.
	 * @param course the course to add.
	 * @return true if is added correctly or else if not.
	 */
	@Override
	public Boolean addCourse(Course course) {
		if (course == null) {
			return false;
		}
		this.courses.add(course);
		return true;
	}

	/**
	 * Retrieve a course after look for id.
	 * @param id the id to look for.
	 * @return the course founded or null
	 */
	@Override
	public Course getById(UUID id) {
		if (id == null) {
			return null;
		}
		return this.courses.stream().filter((c) -> c.id.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Retrieve a course after look for name.
	 * @param name the name to look for.
	 * @return the course founded or null.
	 */
	@Override
	public Course getByName(String name) {
		return this.courses.stream().filter((c) -> c.name.equals(name)).findFirst().orElse(null);
	}

	/**
	 * Retrieve a list of courses in the repository.
	 * @return list of courses.
	 */
	@Override
	public List<Course> getAll() {
		return List.copyOf(this.courses);
	}

	/**
	 * Update the course in the repository.
	 * @param course the course updated (must contain the id).
	 * @return true if is updated else false.
	 */
	@Override
	public Boolean updateCourse(Course course) {
		if (course == null || course.id == null) {
			return false;
		}
		var result = this.courses.stream().filter((c) -> c.id.equals(course.id)).findFirst().orElse(null);
		if (result == null) {
			return false;
		}
		result.Update(course);
		return true;
	}

	/**
	 * Update the course in the repository.
	 * @param id the id to look for.
	 * @param name the name to update.
	 * @return true if it is updated ele false.
	 */
	@Override
	public Boolean updateCourse(UUID id, String name) {
		if (id == null || name == null) {
			return false;
		}
		var result = this.courses.stream().filter((c) -> c.id.equals(id)).findFirst().orElse(null);
		if (result == null) {
			return false;
		}
		result.name = name;
		return true;
	}

}
