package com.zaitec.gs02.infrastructure.persistence.repository.inmemory;

import com.zaitec.gs02.domain.models.Proof;
import com.zaitec.gs02.domain.repositories.IProofRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryProofRepository implements IProofRepository {

	private final ConcurrentLinkedDeque<Proof> proofs = new ConcurrentLinkedDeque<>();

	/**
	 * Add proof.
	 * @param proof proof to add.
	 * @return true if proof is added else false.
	 */
	@Override
	public Boolean add(Proof proof) {
		if (proof == null) {
			return false;
		}
		this.proofs.add(proof);
		return true;
	}

	/**
	 * Get proof by id.
	 * @param id id of proof.
	 * @return proof.
	 */
	@Override
	public Proof getById(UUID id) {
		return this.proofs.stream().filter((proof) -> proof.id.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Update proof.
	 * @param proof proof to update.
	 * @return true if proof is updated else false.
	 */
	@Override
	public Boolean update(Proof proof) {
		if (proof == null) {
			return false;
		}
		Proof existingProof = this.getById(proof.id);
		if (existingProof == null) {
			return false;
		}
		existingProof.Update(proof);
		return true;
	}

	/**
	 * Delete proof.
	 * @param proof proof to delete.
	 * @return true if proof is deleted else false.
	 */
	@Override
	public Boolean delete(Proof proof) {
		if (proof == null) {
			return false;
		}
		Proof existingProof = this.getById(proof.id);
		if (existingProof == null) {
			return false;
		}
		this.proofs.remove(existingProof);
		return true;
	}

}
