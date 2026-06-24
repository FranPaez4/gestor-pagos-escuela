package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Student;
import com.zaitec.gs02.domain.repositories.IStudentRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryStudentRepository implements IStudentRepository {

	private final ConcurrentLinkedDeque<Student> students = new ConcurrentLinkedDeque<>();

	/**
	 * Add a student to the repository.
	 * @param student the student to add
	 * @return true if is added correctly else false
	 */
	@Override
	public Boolean addStudent(Student student) {
		if (student == null) {
			return false;
		}
		try {
			this.students.add(student);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Update a student in the repository.
	 * @param student the student to update
	 * @return true if is updated correctly else false
	 */
	@Override
	public Boolean updateStudent(Student student) {
		try {
			var currentStudent = this.students.stream().filter((st) -> student.IsEqual(st)).findFirst().orElse(null);
			if (currentStudent == null) {
				return false;
			}
			currentStudent.Update(student);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Get a student from the repository.
	 * @param userId the user id
	 * @return the student
	 */
	@Override
	public Student getStudent(UUID userId) {
		return this.students.stream().filter((st) -> st.id.equals(userId)).findFirst().orElse(null);
	}

}
