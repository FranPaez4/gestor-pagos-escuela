package com.zaitec.gs02.application.dtos.auth;

import com.zaitec.gs02.domain.models.User;

import java.time.Instant;
import java.util.UUID;

public record RegisterRequestDto(String username, String userlastname, String email, String password) {

	public User toUser(String hashedPassword, UUID roleId) {
		User user = new User();
		Instant now = Instant.now();
		user.id = UUID.randomUUID();
		user.role_id = roleId;
		user.name = this.username();
		user.lastname = this.userlastname();
		user.email = this.email();
		user.password = hashedPassword;
		user.enabled = false;
		user.created_at = now;
		user.updated_at = now;
		return user;
	}
}
