package com.zaitec.gs02.presentation.controllers;

import com.zaitec.gs02.application.dtos.students.CreateStudentRequestDto;
import com.zaitec.gs02.application.dtos.students.UpdateStudentRequestDTO;
import com.zaitec.gs02.application.usecases.students.CreateStudent;
import com.zaitec.gs02.application.usecases.students.GetUserProfile;
import com.zaitec.gs02.application.usecases.students.UpdateStudents;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.presentation.security.Authorize;
import com.zaitec.gs02.presentation.security.CurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

	private final GetUserProfile getUserProfile;

	private final CreateStudent createStudent;

	private final UpdateStudents updateStudents;

	public StudentController(GetUserProfile getUserProfile, UpdateStudents updateStudents,
			CreateStudent createStudent) {

		this.getUserProfile = getUserProfile;
		this.createStudent = createStudent;
		this.updateStudents = updateStudents;
	}

	@GetMapping("/")
	@Authorize
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> getStudent(@CurrentUser User user) {
		var result = this.getUserProfile.execute(user);
		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("student", result.getValue()));
		}
		return ResponseEntity.status(404).body(Map.of("error", "Student not found"));
	}

	@PutMapping("/")
	@Authorize
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, String>> updateStudent(@CurrentUser User user,
			@RequestBody UpdateStudentRequestDTO studentRequest) {
		if (studentRequest.studentID() == null) {
			return ResponseEntity.status(400).body(Map.of("error", "El ID del estudiante es requerido"));
		}
		if (user.id.equals(studentRequest.studentID())) {
			var resultUpdate = this.updateStudents.execute(studentRequest);
			if (resultUpdate.isSuccess()) {
				return ResponseEntity.status(200).body(Map.of("message", resultUpdate.getValue()));
			}
			return ResponseEntity.status(400).body(Map.of("error", resultUpdate.getErrorMessage()));
		}
		return ResponseEntity.status(401).body(Map.of("error", "No tienes permisos"));
	}

	@PostMapping("/")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> createStudent(@RequestBody CreateStudentRequestDto studentRequest) {
		var result = this.createStudent.execute(studentRequest);
		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", "Student created successfully", result.getValue().message(),
					result.getValue().studentId()));
		}
		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage().message()));
	}

}
