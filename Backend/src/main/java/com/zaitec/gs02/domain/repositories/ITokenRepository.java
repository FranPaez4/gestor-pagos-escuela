package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Token;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ITokenRepository {

	/**
	 * Retrieve the token belongs to this ID.
	 * @param id the token id to search
	 * @return retrieve the token
	 */
	Token GetTokenById(UUID id);

	/**
	 * Retrieve the token which user id belongs.
	 * @param id the user id
	 * @return retrieve the token
	 */
	Token GetTokenByUserId(UUID id);

	/**
	 * Retrieve the tokens which user id belongs.
	 * @param id the user id
	 * @return retrieve the list of tokens
	 */
	List<Token> GetTokensByUserId(UUID id);

	/**
	 * Retrieve all token in the repository.
	 * @return a list of tokens
	 */
	List<Token> GetAll();

	/**
	 * Retrieve all token expired before this date.
	 * @param date the date to filter
	 * @return a list of token selected
	 */
	List<Token> GetAllExpiredBeforeDate(Instant date);

	/**
	 * Retrieve all token revoked.
	 * @return a list of token selected
	 */
	List<Token> GetAllRevoked();

	/**
	 * Update the selected token.
	 * @param token token updated
	 * @return true if is correctly updated else false.
	 */
	Boolean UpdateToken(Token token);

	/**
	 * Add a new token to the repository.
	 * @param token the token to add
	 * @return true if is added correctly else false
	 */
	Boolean AddToken(Token token);

	/**
	 * Delete the token from the repository.
	 * @param token the token to delete
	 * @return true if is deleted correctly else false
	 */
	Boolean DeleteToken(Token token);

}
