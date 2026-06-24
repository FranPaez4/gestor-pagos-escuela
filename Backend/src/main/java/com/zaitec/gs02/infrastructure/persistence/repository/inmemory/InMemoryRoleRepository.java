package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Role;
import com.zaitec.gs02.domain.repositories.IRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryRoleRepository implements IRoleRepository {

	private ConcurrentLinkedDeque<Role> roles = new ConcurrentLinkedDeque<>();

	/**
	 * Constructor that populates the repository with two default roles (Admin and
	 * Student).
	 */
	public InMemoryRoleRepository() {
		Role adminRole = new Role();
		adminRole.id = UUID.randomUUID();
		adminRole.name = "Admin";
		this.roles.add(adminRole);
		Role userRole = new Role();
		userRole.id = UUID.randomUUID();
		userRole.name = "Student";
		this.roles.add(userRole);
	}

	/**
	 * This method retrieves all roles.
	 * @return retrieve a list of roles
	 */
	@Override
	public List<Role> getAll() {
		return this.roles.stream().toList();
	}

	/**
	 * This method looks for a Role using its ID.
	 * @param id the id of the Role to look for
	 * @return retrieve the Role that matches with the id
	 */
	@Override
	public Role getById(UUID id) {
		return this.roles.stream().filter((r) -> r.id.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Looks for a role by the name.
	 * @param name the name to find the role
	 * @return retrieve the Role that matches with this name
	 */
	@Override
	public Role getByName(String name) {
		return this.roles.stream().filter((r) -> r.name.equals(name)).findFirst().orElse(null);
	}

	/**
	 * Add a new role to the repository.
	 * @param role - the role to add in the repository
	 * @return return true if the addition was successful else false
	 */
	@Override
	public Boolean addRole(Role role) {
		try {
			if (!isValidRole(role)) {
				return false;
			}
			this.roles.add(role);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Update the role in the repository.
	 * @param role the role modified
	 * @return true if the role can be successfully modified else false
	 */
	@Override
	public Boolean updateRole(Role role) {
		var filterRole = this.roles.stream().filter((r) -> r.IsEqual(role)).findFirst();
		if (filterRole.isEmpty()) {
			return false;
		}
		filterRole.get().Update(role);
		return true;
	}

	/**
	 * Validates if a role is valid before adding it to the repository.
	 * @param role the role to validate
	 * @return true if the role is valid else false
	 */
	private Boolean isValidRole(Role role) {
		if (role == null) {
			return false;
		}
		if (role.id == null) {
			return false;
		}
		return true;
	}

}
