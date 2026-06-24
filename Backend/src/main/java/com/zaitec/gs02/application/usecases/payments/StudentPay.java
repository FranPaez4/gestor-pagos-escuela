package com.zaitec.gs02.application.usecases.payments;

import com.zaitec.gs02.application.dtos.pays.StudentPayRequestDto;
import com.zaitec.gs02.application.dtos.pays.StudentPayResponseDto;
import com.zaitec.gs02.application.shared.Failure;
import com.zaitec.gs02.application.shared.Success;
import com.zaitec.gs02.application.shared.abstracts.IResult;
import com.zaitec.gs02.application.usecases.payments.abstracts.IStudentPay;
import com.zaitec.gs02.domain.models.Pay;
import com.zaitec.gs02.domain.models.Proof;
import com.zaitec.gs02.domain.repositories.IPayRepository;
import com.zaitec.gs02.domain.repositories.IProofRepository;
import com.zaitec.gs02.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class StudentPay implements IStudentPay {

	private final IUserRepository userRepository;

	private final IPayRepository payRepository;

	private final IProofRepository proofRepository;

	public StudentPay(IUserRepository userRepository, IPayRepository payRepository, IProofRepository proofRepository) {
		this.userRepository = userRepository;
		this.payRepository = payRepository;
		this.proofRepository = proofRepository;
	}

	/**
	 * Execute the logic to realise a pay.
	 * @param object the object that contains all data required
	 * @return a result
	 */
	@Override
	public IResult<StudentPayResponseDto> execute(StudentPayRequestDto object) {
		Pay payRecovered = this.payRepository.getById(object.payId());
		if (payRecovered == null || !payRecovered.studentId.equals(object.studentId())) {
			return new Failure<>(new StudentPayResponseDto("Este pago no existe"));
		}
		if (payRecovered.state.equals(true)) {
			return new Failure<>(new StudentPayResponseDto("Este pago ya fue pagado"));
		}
		Pay pay = new Pay();
		pay.id = payRecovered.id;
		pay.paymentMethodId = object.paymentMethod();
		pay.amount = payRecovered.amount;
		pay.paymentDate = Instant.now();
		pay.state = true;

		if (this.payRepository.update(pay)) {
			if (object.proof() == null) {
				return new Success<>(
						new StudentPayResponseDto("Pago realziado con exito. Acuerdate de subir el comprobante"));
			}
			Proof proof = new Proof();
			proof.id = payRecovered.id;
			proof.proof = object.proof();
			if (this.proofRepository.add(proof)) {
				return new Success<>(new StudentPayResponseDto("Pago realziado con exito"));
			}
			return new Success<>(new StudentPayResponseDto("Pago realziado con exito. Pero el comprobante ha fallado"));
		}

		return new Failure<>(new StudentPayResponseDto("No se ha podido realizar el pago"));
	}

}
