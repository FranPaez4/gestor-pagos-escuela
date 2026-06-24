package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Group;
import com.zaitec.gs02.domain.shared.Level;

import java.util.List;
import java.util.UUID;

public interface IGroupRepository {

	/**
	 * Add group.
	 * @param group group to add.
	 * @return true if group is added else false.
	 */
	Boolean addGroup(Group group);

	/**
	 * Get group by id.
	 * @param id id of group.
	 * @return group.
	 */
	Group getById(UUID id);

	/**
	 * Get groups by level.
	 * @param level level of groups.
	 * @return list of groups.
	 */
	List<Group> getByLevel(Level level);

	/**
	 * Get group by name.
	 * @param name name of group.
	 * @return group.
	 */
	Group getByName(String name);

	/**
	 * Get groups by academic course.
	 * @param academicCourseId academic course id.
	 * @return list of groups.
	 */
	List<Group> getByAcademicCourse(UUID academicCourseId);

	/**
	 * Update group.
	 * @param group group to update.
	 * @return true if group is updated else false.
	 */
	Boolean updateGroup(Group group);

	/**
	 * Delete group by id.
	 * @param id id of group.
	 * @return true if group is deleted else false.
	 */
	Boolean deleteGroup(UUID id);

}
