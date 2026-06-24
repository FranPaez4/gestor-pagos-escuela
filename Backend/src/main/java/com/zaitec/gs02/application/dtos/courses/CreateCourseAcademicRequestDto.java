package com.zaitec.gs02.application.dtos.courses;

import com.zaitec.gs02.domain.shared.AcademicCourseType;

import java.time.Instant;
import java.util.UUID;

public record CreateCourseAcademicRequestDto(UUID course_id, Instant start_date, Instant end_date, float price,
		AcademicCourseType type) {
}
