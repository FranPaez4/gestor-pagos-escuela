package com.zaitec.gs02.application.usecases.payments;

import com.zaitec.gs02.application.dtos.pays.StudentGetSpecificPayRequestDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.payments.abstracts.IGetSpecificPayment;
import com.zaitec.gs02.domain.models.Pay;
import com.zaitec.gs02.domain.repositories.IPayRepository;
import org.springframework.stereotype.Service;

@Service
public class GetSpecificPaymet implements IGetSpecificPayment {

	private final IPayRepository payRepository;

	public GetSpecificPaymet(IPayRepository payRepository) {
		this.payRepository = payRepository;
	}

	@Override
	public IResult<Pay> execute(StudentGetSpecificPayRequestDto request) {
		var result = this.payRepository.getById(request.payId());

		if (result == null) {
			return new Failure<>(null);
		}
		return new Success<>(result);
	}

}
