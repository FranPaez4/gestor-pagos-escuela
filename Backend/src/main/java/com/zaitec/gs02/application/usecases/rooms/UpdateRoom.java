package com.zaitec.gs02.application.usecases.rooms;

import com.zaitec.gs02.application.dtos.rooms.UpdateRoomRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.rooms.abstracs.IUpdateRoom;
import com.zaitec.gs02.domain.models.Room;
import com.zaitec.gs02.domain.repositories.IRoomRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateRoom implements IUpdateRoom {

	private final IRoomRepository roomRepository;

	public UpdateRoom(IRoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Override
	public IResult<String> execute(UpdateRoomRequestDto request) {
		if (request.id() == null) {
			return new Failure<>("El ID de la sala es obligatorio");
		}
		if (request.name() == null || request.name().isBlank()) {
			return new Failure<>("El nuevo nombre de la sala no puede estar vacío");
		}

		Room existingRoom = this.roomRepository.getById(request.id());

		if (existingRoom == null) {
			return new Failure<>("Sala no encontrada");
		}

		existingRoom.name = request.name();

		Boolean isUpdated = this.roomRepository.update(existingRoom);

		if (isUpdated) {
			return new Success<>("Sala actualizada con éxito");
		}

		return new Failure<>("Error interno al actualizar la sala");
	}

}
