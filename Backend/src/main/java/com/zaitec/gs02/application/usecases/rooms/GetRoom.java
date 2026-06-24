package com.zaitec.gs02.application.usecases.rooms;

import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.rooms.abstracs.IGetRoom;
import com.zaitec.gs02.domain.models.Room;
import com.zaitec.gs02.domain.repositories.IRoomRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetRoom implements IGetRoom {

	private final IRoomRepository repository;

	public GetRoom(IRoomRepository repository) {
		this.repository = repository;
	}

	@Override
	public IResult<Room> execute(UUID id) {
		if (id == null) {
			return new Failure<>(null);
		}
		var result = this.repository.getById(id);

		if (result == null) {
			return new Failure<>(null);
		}
		return new Success<>(result);
	}

}
