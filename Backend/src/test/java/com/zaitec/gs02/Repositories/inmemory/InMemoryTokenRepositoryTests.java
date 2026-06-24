package com.zaitec.gs02.Repositories.inmemory;

import com.zaitec.gs02.domain.models.Token;
import com.zaitec.gs02.domain.repositories.ITokenRepository;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class InMemoryTokenRepositoryTests {

	@Autowired
	ITokenRepository repository;

	@Test
	@Order(1)
	void Add_New_Token() {
		var token = new Token();
		token.id = UUID.randomUUID();
		token.user_id = UUID.randomUUID();
		token.value = "token";
		token.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token.revoked = false;

		var result = this.repository.AddToken(token);
		assertThat(result).isTrue();
	}

	@Test
	@Order(2)
	void Get_Token_By_Id() {
		var token = new Token();
		token.id = UUID.randomUUID();
		token.user_id = UUID.randomUUID();
		token.value = "token";
		token.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token.revoked = false;

		this.repository.AddToken(token);

		var retrievedToken = this.repository.GetTokenById(token.id);
		assertThat(retrievedToken).isNotNull();
	}

	@Test
	@Order(3)
	void Get_Token_By_User_Id() {
		var token = new Token();
		token.id = UUID.randomUUID();
		token.user_id = UUID.randomUUID();
		token.value = "token";
		token.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token.revoked = false;

		this.repository.AddToken(token);

		var retrievedToken = this.repository.GetTokenByUserId(token.user_id);
		assertThat(retrievedToken).isNotNull();
	}

	@Test
	@Order(4)
	void Get_All_Tokens() {
		var token = new Token();
		token.id = UUID.randomUUID();
		token.user_id = UUID.randomUUID();
		token.value = "token";
		token.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token.revoked = false;
		this.repository.AddToken(token);

		var token1 = new Token();
		token1.id = UUID.randomUUID();
		token1.user_id = UUID.randomUUID();
		token1.value = "token";
		token1.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token1.revoked = false;
		this.repository.AddToken(token1);

		assertThat(this.repository.GetAll()).hasSize(6);
	}

	@Test
	@Order(5)
	void Get_All_Expired_Tokens() {
		var token = new Token();
		token.id = UUID.randomUUID();
		token.user_id = UUID.randomUUID();
		token.value = "token";
		token.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token.revoked = false;
		this.repository.AddToken(token);
		token.expired_at = token.expired_at.minusSeconds(2 * 365 * 24 * 60 * 60);
		this.repository.UpdateToken(token);

		var token1 = new Token();
		token1.id = UUID.randomUUID();
		token1.user_id = UUID.randomUUID();
		token1.value = "token";
		token1.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token1.revoked = false;
		this.repository.AddToken(token1);

		assertThat(this.repository.GetAllExpiredBeforeDate(Instant.now())).hasSize(1);
	}

	@Test
	@Order(6)
	void Update_A_Token() {
		var token = new Token();
		token.id = UUID.randomUUID();
		token.user_id = UUID.randomUUID();
		token.value = "token";
		token.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token.revoked = false;
		this.repository.AddToken(token);

		token.revoked = true;
		this.repository.UpdateToken(token);
		assertThat(this.repository.GetTokenById(token.id).revoked).isTrue();
	}

	@Test
	@Order(7)
	void Delete_A_Token() throws NoSuchElementException {
		var token = new Token();
		token.id = UUID.randomUUID();
		token.user_id = UUID.randomUUID();
		token.value = "token";
		token.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token.revoked = false;
		this.repository.AddToken(token);

		var token1 = new Token();
		token1.id = UUID.randomUUID();
		token1.user_id = UUID.randomUUID();
		token1.value = "token";
		token1.expired_at = Instant.now().plusSeconds(365 * 24 * 60 * 60);
		token1.revoked = false;
		this.repository.AddToken(token1);

		var result = this.repository.DeleteToken(token);
		assertThat(result).isTrue();
		assertThatThrownBy(() -> this.repository.GetTokenById(token.id)).isInstanceOf(NoSuchElementException.class);
	}

}
