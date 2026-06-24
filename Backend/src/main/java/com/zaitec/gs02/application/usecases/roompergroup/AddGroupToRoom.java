package com.zaitec.gs02.application.usecases.roompergroup;

import com.zaitec.gs02.application.dtos.roompergroup.AddRoomPerGroupRequest;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.roompergroup.abstracts.IAddGroupToRoom;
import com.zaitec.gs02.domain.models.RoomPerGroup;
import com.zaitec.gs02.domain.repositories.IRoomPerGroupRepository;
import org.springframework.stereotype.Service;

@Service
public class AddGroupToRoom implements IAddGroupToRoom {

	private final IRoomPerGroupRepository roomPerGroupRepository;

	public AddGroupToRoom(IRoomPerGroupRepository roomPerGroupRepository) {

		this.roomPerGroupRepository = roomPerGroupRepository;
	}

	@Override
	public IResult<String> execute(AddRoomPerGroupRequest request) {
		try {
			if (request.groupId() == null || request.roomId() == null || request.startTime() == null
					|| request.endTime() == null) {
				return new Failure<>("Faltan datos obligatorios");
			}

			var roompergroup = new RoomPerGroup();
			roompergroup.group_id = request.groupId();
			roompergroup.room_id = request.roomId();
			roompergroup.schedule_init = request.startTime();
			roompergroup.schedule_end = request.endTime();

			this.roomPerGroupRepository.addRoomPerGroup(roompergroup);

			return new Success<>("successful");
		}
		catch (Exception ex) {
			return new Failure<>("No se puedo grabar el registro");
		}
	}

}
