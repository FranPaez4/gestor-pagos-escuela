package com.zaitec.gs02.application.usecases.rooms.abstracs;

import com.zaitec.gs02.application.usecases.abstracts.IUseCase;
import com.zaitec.gs02.domain.models.Room;

import java.util.UUID;

public interface IGetRoom extends IUseCase<UUID, Room> {

}
