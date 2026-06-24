package com.zaitec.gs02.presentation.dtos.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaitec.gs02.application.dtos.auth.RegisterRequestDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class RegisterRequestDtoTests {

	@Test
	void To_User_Maps_Register_Request_Correctly() {
		RegisterRequestDto request = new RegisterRequestDto("Sergio", "Lopez", "test@test.com", "plain-password");

		var user = request.toUser("hashed-password", UUID.randomUUID());

		assertThat(user.id).isNotNull();
		assertThat(user.role_id).isNotNull();
		assertThat(user.name).isEqualTo("Sergio");
		assertThat(user.email).isEqualTo("test@test.com");
		assertThat(user.password).isEqualTo("hashed-password");
		assertThat(user.enabled).isFalse();
		assertThat(user.created_at).isNotNull();
		assertThat(user.updated_at).isNotNull();
	}

}
