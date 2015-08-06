package com.foliofn.verification.repositories;

import org.springframework.stereotype.Repository;

import com.foliofn.verification.entities.VerificationUser;

/**
 * VerificationUserRepository specifies the interface for repositories handling the persistence of
 * VerificationUser.
 */
@Repository
public interface VerificationUserRepository {

    /**
     * Reads the persistent user record with the specified username if found, <code>null</code>
     * otherwise.
     * 
     * @param username
     *            name of the user to fetch.
     * @return the user with the specified username if one exists, <code>null</code> otherwise.
     */
    public VerificationUser findByUsername(String username);
    
    /**
     * Persists the user to the underlying store, creating or updating a persistent record as
     * necessary. The record can then be retrieved using a corresponding finder method.
     * 
     * @param user
     *            the user to persist.
     * @see #findByUsername(String)
     */
    public void saveOrUpdate(VerificationUser user);

    /**
     * Same as {@link #saveOrUpdate(VerificationUser)} however also allowing the user's Seckey
     * secret to be updated.
     * 
     * @param user
     *            the user to persist.
     * @param seckey
     *            the user's Seckey secret.
     */
    public void saveOrUpdate(VerificationUser user, String seckey);

    public void save(VerificationUser user);

    public void save(VerificationUser user, String secret);

    public void update(VerificationUser user);

    public void update(VerificationUser user, String secret);

    /**
     * Determines if the provided seckey secret matches the stored secret.
     * 
     * @param username
     *            the username to check the seckey secret against.
     * @param seckey
     *            the user provided seckey secret.
     * @return <code>true</code> if the expected matches the actual secret, <code>false</code>
     *         otherwise.
     */
    public boolean validateSeckeySecret(String username, String seckey);
    
}