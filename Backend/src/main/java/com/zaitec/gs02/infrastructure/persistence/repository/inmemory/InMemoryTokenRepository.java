package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Token;
import com.zaitec.gs02.domain.repositories.ITokenRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

@Repository
public class InMemoryTokenRepository implements ITokenRepository {

	private static final Logger LOGGER = Logger.getLogger(InMemoryTokenRepository.class.getName());

	private final ConcurrentLinkedDeque<Token> tokens = new ConcurrentLinkedDeque<>();

	/**
	 * Retrieve the token belongs to this ID.
	 * @param id the token id to search
	 * @return retrieve the token
	 */
	@Override
	public Token GetTokenById(UUID id) {
		return this.tokens.stream().filter((t) -> t.id == id).findFirst().orElseThrow();
	}

	/**
	 * Retrieve the token which user id belongs.
	 * @param id the user id
	 * @return retrieve the token
	 */
	@Override
	public Token GetTokenByUserId(UUID id) {
		return this.tokens.stream().filter((t) -> t.user_id == id && !t.revoked).findFirst().orElseThrow();
	}

	/**
	 * Retrieve the token which user id belongs.
	 * @param id the user id
	 * @return retrieve the token
	 */
	@Override
	public List<Token> GetTokensByUserId(UUID id) {
		return this.tokens.stream().filter((t) -> t.user_id.equals(id) && !t.revoked).toList();
	}

	/**
	 * Retrieve all token in the repository.
	 * @return a list of tokens
	 */
	@Override
	public List<Token> GetAll() {
		return this.tokens.stream().toList();
	}

	/**
	 * Retrieve all token expired before this date.
	 * @param date the date to filter
	 * @return a list of token selected
	 */
	@Override
	public List<Token> GetAllExpiredBeforeDate(Instant date) {
		return this.tokens.stream().filter((t) -> t.expired_at.isBefore(date)).toList();
	}

	/**
	 * Retrieve all token revoked.
	 * @return a list of token selected
	 */
	public List<Token> GetAllRevoked() {
		return this.tokens.stream().filter((t) -> t.revoked).toList();
	}

	/**
	 * Update the selected token.
	 * @param token token updated
	 * @return true if is correctly updated else false.
	 */
	@Override
	public Boolean UpdateToken(Token token) {
		try {
			var tokenbeupdated = this.tokens.stream().filter((t) -> t.IsEqual(token)).findFirst().orElseThrow();
			tokenbeupdated.Update(token);
			return true;
		}
		catch (Exception ex) {
			LOGGER.severe(ex.getMessage());
			return false;
		}
	}

	/**
	 * Add a new token to the repository.
	 * @param token the token to add
	 * @return true if is added correctly else false
	 */
	@Override
	public Boolean AddToken(Token token) {
		try {
			if (!IsTokenValid(token)) {
				LOGGER.finer("Token is not valid");
				return false;
			}
			this.tokens.add(token);
			return true;
		}
		catch (Exception ex) {
			LOGGER.severe(ex.getMessage());
			return false;
		}
	}

	/**
	 * Delete the token from the repository.
	 * @param token the token to delete
	 * @return true if is deleted correctly else false
	 */
	@Override
	public Boolean DeleteToken(Token token) {
		var tobedeleted = this.tokens.stream().filter((t) -> t.id == token.id).findFirst().orElseThrow();
		return this.tokens.remove(tobedeleted);
	}

	private Boolean IsTokenValid(Token token) {
		return token != null && !token.expired_at.isBefore(Instant.now()) && !token.revoked && token.user_id != null;
	}

}
