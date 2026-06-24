package com.zaitec.gs02.Repositories.inmemory;

import com.zaitec.gs02.domain.models.Student;
import com.zaitec.gs02.domain.repositories.IStudentRepository;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class InMemoryStudentRepositoryTests {

	@Autowired
	IStudentRepository repository;

	@Test
	@Order(1)
	void New_Student_Is_Added_Correctly() {
		Student student = new Student();
		student.id = UUID.randomUUID();
		student.birthDate = new Date();
		student.address = "Calle Falsa 123";
		student.created_at = Instant.now();
		student.updated_at = Instant.now();

		var result = this.repository.addStudent(student);

		assertThat(result).isTrue();
	}

	@Test
	@Order(2)
	void New_Student_Is_Added_As_Null_Return_False() {
		var result = this.repository.addStudent(null);
		assertThat(result).isFalse();
	}

	@Test
	@Order(3)
	void Get_Student_By_Id_Correctly() {
		UUID testUUID = UUID.randomUUID();

		Student student1 = new Student();
		student1.id = testUUID;
		student1.birthDate = new Date();
		student1.address = "Calle Real 1";
		student1.created_at = Instant.now();
		student1.updated_at = Instant.now();

		Student student2 = new Student();
		student2.id = UUID.randomUUID();
		student2.birthDate = new Date();
		student2.address = "Calle Real 2";
		student2.created_at = Instant.now();
		student2.updated_at = Instant.now();

		this.repository.addStudent(student2);
		this.repository.addStudent(student1);

		var result = this.repository.getStudent(testUUID);

		assertThat(result).isNotNull();
		assertThat(result.id).isSameAs(testUUID);
	}

	@Test
	@Order(4)
	void Get_Student_By_Id_Returns_Null_If_Not_Found() {
		var result = this.repository.getStudent(UUID.randomUUID());
		assertThat(result).isNull();
	}

	@Test
	@Order(5)
	void Update_Student_Correctly() {
		UUID testUUID = UUID.randomUUID();

		Student student = new Student();
		student.id = testUUID;
		student.birthDate = new Date();
		student.address = "Direccion Antigua";
		student.created_at = Instant.now();
		student.updated_at = Instant.now();

		this.repository.addStudent(student);

		Student updated = new Student();
		updated.id = testUUID;
		updated.birthDate = new Date();
		updated.address = "Direccion Nueva";
		updated.created_at = student.created_at;
		updated.updated_at = Instant.now();

		var result = this.repository.updateStudent(updated);
		var retrieved = this.repository.getStudent(testUUID);

		assertThat(result).isTrue();
		assertThat(retrieved).isNotNull();
		assertThat(retrieved.address).isEqualTo("Direccion Nueva");
	}

	@Test
	@Order(6)
	void Update_Student_Returns_False_When_Not_Found() {
		Student student = new Student();
		student.id = UUID.randomUUID();
		student.birthDate = new Date();
		student.address = "No existe";
		student.created_at = Instant.now();
		student.updated_at = Instant.now();

		var result = this.repository.updateStudent(student);

		assertThat(result).isFalse();
	}

	@Test
	@Order(7)
	void Update_Student_Returns_False_When_Null() {
		var result = this.repository.updateStudent(null);
		assertThat(result).isFalse();
	}

}
