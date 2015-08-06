package com.foliofn.security;

/**
 * FataSecurityException indicates the system has encountered an error in the implementation
 * preventing correct operating. The system most likely will be unusuable and the security of the
 * system could be compromised so it should be taken out of service immediately.
 */
public class FatalSecurityException extends SecurityException {

    private static final long serialVersionUID = 1L;

    public FatalSecurityException() {
        super();
    }

    public FatalSecurityException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FatalSecurityException(String msg) {
        super(msg);
    }

    public FatalSecurityException(Throwable cause) {
        super(cause);
    }
}