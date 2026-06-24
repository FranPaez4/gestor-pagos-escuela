package com.zaitec.gs02.application.dtos.students;

import java.util.Date;
import java.util.UUID;

public record UpdateStudentRequestDTO(UUID studentID, String firstName, String lastName, String email,
		String phoneNumber, Date birthDate, String address

) {
}
