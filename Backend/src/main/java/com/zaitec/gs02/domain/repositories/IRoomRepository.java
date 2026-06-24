package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Room;

import java.util.List;
import java.util.UUID;

public interface IRoomRepository {

	/**
	 * Add new Room to the repository.
	 * @param room the room to add
	 * @return true if success else false
	 */
	Boolean addRoom(Room room);

	/**
	 * Get a Room by Id from the repository.
	 * @param roomId the id of the room.
	 * @return the room or null if not found.
	 */
	Room getById(UUID roomId);

	/**
	 * Get all rooms in the repository.
	 * @return a list of all rooms or empty if no rooms found
	 */
	List<Room> getAll();

	/**
	 * Update a Room in the repository.
	 * @param newRoom the Room object with updated values
	 * @return true if success else false
	 */
	Boolean update(Room newRoom);

	/**
	 * Delete a Room from the repository.
	 * @param roomId the id of the room to update
	 * @param newName the new name for the room.
	 * @return true if success else false
	 */
	Boolean update(UUID roomId, String newName);

}
