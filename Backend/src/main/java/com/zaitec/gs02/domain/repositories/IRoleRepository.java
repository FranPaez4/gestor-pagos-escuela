package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleRepository {

	List<Role> getAll();

	Role getById(UUID id);

	Role getByName(String name);

	Boolean addRole(Role role);

	Boolean updateRole(Role role);

}
