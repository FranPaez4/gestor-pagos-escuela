package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.RoomPerGroup;
import com.zaitec.gs02.domain.repositories.IRoomPerGroupRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * interface/implementation for room per group repository.
 *
 * @author Alex Lagares
 */
@Repository
public class InMemoryRoomPerGroupRepository implements IRoomPerGroupRepository {

	private final ConcurrentLinkedDeque<RoomPerGroup> store = new ConcurrentLinkedDeque<>();

	/**
	 * constructor for InMemoryRoomPerGroupRepository.
	 */
	public InMemoryRoomPerGroupRepository() {
		// empty constructor for spring.
	}

	@Override
	public Boolean addRoomPerGroup(RoomPerGroup roomPerGroup) {
		if (roomPerGroup == null) {
			return false;
		}
		return this.store.add(roomPerGroup);
	}

	@Override
	public RoomPerGroup getByGroupAndRoom(UUID groupId, UUID roomId) {
		return this.store.stream()
			.filter((r) -> r.group_id.equals(groupId) && r.room_id.equals(roomId))
			.findFirst()
			.orElse(null);
	}

	@Override
	public List<RoomPerGroup> getByGroup(UUID groupId) {
		// CORREGIDO: List<RoomPerGroup> en lugar de List<Group>
		return List.copyOf(this.store.stream().filter((r) -> r.group_id.equals(groupId)).toList());
	}

	@Override
	public Boolean updateRoomPerGroup(RoomPerGroup roomPerGroup) {
		if (roomPerGroup == null) {
			return false;
		}
		var existing = getByGroupAndRoom(roomPerGroup.group_id, roomPerGroup.room_id);
		if (existing == null) {
			return false;
		}
		existing.Update(roomPerGroup);
		return true;
	}

	@Override
	public Boolean deleteRoomPerGroup(UUID groupId, UUID roomId) {
		if (groupId == null || roomId == null) {
			return false;
		}
		return this.store.removeIf((r) -> r.group_id.equals(groupId) && r.room_id.equals(roomId));
	}

}
