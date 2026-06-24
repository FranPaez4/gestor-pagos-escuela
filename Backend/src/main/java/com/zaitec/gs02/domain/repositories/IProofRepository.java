package com.zaitec.gs02.domain.repositories;

import com.zaitec.gs02.domain.models.Proof;

import java.util.UUID;

public interface IProofRepository {

	/**
	 * Add proof.
	 * @param proof proof to add.
	 * @return true if proof is added else false.
	 */
	Boolean add(Proof proof);

	/**
	 * Get proof by id.
	 * @param id id of proof.
	 * @return proof.
	 */
	Proof getById(UUID id);

	/**
	 * Update proof.
	 * @param proof proof to update.
	 * @return true if proof is updated else false.
	 */
	Boolean update(Proof proof);

	/**
	 * Delete proof.
	 * @param proof proof to delete.
	 * @return true if proof is deleted else false.
	 */
	Boolean delete(Proof proof);

}
