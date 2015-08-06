package com.foliofn.verification.repositories;

public class VerificationPolicyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VerificationPolicyNotFoundException() {
        this(null, null);
    }

    public VerificationPolicyNotFoundException(String msg) {
        this(msg, null);
    }

    public VerificationPolicyNotFoundException(Throwable cause) {
        this(null, cause);
    }

    public VerificationPolicyNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}