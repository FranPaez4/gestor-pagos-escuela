package com.zaitec.gs02.application.usecases.rooms;

import com.zaitec.gs02.application.dtos.rooms.CreateRoomRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.rooms.abstracs.ICreateRoom;
import com.zaitec.gs02.domain.models.Room;
import com.zaitec.gs02.domain.repositories.IRoomRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateRoom implements ICreateRoom {

	private final IRoomRepository roomRepository;

	public CreateRoom(IRoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Override
	public IResult<String> execute(CreateRoomRequestDto request) {
		if (request.name() == null || request.name().isBlank()) {
			return new Failure<>("El nombre de la sala no puede estar vacío");
		}

		Room newRoom = new Room();
		newRoom.id = UUID.randomUUID();
		newRoom.name = request.name();

		Boolean isAdded = this.roomRepository.addRoom(newRoom);

		if (isAdded) {
			// El "chivato" para Swagger, que no falte
			System.out.println("🚀 SALA CREADA CON ID: " + newRoom.id);
			return new Success<>("Sala creada con éxito");
		}

		return new Failure<>("No se pudo guardar la sala");
	}

}
