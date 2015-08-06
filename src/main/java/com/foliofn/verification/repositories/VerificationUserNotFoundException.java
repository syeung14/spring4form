package com.foliofn.verification.repositories;

public class VerificationUserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VerificationUserNotFoundException() {
        this(null, null);
    }

    public VerificationUserNotFoundException(String msg) {
        this(msg, null);
    }

    public VerificationUserNotFoundException(Throwable cause) {
        this(null, cause);
    }

    public VerificationUserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}