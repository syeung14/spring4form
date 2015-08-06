package com.foliofn.verification.repositories;

public class VerificationUserPersistenceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VerificationUserPersistenceException() {
        this(null, null);
    }

    public VerificationUserPersistenceException(String msg) {
        this(msg, null);
    }

    public VerificationUserPersistenceException(Throwable cause) {
        this(null, cause);
    }

    public VerificationUserPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}