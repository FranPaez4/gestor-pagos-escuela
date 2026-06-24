package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Group;
import com.zaitec.gs02.domain.shared.Level;
import com.zaitec.gs02.domain.repositories.IGroupRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryGroupRepository implements IGroupRepository {

	private final ConcurrentLinkedDeque<Group> groups = new ConcurrentLinkedDeque<>();

	/**
	 * constructor for inMemoryGroupRepository.
	 */
	public InMemoryGroupRepository() {
		// Constructor vacío intencionado para la inyección de dependencias de Spring
	}

	/**
	 * Add group.
	 * @param group group to add.
	 * @return true if group is added else false.
	 */
	@Override
	public Boolean addGroup(Group group) {
		if (group == null) {
			return false;
		}
		this.groups.add(group);
		return true;
	}

	/**
	 * Get group by id.
	 * @param id id of group.
	 * @return group.
	 */
	@Override
	public Group getById(UUID id) {
		if (id == null) {
			return null;
		}
		return this.groups.stream().filter((group) -> group.id.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Get groups by level.
	 * @param level level of groups.
	 * @return list of groups.
	 */
	@Override
	public List<Group> getByLevel(Level level) {
		return List.copyOf(this.groups.stream().filter((group) -> group.level.equals(level)).toList());
	}

	/**
	 * Get group by name.
	 * @param name name of group.
	 * @return group.
	 */
	@Override
	public Group getByName(String name) {
		if (name == null) {
			return null;
		}
		return this.groups.stream().filter((group) -> group.name.equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	/**
	 * Get groups by academic course.
	 * @param academicCourseId academic course id.
	 * @return list of groups.
	 */
	@Override
	public List<Group> getByAcademicCourse(UUID academicCourseId) {
		if (academicCourseId == null) {
			return List.of();
		}
		return List
			.copyOf(this.groups.stream().filter((group) -> group.academic_course_id.equals(academicCourseId)).toList());
	}

	/**
	 * Update group.
	 * @param group group to update.
	 * @return true if group is updated else false.
	 */
	@Override
	public Boolean updateGroup(Group group) {
		if (group == null) {
			return false;
		}
		var currentGroup = this.groups.stream().filter((g) -> g.id.equals(g.id)).findFirst();
		if (currentGroup.isEmpty()) {
			return false;
		}
		currentGroup.get().Update(group);
		return true;
	}

	/**
	 * Delete group by id.
	 * @param id id of group to delete.
	 * @return true if group is deleted else false.
	 */
	@Override
	public Boolean deleteGroup(UUID id) {
		if (id == null) {
			return false;
		}
		return this.groups.removeIf((group) -> group.id.equals(id));
	}

}
