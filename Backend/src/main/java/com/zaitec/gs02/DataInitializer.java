package com.zaitec.gs02;

import com.zaitec.gs02.application.services.PasswordHasherService;
import com.zaitec.gs02.domain.models.Pay;
import com.zaitec.gs02.domain.models.Role;
import com.zaitec.gs02.domain.models.Student;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.domain.repositories.IPayRepository;
import com.zaitec.gs02.domain.repositories.IRoleRepository;
import com.zaitec.gs02.domain.repositories.IStudentRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class DataInitializer {

	private final IRoleRepository roleRepository;

	private final IUserRepository userRepository;

	private final PasswordHasherService passwordHasherService;

	private final IStudentRepository studentRepository;

	private final IPayRepository payRepository;

	public DataInitializer(IRoleRepository roleRepository, IUserRepository userRepository,
			PasswordHasherService passwordHasherService, IStudentRepository studentRepository,
			IPayRepository payRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordHasherService = passwordHasherService;
		this.studentRepository = studentRepository;
		this.payRepository = payRepository;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		initRoles();
		initAdminUsers();
		initStandardUser();
		initEnableStudent();
		initPymentPendingForStudent();
	}

	private void initAdminUsers() {
		Instant now = Instant.now();

		User admin1 = new User();
		admin1.id = UUID.randomUUID();
		admin1.role_id = this.roleRepository.getByName("Admin").id;
		admin1.name = "Sergio";
		admin1.lastname = "Lopez";
		admin1.enabled = true;
		admin1.email = "sergio@test.com";
		admin1.phone = "123456789";
		admin1.password = this.passwordHasherService.hashPassword("P@ssw0rd");
		admin1.updated_at = now;
		admin1.created_at = now;

		createUserIfNotExists(admin1);
	}

	private void initStandardUser() {

		Instant now = Instant.now();

		User student1 = new User();
		student1.id = UUID.randomUUID();
		student1.role_id = this.roleRepository.getByName("Student").id;
		student1.name = "Juan";
		student1.lastname = "Lopez";
		student1.enabled = false;
		student1.phone = "123456789";
		student1.email = "juan@test.com";
		student1.password = this.passwordHasherService.hashPassword("P@ssw0rd");
		student1.updated_at = now;
		student1.created_at = now;

		createUserIfNotExists(student1);
	}

	private void initEnableStudent() {
		User user = this.userRepository.getByEmail("juan@test.com");

		Student student = new Student();
		student.id = user.id;
		student.address = "C/ del congo";
		student.birthDate = new Date(1990, 6, 27);

		this.studentRepository.addStudent(student);
		this.userRepository.enableUserById(user.id);
	}

	private void initPymentPendingForStudent() {

		User user = this.userRepository.getByEmail("juan@test.com");

		Pay pendingPay1 = new Pay();
		pendingPay1.id = UUID.randomUUID();
		pendingPay1.studentId = user.id;
		pendingPay1.paymentMethodId = UUID.randomUUID();
		pendingPay1.enrollmentId = UUID.randomUUID();
		pendingPay1.validate = false;
		pendingPay1.state = false;
		pendingPay1.amount = 300D;
		pendingPay1.updated_at = Instant.now();
		pendingPay1.created_at = Instant.now();
		pendingPay1.paymentDate = null;
		pendingPay1.validateDate = null;

		this.payRepository.create(pendingPay1);

	}

	private void initRoles() {
		createRoleIfNotExists("Admin");
		createRoleIfNotExists("Student");
	}

	private void createRoleIfNotExists(String roleName) {

		var existing = this.roleRepository.getByName(roleName);
		if (existing != null) {
			return;
		}

		Role role = new Role();
		role.id = UUID.randomUUID();
		role.name = roleName;

		this.roleRepository.addRole(role);
	}

	private void createUserIfNotExists(User user) {
		var existing = this.userRepository.getByEmail(user.email);
		if (existing != null) {
			return;
		}
		this.userRepository.addUser(user);

	}

}
