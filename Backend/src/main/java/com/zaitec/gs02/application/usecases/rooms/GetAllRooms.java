package com.zaitec.gs02.application.usecases.rooms;

import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.rooms.abstracs.IGetAllRooms;
import com.zaitec.gs02.domain.models.Room;
import com.zaitec.gs02.domain.repositories.IRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllRooms implements IGetAllRooms {

	private final IRoomRepository repository;

	public GetAllRooms(IRoomRepository repository) {
		this.repository = repository;
	}

	@Override
	public IResult<List<Room>> execute(Object o) {
		var result = this.repository.getAll();
		if (result == null) {
			return new Failure<>(null);
		}
		return new Success<>(result);
	}

}
