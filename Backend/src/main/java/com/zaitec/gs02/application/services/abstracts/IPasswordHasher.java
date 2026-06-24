package com.zaitec.gs02.application.services.abstracts;

public interface IPasswordHasher {

	/**
	 * Hashes the password.
	 * @param password the password to hash
	 * @return the hashed password
	 */
	String hashPassword(String password);

	/**
	 * Checks if the password matches the hash.
	 * @param password the password to check
	 * @param hash the hash to check against
	 * @return true if the password matches the hash, false otherwise
	 */
	boolean checkPassword(String password, String hash);

}
