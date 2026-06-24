package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Student;

import java.util.UUID;

public interface IStudentRepository {

	Boolean addStudent(Student student);

	Boolean updateStudent(Student student);

	Student getStudent(UUID userId);

}
