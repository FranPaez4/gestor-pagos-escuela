package com.zaitec.gs02.application.usecases.Groups;

import com.zaitec.gs02.application.dtos.Groups.CreateGroupRequestDto;
import com.zaitec.gs02.application.usecases.Groups.abstracts.ICreateGroup;
import com.zaitec.gs02.domain.models.Group;
import com.zaitec.gs02.domain.repositories.IGroupRepository;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.Failure;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CreateGroup implements ICreateGroup {

	private final IGroupRepository groupRepository;

	public CreateGroup(IGroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	@Override
	public IResult<String> execute(CreateGroupRequestDto request) {

		if (request.academic_course_id() == null || request.name() == null || request.name().isBlank()) {
			return new Failure<>("El ID del curso académico y el nombre del grupo son obligatorios");
		}

		Group newGroup = new Group();
		newGroup.id = UUID.randomUUID();
		newGroup.academic_course_id = request.academic_course_id();
		newGroup.name = request.name();
		newGroup.level = request.level();
		newGroup.start_time = request.start_time();
		newGroup.end_time = request.end_time();

		Boolean isAdded = this.groupRepository.addGroup(newGroup);

		if (isAdded) {
			System.out.println(" GRUPO CREADO CON ID: " + newGroup.id);
			return new Success<>("Grupo creado con éxito");
		}
		else {
			return new Failure<>("No se pudo guardar el grupo en la base de datos");
		}
	}

}
