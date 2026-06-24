package com.zaitec.gs02.application.dtos.students;

import java.util.Date;

public record StudentProfileResponseDto(String firstName, String lastName, String email, String phoneNumber,
		Date birthDate, String address

) {
}
