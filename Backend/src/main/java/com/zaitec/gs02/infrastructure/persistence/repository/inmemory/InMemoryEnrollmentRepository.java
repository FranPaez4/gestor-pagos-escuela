package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Enrollment;
import com.zaitec.gs02.domain.repositories.IEnrollmentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryEnrollmentRepository implements IEnrollmentRepository {

	@Override
	public void add(Enrollment enrollment) {

	}

	@Override
	public void remove(UUID id) {

	}

	@Override
	public Optional<Enrollment> findById(UUID id) {
		return Optional.empty();
	}

	@Override
	public List<Enrollment> findAll() {
		return List.of();
	}

	@Override
	public List<Enrollment> findByStudentId(UUID studentId) {
		return List.of();
	}

	@Override
	public List<Enrollment> findByAcademicCourseId(UUID academicCourseId) {
		return List.of();
	}

	@Override
	public boolean existsByStudentAndAcademicCourse(UUID studentId, UUID academicCourseId) {
		return false;
	}

}
