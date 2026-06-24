package com.zaitec.gs02.application.dtos.pays;

import java.util.UUID;

public record StudentPayRequestDto(UUID studentId, UUID payId, UUID paymentMethod, byte[] proof) {
}
