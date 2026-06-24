package com.zaitec.gs02.presentation.controllers;

import com.zaitec.gs02.application.dtos.pays.StudentGetPaysRequestDto;
import com.zaitec.gs02.application.dtos.pays.StudentGetSpecificPayRequestDto;
import com.zaitec.gs02.application.dtos.pays.StudentPayRequestDto;
import com.zaitec.gs02.application.usecases.payments.GetPayments;
import com.zaitec.gs02.application.usecases.payments.GetSpecificPaymet;
import com.zaitec.gs02.application.usecases.payments.StudentPay;
import com.zaitec.gs02.domain.models.User;
import com.zaitec.gs02.presentation.security.Authorize;
import com.zaitec.gs02.presentation.security.CurrentUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final StudentPay studentPay;

	private final GetPayments getPayments;

	private final GetSpecificPaymet getSpecificPaymet;

	public PaymentController(StudentPay studentPay, GetPayments getPayments, GetSpecificPaymet getSpecificPaymet) {
		this.studentPay = studentPay;
		this.getPayments = getPayments;
		this.getSpecificPaymet = getSpecificPaymet;
	}

	@GetMapping("/")
	@Authorize
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> payments(@CurrentUser User user) {
		var result = this.getPayments.execute(new StudentGetPaysRequestDto(user.id));
		if (result.isSuccess()) {
			return ResponseEntity.status(201).body(Map.of("pays", result.getValue()));
		}
		return ResponseEntity.status(400).body(Map.of("error", "Nos se puede realizar"));
	}

	@GetMapping("/{id}")
	@Authorize
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, ?>> getPay(@CurrentUser User user,
			@RequestParam StudentGetSpecificPayRequestDto requestDto) {

		var result = this.getSpecificPaymet.execute(requestDto);

		if (result.isSuccess()) {
			return ResponseEntity.status(201).body(Map.of("error", result.getValue()));
		}

		return ResponseEntity.status(400).body(Map.of("error", "Nos se puede realizar"));
	}

	@PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Authorize
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, String>> doAPayment(@CurrentUser User user, @RequestParam UUID studentId,
			@RequestParam UUID payId, @RequestParam UUID paymentMethod, @RequestParam MultipartFile file) {
		byte[] proofBytes;
		if (!user.id.equals(studentId)) {
			return ResponseEntity.status(401).body(Map.of("error", "No tienes acceso"));
		}
		try {
			proofBytes = file.getBytes();
		}
		catch (Exception ex) {
			return ResponseEntity.status(400).body(Map.of("error", "Error convirtiendo la imagen"));
		}

		var request = new StudentPayRequestDto(studentId, payId, paymentMethod, proofBytes);
		var result = this.studentPay.execute(request);
		if (result.isSuccess()) {
			return ResponseEntity.status(200).body(Map.of("message", result.getValue().message()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage().message()));
	}

}
