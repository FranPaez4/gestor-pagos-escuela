package com.zaitec.gs02.application.services;

import com.zaitec.gs02.application.services.abstracts.IPasswordHasher;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordHasherService implements IPasswordHasher {

	private static final int SALT_LENGTH = 16;

	private final SecureRandom secureRandom = new SecureRandom();

	/**
	 * Hashes the password.
	 * @param password the password to hash
	 * @return the hashed password
	 */
	@Override
	public String hashPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("password cannot be null");
		}

		try {
			byte[] salt = new byte[SALT_LENGTH];
			this.secureRandom.nextBytes(salt);

			byte[] hash = hashWithSalt(password, salt);

			String saltBase64 = Base64.getEncoder().encodeToString(salt);
			String hashBase64 = Base64.getEncoder().encodeToString(hash);

			return saltBase64 + ":" + hashBase64;
		}
		catch (Exception ex) {
			throw new RuntimeException("Error hashing password", ex);
		}
	}

	/**
	 * Checks if the password matches the hash.
	 * @param password the password to check
	 * @param hash the hash to check against
	 * @return true if the password matches the hash, false otherwise
	 */
	@Override
	public boolean checkPassword(String password, String hash) {
		if (password == null || hash == null) {
			return false;
		}

		try {
			String[] parts = hash.split(":");
			if (parts.length != 2) {
				return false;
			}

			byte[] salt = Base64.getDecoder().decode(parts[0]);
			byte[] expectedHash = Base64.getDecoder().decode(parts[1]);

			byte[] actualHash = hashWithSalt(password, salt);

			if (actualHash.length != expectedHash.length) {
				return false;
			}

			// Comparación en tiempo constante
			int result = 0;
			for (int i = 0; i < actualHash.length; i++) {
				result |= actualHash[i] ^ expectedHash[i];
			}
			return result == 0;
		}
		catch (Exception ex) {
			return false;
		}
	}

	private byte[] hashWithSalt(String password, byte[] salt) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(salt);
		digest.update(password.getBytes(StandardCharsets.UTF_8));
		return digest.digest();
	}

}
