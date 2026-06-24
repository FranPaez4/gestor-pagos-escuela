package com.zaitec.gs02.application.dtos.roompergroup;

import java.time.LocalTime;
import java.util.UUID;

public record AddRoomPerGroupRequest(UUID roomId, UUID groupId, LocalTime startTime, LocalTime endTime) {
}
