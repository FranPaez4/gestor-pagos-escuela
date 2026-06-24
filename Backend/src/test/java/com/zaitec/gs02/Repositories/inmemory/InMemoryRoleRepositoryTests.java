package com.zaitec.gs02.Repositories.inmemory;

import com.zaitec.gs02.domain.models.Role;
import com.zaitec.gs02.domain.repositories.IRoleRepository;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class InMemoryRoleRepositoryTests {

	@Autowired
	IRoleRepository repository;

	@Test
	@Order(1)
	void New_Role_Is_Added_Correctly() {
		Role role = new Role();
		role.id = UUID.randomUUID();
		role.name = "Teacher";

		var result = this.repository.addRole(role);

		assertThat(result).isTrue();
	}

	@Test
	@Order(2)
	void New_Role_Is_Added_As_Null_Return_False() {
		var result = this.repository.addRole(null);
		assertThat(result).isFalse();
	}

	@Test
	@Order(3)
	void Get_Role_By_Id_Correctly() {
		UUID testUUID = UUID.randomUUID();

		Role role1 = new Role();
		role1.id = testUUID;
		role1.name = "Manager";

		Role role2 = new Role();
		role2.id = UUID.randomUUID();
		role2.name = "Developer";

		this.repository.addRole(role2);
		this.repository.addRole(role1);

		var result = this.repository.getById(testUUID);
		assertThat(result.id).isSameAs(testUUID);
	}

	@Test
	@Order(4)
	void Get_All_Roles_Match_Correctly() {
		Role role1 = new Role();
		role1.id = UUID.randomUUID();
		role1.name = "Designer";

		Role role2 = new Role();
		role2.id = UUID.randomUUID();
		role2.name = "QA";

		var result = this.repository.getAll();

		this.repository.addRole(role2);
		this.repository.addRole(role1);

		assertThat(this.repository.getAll()).hasSize(result.size() + 2);
	}

	@Test
	@Order(5)
	void Get_Role_By_Name_Correctly() {
		Role role1 = new Role();
		role1.id = UUID.randomUUID();
		role1.name = "Analyst";

		Role role2 = new Role();
		role2.id = UUID.randomUUID();
		role2.name = "Architect";

		this.repository.addRole(role2);
		this.repository.addRole(role1);

		var result = this.repository.getByName("Architect");
		assertThat(result.id).isSameAs(role2.id);
		assertThat(result.name).isEqualTo("Architect");
	}

	@Test
	@Order(6)
	void Update_Role_Correctly() {
		UUID testUUID = UUID.randomUUID();

		Role role = new Role();
		role.id = testUUID;
		role.name = "OriginalName";

		Role roleUpdated = new Role();
		roleUpdated.id = testUUID;
		roleUpdated.name = "UpdatedName";

		this.repository.addRole(role);

		var result = this.repository.updateRole(roleUpdated);
		var roleRetrieved = this.repository.getById(testUUID);

		assertThat(result).isTrue();
		assertThat(roleRetrieved.name).isEqualTo("UpdatedName");
	}

	@Test
	@Order(7)
	void Update_Non_Existent_Role_Returns_False() {
		Role role = new Role();
		role.id = UUID.randomUUID();
		role.name = "NonExistent";

		var result = this.repository.updateRole(role);

		assertThat(result).isFalse();
	}

	@Test
	@Order(8)
	void Get_Default_Roles_On_Initialization() {
		var allRoles = this.repository.getAll();

		var adminRole = this.repository.getByName("Admin");
		var studentRole = this.repository.getByName("Student");

		assertThat(adminRole).isNotNull();
		assertThat(studentRole).isNotNull();
		assertThat(adminRole.name).isEqualTo("Admin");
		assertThat(studentRole.name).isEqualTo("Student");
	}

	@Test
	@Order(9)
	void Add_Role_With_Null_Id_Returns_False() {
		Role role = new Role();
		role.id = null;
		role.name = "InvalidRole";

		var result = this.repository.addRole(role);

		assertThat(result).isFalse();
	}

}
