package com.zaitec.gs02.application.dtos.courses;

import java.util.UUID;

public record UpdateCourseRequestDto(UUID id, String name) {
}
