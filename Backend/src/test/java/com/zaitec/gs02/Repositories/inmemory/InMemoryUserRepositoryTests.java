package com.zaitec.gs02.Repositories.inmemory;

import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class InMemoryUserRepositoryTests {

	@Autowired
	IUserRepository repository;

	@Test
	@Order(1)
	void New_User_Is_Added_Correctly() {
		User user = new User();
		user.id = UUID.randomUUID();
		user.role_id = UUID.randomUUID();
		user.name = "Sergio";
		user.lastname = "Lopez";
		user.phone = "123456789";
		user.email = "test@test.com";
		user.password = "myshashedpassword";
		user.enabled = false;
		user.created_at = Instant.now();
		user.updated_at = Instant.now();

		var result = this.repository.addUser(user);

		assertThat(result).isTrue();
	}

	@Test
	@Order(2)
	void New_User_Is_Added_As_Null_Return_False() {
		var result = this.repository.addUser(null);
		assertThat(result).isFalse();
	}

	@Test
	@Order(3)
	void Get_User_By_Id_Correctly() {
		UUID testUUID = UUID.randomUUID();

		User user1 = new User();

		user1.id = testUUID;
		user1.role_id = UUID.randomUUID();
		user1.name = "Sergio";
		user1.lastname = "Lopez";
		user1.phone = "123456789";
		user1.email = "test@test.com";
		user1.password = "myshashedpassword";
		user1.enabled = false;
		user1.created_at = Instant.now();
		user1.updated_at = Instant.now();

		User user2 = new User();

		user2.id = UUID.randomUUID();
		user2.role_id = UUID.randomUUID();
		user2.name = "Sergio";
		user2.lastname = "Lopez";
		user2.phone = "123456789";
		user2.email = "test@test.com";
		user2.password = "myshashedpassword";
		user2.enabled = false;
		user2.created_at = Instant.now();
		user2.updated_at = Instant.now();

		this.repository.addUser(user2);
		this.repository.addUser(user1);

		var result = this.repository.getById(testUUID);
		assertThat(result.id).isSameAs(testUUID);
	}

	@Test
	@Order(4)
	void Get_All_Users_Match_Correctly() {

		User user1 = new User();

		user1.id = UUID.randomUUID();
		user1.role_id = UUID.randomUUID();
		user1.name = "Sergio";
		user1.lastname = "Lopez";
		user1.phone = "123456789";
		user1.email = "u3@test.com";
		user1.password = "myshashedpassword";
		user1.enabled = false;
		user1.created_at = Instant.now();
		user1.updated_at = Instant.now();

		User user2 = new User();

		user2.id = UUID.randomUUID();
		user2.role_id = UUID.randomUUID();
		user2.name = "Sergio";
		user2.lastname = "Lopez";
		user2.phone = "123456789";
		user2.email = "u1@test.com";
		user2.password = "myshashedpassword";
		user2.enabled = false;
		user2.created_at = Instant.now();
		user2.updated_at = Instant.now();

		var result = this.repository.getAll();

		this.repository.addUser(user2);
		this.repository.addUser(user1);

		assertThat(this.repository.getAll()).hasSize(result.size() + 2);
	}

	@Test
	@Order(4)
	void Get_User_By_Email_Correctly() {

		User user1 = new User();

		user1.id = UUID.randomUUID();
		user1.role_id = UUID.randomUUID();
		user1.name = "Sergio";
		user1.lastname = "Lopez";
		user1.phone = "123456789";
		user1.email = "test1@test.com";
		user1.password = "myshashedpassword";
		user1.enabled = false;
		user1.created_at = Instant.now();
		user1.updated_at = Instant.now();

		User user2 = new User();

		user2.id = UUID.randomUUID();
		user2.role_id = UUID.randomUUID();
		user2.name = "Sergio";
		user2.lastname = "Lopez";
		user2.phone = "123456789";
		user2.email = "test2@test.com";
		user2.password = "myshashedpassword";
		user2.enabled = false;
		user2.created_at = Instant.now();
		user2.updated_at = Instant.now();

		this.repository.addUser(user2);
		this.repository.addUser(user1);

		var result = this.repository.getByEmail("test2@test.com");
		assertThat(result.id).isSameAs(user2.id);
	}

	@Test
	@Order(6)
	void Get_User_By_Role_Correctly() {
		UUID roleId = UUID.randomUUID();
		User user1 = new User();

		user1.id = UUID.randomUUID();
		user1.role_id = roleId;
		user1.name = "Sergio";
		user1.lastname = "Lopez";
		user1.phone = "123456789";
		user1.email = "test1@test.com";
		user1.password = "myshashedpassword";
		user1.enabled = false;
		user1.created_at = Instant.now();
		user1.updated_at = Instant.now();

		User user2 = new User();

		user2.id = UUID.randomUUID();
		user2.role_id = roleId;
		user2.name = "Sergio";
		user2.lastname = "Lopez";
		user2.phone = "123456789";
		user2.email = "test2@test.com";
		user2.password = "myshashedpassword";
		user2.enabled = false;
		user2.created_at = Instant.now();
		user2.updated_at = Instant.now();

		this.repository.addUser(user2);
		this.repository.addUser(user1);

		var result = this.repository.getByRole(roleId);
		assertThat(result).hasSize(2);
	}

	@Test
	@Order(7)
	void Enable_User_Correctly() {
		User user = new User();
		user.id = UUID.randomUUID();
		user.role_id = UUID.randomUUID();
		user.name = "Sergio";
		user.lastname = "Lopez";
		user.phone = "123456789";
		user.email = "test@test.com";
		user.password = "myshashedpassword";
		user.enabled = false;
		user.created_at = Instant.now();
		user.updated_at = Instant.now();

		this.repository.addUser(user);

		var result = this.repository.enableUserById(user.id);
		var userRetrieved = this.repository.getById(user.id);

		assertThat(result).isTrue();
		assertThat(userRetrieved.enabled).isTrue();

	}

	@Test
	@Order(8)
	void Disable_User_Correctly() {
		User user = new User();
		user.id = UUID.randomUUID();
		user.role_id = UUID.randomUUID();
		user.name = "Sergio";
		user.lastname = "Lopez";
		user.phone = "123456789";
		user.email = "test@test.com";
		user.password = "myshashedpassword";
		user.enabled = true;
		user.created_at = Instant.now();
		user.updated_at = Instant.now();

		this.repository.addUser(user);

		var result = this.repository.disableUserById(user.id);
		var userRetrieved = this.repository.getById(user.id);

		assertThat(result).isTrue();
		assertThat(userRetrieved.enabled).isFalse();

	}

	@Test
	@Order(8)
	void Update_User_Correctly() {
		UUID testUUID = UUID.randomUUID();
		User user = new User();
		user.id = testUUID;
		user.role_id = UUID.randomUUID();
		user.name = "Sergio";
		user.lastname = "Lopez";
		user.phone = "123456789";
		user.email = "test@test.com";
		user.password = "myshashedpassword";
		user.enabled = false;
		user.created_at = Instant.now();
		user.updated_at = Instant.now();

		User user1 = new User();
		user1.id = testUUID;
		user1.role_id = UUID.randomUUID();
		user1.name = "Sergio";
		user1.lastname = "Lopez";
		user1.phone = "123456789";
		user1.email = "test@test.com";
		user1.password = "myshashedpassword";
		user1.enabled = true;
		user1.created_at = Instant.now();
		user1.updated_at = Instant.now();

		this.repository.addUser(user);

		var result = this.repository.updateUser(user1);
		var userRetrieved = this.repository.getById(testUUID);

		assertThat(result).isTrue();
		assertThat(userRetrieved.enabled).isTrue();

	}

}
