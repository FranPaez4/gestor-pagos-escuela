package com.zaitec.gs02.application.dtos.pays;

import java.util.UUID;

public record StudentGetSpecificPayRequestDto(UUID studentId, UUID payId) {
}
