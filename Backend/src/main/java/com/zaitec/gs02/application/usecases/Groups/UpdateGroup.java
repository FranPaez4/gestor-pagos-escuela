package com.zaitec.gs02.application.usecases.Groups;

import com.zaitec.gs02.application.dtos.Groups.UpdateGroupRequestDto;
import com.zaitec.gs02.application.usecases.Groups.abstracts.IUpdateGroup;
import com.zaitec.gs02.domain.models.Group;
import com.zaitec.gs02.domain.repositories.IGroupRepository;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.Failure;
import org.springframework.stereotype.Service;

@Service
public class UpdateGroup implements IUpdateGroup {

	private final IGroupRepository groupRepository;

	public UpdateGroup(IGroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	@Override
	public IResult<String> execute(UpdateGroupRequestDto request) {
		if (request.id() == null) {
			return new Failure<>("El ID del grupo es obligatorio");
		}

		Group existingGroup = this.groupRepository.getById(request.id());

		if (existingGroup == null) {
			return new Failure<>("Grupo no encontrado");
		}

		Group updatedData = new Group();
		updatedData.id = request.id();
		updatedData.name = request.name();
		updatedData.level = request.level();
		updatedData.academic_course_id = request.academic_course_id();
		updatedData.start_time = request.start_time();
		updatedData.end_time = request.end_time();

		Boolean success = this.groupRepository.updateGroup(updatedData);

		if (success) {
			return new Success<>("Grupo actualizado con éxito");
		}
		else {
			return new Failure<>("No se encontró el grupo con ese ID");
		}
	}

}
