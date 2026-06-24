package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryUserRepository implements IUserRepository {

	private ConcurrentLinkedDeque<User> users = new ConcurrentLinkedDeque<>();

	/**
	 * This method retrieve all users.
	 * @return retrieve a list of users
	 */
	@Override
	public List<User> getAll() {
		return this.users.stream().toList();
	}

	/**
	 * This method looks for a User using his ID.
	 * @param id the id of the User to look for
	 * @return retrieve the User that match with the id
	 */
	@Override
	public User getById(UUID id) {
		return this.users.stream().filter((u) -> u.id.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Looks for a user by the email.
	 * @param email the email to find the user
	 * @return retrieve the User that match with this email
	 */
	@Override
	public User getByEmail(String email) {
		return this.users.stream().filter((u) -> u.email.equals(email)).findFirst().orElse(null);
	}

	/**
	 * THis method returns he users that match the role.
	 * @param roleId is the UUID belongs to a role_id
	 * @return retrieve a list of users that match with this role
	 */
	@Override
	public List<User> getByRole(UUID roleId) {
		return this.users.stream().filter((u) -> u.role_id.equals(roleId)).toList();
	}

	/**
	 * Add a new user to the repository.
	 * @param user - the user to add in the repository
	 * @return return true if the addition was successfully else false
	 */
	@Override
	public Boolean addUser(User user) {
		try {
			if (!IsValidUser(user)) {
				return false;
			}
			this.users.add(user);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Update the user of the repository.
	 * @param user the user modified
	 * @return true if the user can be successfully modified else false
	 */
	@Override
	public Boolean updateUser(User user) {
		var filterUser = this.users.stream().filter((u) -> u.IsEqual(user)).findFirst();
		if (filterUser.isEmpty()) {
			return false;
		}
		filterUser.get().Update(user);
		return true;
	}

	/**
	 * Enable one user.
	 * @param id the id of the user to enable
	 * @return true if the user was enabled correctly else false
	 */
	@Override
	public Boolean enableUserById(UUID id) {
		var user = this.users.stream().filter((u) -> u.id.equals(id)).findFirst();
		if (user.isPresent()) {
			user.get().enabled = true;
			return true;
		}
		return false;
	}

	/**
	 * Disable de user.
	 * @param id the id of the user to disable
	 * @return true if the user was disabled correctly else false
	 */
	@Override
	public Boolean disableUserById(UUID id) {
		var user = this.users.stream().filter((u) -> u.id.equals(id)).findFirst();
		if (user.isPresent()) {
			user.get().enabled = false;
			return true;
		}
		return false;
	}

	private Boolean IsValidUser(User user) {
		if (user == null) {
			return false;
		}
		if (user.id.equals(null)) {
			return false;
		}
		return true;
	}

}
