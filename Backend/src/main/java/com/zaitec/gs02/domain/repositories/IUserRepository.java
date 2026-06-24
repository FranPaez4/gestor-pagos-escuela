package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {

	List<User> getAll();

	User getById(UUID id);

	User getByEmail(String email);

	List<User> getByRole(UUID roleId);

	Boolean addUser(User user);

	Boolean updateUser(User user);

	Boolean enableUserById(UUID id);

	Boolean disableUserById(UUID id);

}
