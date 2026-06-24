package com.zaitec.gs02.application.dtos.Groups;

import com.zaitec.gs02.domain.shared.Level;

import java.util.UUID;

public record UpdateGroupRequestDto(UUID id, UUID academic_course_id, String name, Level level, String start_time,
		String end_time) {
}
