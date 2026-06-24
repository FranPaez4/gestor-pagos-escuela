package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEnrollmentRepository {

	void add(Enrollment enrollment);

	void remove(UUID id);

	Optional<Enrollment> findById(UUID id);

	List<Enrollment> findAll();

	List<Enrollment> findByStudentId(UUID studentId);

	List<Enrollment> findByAcademicCourseId(UUID academicCourseId);

	boolean existsByStudentAndAcademicCourse(UUID studentId, UUID academicCourseId);

}
