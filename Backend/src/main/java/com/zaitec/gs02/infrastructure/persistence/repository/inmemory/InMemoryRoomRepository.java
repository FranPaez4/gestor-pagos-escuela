package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Room;
import com.zaitec.gs02.domain.repositories.IRoomRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryRoomRepository implements IRoomRepository {

	private final ConcurrentLinkedDeque<Room> rooms = new ConcurrentLinkedDeque<>();

	public InMemoryRoomRepository() {

	}

	/**
	 * Add new Room to the repository.
	 * @param room the room to add
	 * @return true if success else false
	 */
	@Override
	public Boolean addRoom(Room room) {
		if (room == null) {
			return false;
		}
		this.rooms.addLast(room);
		return true;
	}

	/**
	 * Get a Room by Id from the repository.
	 * @param roomId the id of the room.
	 * @return the room or null if not found.
	 */
	@Override
	public Room getById(UUID roomId) {
		if (roomId == null) {
			return null;
		}
		return this.rooms.stream().filter((r) -> r.id.equals(roomId)).findFirst().orElse(null);
	}

	/**
	 * Get all rooms in the repository.
	 * @return a list of all rooms or empty if no rooms found
	 */
	@Override
	public List<Room> getAll() {
		return List.copyOf(this.rooms);
	}

	/**
	 * Update a Room in the repository.
	 * @param newRoom the new Room with updated info
	 */
	@Override
	public Boolean update(Room newRoom) {
		return update(newRoom.id, newRoom.name);
	}

	/**
	 * Delete a Room from the repository.
	 * @param roomId the id of the room to update
	 * @param newName the new name for the room.
	 * @return true if success else false
	 */
	@Override
	public Boolean update(UUID roomId, String newName) {
		if (roomId.equals(null) || newName.equals(null) || newName.isEmpty() || newName.isBlank()) {
			return false;
		}
		var result = this.rooms.stream().filter((r) -> r.id.equals(roomId)).findFirst();

		if (result == null) {
			return false;
		}
		result.get().name = newName;
		return true;
	}

}
