package com.zaitec.gs02.application.workers;

import com.zaitec.gs02.application.services.JwtService;
import com.zaitec.gs02.domain.models.Token;
import com.zaitec.gs02.domain.repositories.ITokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class TokenCleanupWorkerService {

	private final ITokenRepository tokenRepository;

	private final JwtService jwtService;

	TokenCleanupWorkerService(ITokenRepository tokenRepository, JwtService jwtService) {
		this.tokenRepository = tokenRepository;
		this.jwtService = jwtService;
	}

	@Scheduled(fixedRate = 60_000)
	void cleanRevokedAndExpiredTokens() {
		var tokens = this.tokenRepository.GetAllExpiredBeforeDate(Instant.now());
		for (Token token : tokens) {
			if (!this.jwtService.validateToken(token.value)) {
				this.tokenRepository.DeleteToken(token);
			}
		}
		tokens = this.tokenRepository.GetAllRevoked();
		for (Token token : tokens) {
			if (!this.jwtService.validateToken(token.value)) {
				this.tokenRepository.DeleteToken(token);
			}
		}
	}

}
