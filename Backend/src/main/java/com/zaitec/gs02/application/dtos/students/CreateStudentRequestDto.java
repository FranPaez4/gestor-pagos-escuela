package com.zaitec.gs02.application.dtos.students;

import java.util.Date;
import java.util.UUID;

public record CreateStudentRequestDto(UUID userID, Date birthDate, String address) {
}
