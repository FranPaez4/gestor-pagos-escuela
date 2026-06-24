package com.zaitec.gs02.presentation.controllers;

import com.zaitec.gs02.application.dtos.Groups.CreateGroupRequestDto;
import com.zaitec.gs02.application.dtos.Groups.UpdateGroupRequestDto;
import com.zaitec.gs02.application.dtos.courses.CreateCourseAcademicRequestDto;
import com.zaitec.gs02.application.dtos.courses.CreateCoursesRequestDto;
import com.zaitec.gs02.application.dtos.courses.UpdateAcademicCourseRequestDto;
import com.zaitec.gs02.application.dtos.courses.UpdateCourseRequestDto;
import com.zaitec.gs02.application.dtos.roompergroup.AddRoomPerGroupRequest;
import com.zaitec.gs02.application.dtos.rooms.CreateRoomRequestDto;
import com.zaitec.gs02.application.dtos.rooms.UpdateRoomRequestDto;
import com.zaitec.gs02.application.dtos.users.ActivateUserRequestDto;
import com.zaitec.gs02.application.dtos.users.DisableUserRequestDto;
import com.zaitec.gs02.application.usecases.Groups.abstracts.ICreateGroup;
import com.zaitec.gs02.application.usecases.Groups.abstracts.IUpdateGroup;
import com.zaitec.gs02.application.usecases.courses.UpdateAcademicCourse;
import com.zaitec.gs02.application.usecases.courses.abstracts.ICreateAcademicCourse;
import com.zaitec.gs02.application.usecases.courses.abstracts.ICreateCourse;
import com.zaitec.gs02.application.usecases.courses.abstracts.IUpdateAcademicCourse;
import com.zaitec.gs02.application.usecases.courses.abstracts.IUpdateCourse;
import com.zaitec.gs02.application.usecases.roompergroup.abstracts.IAddGroupToRoom;
import com.zaitec.gs02.application.usecases.rooms.abstracs.ICreateRoom;
import com.zaitec.gs02.application.usecases.rooms.abstracs.IGetAllRooms;
import com.zaitec.gs02.application.usecases.rooms.abstracs.IGetRoom;
import com.zaitec.gs02.application.usecases.rooms.abstracs.IUpdateRoom;
import com.zaitec.gs02.application.usecases.users.abstracts.IActivateUser;
import com.zaitec.gs02.application.usecases.users.abstracts.IDisableUser;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.presentation.security.Authorize;
import com.zaitec.gs02.presentation.security.CurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private final ICreateCourse createCourseUseCase;

	private final IUpdateCourse updateCourseUseCase;

	private final ICreateAcademicCourse createAcademicCourseUseCase;

	private final IUpdateAcademicCourse updateAcademicCourseUseCase;

	private final ICreateGroup createGroupUseCase;

	private final IUpdateGroup updateGroupUseCase;

	private final ICreateRoom createRoomUseCase;

	private final IUpdateRoom updateRoomUseCase;

	private final IGetAllRooms getAllRoomsUseCase;

	private final IGetRoom getRoomUseCase;

	private final IAddGroupToRoom addGroupToRoomUseCase;

	private final IActivateUser activateUserUseCase;

	private final IDisableUser disableUserUseCase;

	public AdminController(ICreateCourse createCourseUseCase, IUpdateCourse updateCourseUseCase,
			ICreateAcademicCourse createAcademicCourseUseCase, UpdateAcademicCourse updateAcademicCourse,
			IUpdateAcademicCourse updateAcademicCourseUseCase, ICreateGroup createGroupUseCase,
			IUpdateCourse updateCourse, IUpdateGroup updateGroupUseCase, ICreateRoom createRoomUseCase,
			IUpdateRoom updateRoomUseCase, IGetAllRooms getAllRoomsUseCase, IGetRoom getRoomUseCase,
			IAddGroupToRoom addGroupToRoomUseCase, IActivateUser activateUserUseCase, IDisableUser disableUserUseCase) {

		this.createCourseUseCase = createCourseUseCase;
		this.updateCourseUseCase = updateCourseUseCase;
		this.createAcademicCourseUseCase = createAcademicCourseUseCase;
		this.updateAcademicCourseUseCase = updateAcademicCourseUseCase;
		this.createGroupUseCase = createGroupUseCase;
		this.updateGroupUseCase = updateGroupUseCase;
		this.createRoomUseCase = createRoomUseCase;
		this.updateRoomUseCase = updateRoomUseCase;
		this.getAllRoomsUseCase = getAllRoomsUseCase;
		this.getRoomUseCase = getRoomUseCase;
		this.addGroupToRoomUseCase = addGroupToRoomUseCase;
		this.activateUserUseCase = activateUserUseCase;
		this.disableUserUseCase = disableUserUseCase;
	}

	@PostMapping("/courses")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> createCourse(@CurrentUser User admin,
			@RequestBody CreateCoursesRequestDto request) {

		if (request.name() == null || request.name().isBlank() || request.id() == null) {

			return ResponseEntity.status(400).body(Map.of("error", "Datos del curso incompletos o inválidos"));
		}

		var result = this.createCourseUseCase.execute(request);

		if (result.isSuccess()) {

			return ResponseEntity.status(201)
				.body(Map.of("message", "Curso creado exitosamente", "course", result.getValue()));
		}

		if (result.getErrorMessage().toLowerCase().contains("ya existe")
				|| result.getErrorMessage().toLowerCase().contains("duplicado")) {
			return ResponseEntity.status(409).body(Map.of("error", result.getErrorMessage()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage()));
	}

	@PutMapping("/courses/")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> updateCourse(@CurrentUser User admin,
			@RequestBody UpdateCourseRequestDto request) {

		if (request.name() == null || request.name().isBlank() || request.id() == null) {
			return ResponseEntity.status(400).body(Map.of("error", "Datos del curso incompletos o inválidos"));
		}

		var result = this.updateCourseUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", "Curso actualizado exitosamente", "course", result.getValue()));
		}

		if (result.getErrorMessage().toLowerCase().contains("no encontrado")) {
			return ResponseEntity.status(404).body(Map.of("error", result.getErrorMessage()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage()));
	}

	@PostMapping("/academic-courses")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> createAcademicCourse(@CurrentUser User admin,
			@RequestBody CreateCourseAcademicRequestDto request) {

		var result = this.createAcademicCourseUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.status(201)
				.body(Map.of("message", "Curso académico creado exitosamente", "academicCourse", result.getValue()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage()));
	}

	@PutMapping("/academic-courses")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> updateAcademicCourse(@CurrentUser User admin,
			@RequestBody UpdateAcademicCourseRequestDto request) {

		var result = this.updateAcademicCourseUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", result.getValue()));
		}

		if (result.getErrorMessage().toLowerCase().contains("no encontrado")) {
			return ResponseEntity.status(404).body(Map.of("error", result.getErrorMessage()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage()));
	}

	@PostMapping("/groups")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> createGroup(@CurrentUser User admin,
			@RequestBody CreateGroupRequestDto request) {

		var result = this.createGroupUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.status(201).body(Map.of("message", result.getValue()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage()));
	}

	@PutMapping("/groups")
	@Authorize(roles = "admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> updateGroup(@CurrentUser User admin,
			@RequestBody UpdateGroupRequestDto request) {

		var result = this.updateGroupUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", result.getValue()));
		}

		int statusCode = result.getErrorMessage().contains("encontrado") ? 404 : 400;
		return ResponseEntity.status(statusCode).body(Map.of("error", result.getErrorMessage()));
	}

	@PostMapping("/rooms")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> createRoom(@CurrentUser User admin,
			@RequestBody CreateRoomRequestDto request) {

		var result = this.createRoomUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.status(201).body(Map.of("message", result.getValue()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage()));
	}

	@PutMapping("/rooms")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> updateRoom(@CurrentUser User admin,
			@RequestBody UpdateRoomRequestDto request) {

		var result = this.updateRoomUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", result.getValue()));
		}

		int statusCode = result.getErrorMessage().contains("encontrada") ? 404 : 400;
		return ResponseEntity.status(statusCode).body(Map.of("error", result.getErrorMessage()));
	}

	@GetMapping("/rooms")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> getAllRooms(@CurrentUser User user) {
		var response = this.getAllRoomsUseCase.execute(null);
		if (response.isSuccess()) {
			return ResponseEntity.ok().body(Map.of("rooms", response.getValue()));
		}
		return ResponseEntity.status(404).body((Map<String, ?>) Map.of("error", "No se encontraron datos"));
	}

	@GetMapping("/rooms/{id}")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> getAllRooms(@CurrentUser User user, @PathVariable("id") UUID id) {
		if (id == null) {
			return ResponseEntity.status(404).body(Map.of("error", "el id no puede ser nulo"));
		}
		var response = this.getRoomUseCase.execute(id);
		if (response.isSuccess()) {
			return ResponseEntity.ok().body((Map.of("room", response.getValue())));
		}
		return ResponseEntity.status(400).body(Map.of("error", "no existe la sala"));
	}

	@PostMapping("/rooms/{roomid}/group/{groupid}")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> addGroupToRoom(@CurrentUser User admin, @PathVariable UUID roomid,
			@PathVariable UUID groupid, @RequestBody AddRoomPerGroupRequest request) {
		if (!roomid.equals(request.roomId()) || !groupid.equals(request.groupId())) {
			return ResponseEntity.status(400).body(Map.of("error", "El id no corresponde al id del room"));
		}
		var response = this.addGroupToRoomUseCase.execute(request);

		if (response.isSuccess()) {
			return ResponseEntity.status(201).body(Map.of("message", "Agregado exitosamente"));
		}

		return ResponseEntity.status(400).body(Map.of("error", response.getErrorMessage()));
	}

	@PatchMapping("/users/{id}/activate")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> activateUser(@CurrentUser User admin, @PathVariable("id") UUID id) {

		var request = new ActivateUserRequestDto(id);

		var result = this.activateUserUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", result.getValue()));
		}

		int statusCode = result.getErrorMessage().contains("encontrado") ? 404 : 400;
		return ResponseEntity.status(statusCode).body(Map.of("error", result.getErrorMessage()));
	}

	@PatchMapping("/users/{id}/disable")
	@Authorize(roles = "Admin")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> disableUser(@CurrentUser User admin, @PathVariable("id") UUID id) {

		var request = new DisableUserRequestDto(id);
		var result = this.disableUserUseCase.execute(request);

		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", result.getValue()));
		}

		int statusCode = result.getErrorMessage().contains("encontrado") ? 404 : 400;
		return ResponseEntity.status(statusCode).body(Map.of("error", result.getErrorMessage()));
	}

}
