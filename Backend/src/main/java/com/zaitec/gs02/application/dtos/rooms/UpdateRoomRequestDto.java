package com.zaitec.gs02.application.dtos.rooms;

import java.util.UUID;

public record UpdateRoomRequestDto(UUID id, String name) {
}
