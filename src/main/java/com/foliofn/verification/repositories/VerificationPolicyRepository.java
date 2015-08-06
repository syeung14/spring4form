package com.foliofn.verification.repositories;

import org.springframework.stereotype.Repository;

import com.foliofn.verification.entities.VerificationPolicy;

/**
 * VerificationPolicyRepository specifies the read-only interface for repositories handling the
 * persistence of VerificationPolicy.
 */
@Repository
public interface VerificationPolicyRepository {

    public VerificationPolicy findById(String name);

    public VerificationPolicy findByName(String name);
}