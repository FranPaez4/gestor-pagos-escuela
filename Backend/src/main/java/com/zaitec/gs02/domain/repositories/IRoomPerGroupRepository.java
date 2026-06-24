package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.RoomPerGroup;
import java.util.List;
import java.util.UUID;

/**
 * interface/implementation for room per group repository.
 *
 * @author Alex Lagares
 */
public interface IRoomPerGroupRepository {

	/**
	 * add room per group relationship.
	 * @param roomPerGroup the entity to add.
	 * @return true if added.
	 */
	Boolean addRoomPerGroup(RoomPerGroup roomPerGroup);

	/**
	 * get relationship by composite key.
	 * @param groupId id of the group.
	 * @param roomId id of the room.
	 * @return the relationship found.
	 */
	RoomPerGroup getByGroupAndRoom(UUID groupId, UUID roomId);

	/**
	 * get all relationships for a specific group.
	 * @param groupId the group id.
	 * @return list of room per group relationships.
	 */
	List<RoomPerGroup> getByGroup(UUID groupId);

	/**
	 * update the relationship.
	 * @param roomPerGroup the entity to update.
	 * @return true if updated.
	 */
	Boolean updateRoomPerGroup(RoomPerGroup roomPerGroup);

	/**
	 * delete relationship.
	 * @param groupId id of the group.
	 * @param roomId id of the room.
	 * @return true if deleted.
	 */
	Boolean deleteRoomPerGroup(UUID groupId, UUID roomId);

}
