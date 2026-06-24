package com.zaitec.gs02.presentation.controllers;

import com.zaitec.gs02.application.usecases.auth.abstracts.ILogOutUseCase;
import com.zaitec.gs02.application.usecases.auth.abstracts.ILoginUseCase;
import com.zaitec.gs02.application.usecases.auth.abstracts.IRegisterUseCase;
import com.zaitec.gs02.application.dtos.auth.LogOutRequestDto;
import com.zaitec.gs02.application.dtos.auth.LoginRequestDto;
import com.zaitec.gs02.application.dtos.auth.RegisterRequestDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final ILoginUseCase loginUseCase;

	private final IRegisterUseCase registerUseCase;

	private final ILogOutUseCase logOutUseCase;

	public AuthController(ILoginUseCase loginUseCase, IRegisterUseCase registerUseCase, ILogOutUseCase logOutUseCase) {
		this.loginUseCase = loginUseCase;
		this.registerUseCase = registerUseCase;
		this.logOutUseCase = logOutUseCase;
	}

	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequest) {
		try {
			var result = this.loginUseCase.execute(loginRequest);
			if (result.isSuccess()) {
				String token = result.getValue().value1();
				return ResponseEntity.ok(Map.of("token", token, "userId", result.getValue().value2()));
			}
			else {
				return ResponseEntity.status(401).body(Map.of("error", result.getErrorMessage().value1()));
			}
		}
		catch (com.zaitec.gs02.application.exceptions.InvalidCredentialsException ex) {
			return ResponseEntity.status(401).body(Map.of("error", ex.getMessage()));
		}
	}

	@PostMapping(value = "/register", consumes = "application/json")
	public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequestDto registerRequest) {
		try {
			var result = this.registerUseCase.execute(registerRequest);
			if (result.isSuccess()) {
				return ResponseEntity.status(201).body(Map.of("message", result.getValue()));
			}
			return ResponseEntity.badRequest().body(Map.of("error", result.getErrorMessage()));
		}
		catch (RuntimeException ex) {
			return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
		}
	}

	@PostMapping("/logout")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<Map<String, String>> logout(
			@RequestHeader(value = "Authorization", required = false) String authorizationHeader,
			@RequestBody LogOutRequestDto logOutRequest) {
		var result = this.logOutUseCase.execute(authorizationHeader, logOutRequest.userId());

		if (result.isSuccess()) {
			return ResponseEntity.ok(Map.of("message", result.getValue()));
		}

		return ResponseEntity.status(400).body(Map.of("error", result.getErrorMessage()));
	}

}
