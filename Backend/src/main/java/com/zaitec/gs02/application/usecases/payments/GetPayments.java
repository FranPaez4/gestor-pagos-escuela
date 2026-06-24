package com.zaitec.gs02.application.usecases.payments;

import com.zaitec.gs02.application.dtos.pays.StudentGetPaysRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.payments.abstracts.IGetPayments;
import com.zaitec.gs02.domain.models.Pay;
import com.zaitec.gs02.domain.repositories.IPayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPayments implements IGetPayments {

	private final IPayRepository paymentRepository;

	public GetPayments(IPayRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Override
	public IResult<List<Pay>> execute(StudentGetPaysRequestDto request) {
		var payments = this.paymentRepository.getByStudentId(request.studentID());

		if (payments == null) {
			return new Failure<>(null);
		}

		return new Success<>(payments);
	}

}
