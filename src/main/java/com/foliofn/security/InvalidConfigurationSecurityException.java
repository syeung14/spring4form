package com.foliofn.security;

/**
 * InvalidConfigurationSecurityException is thrown when the application's security related
 * configuration is incorrect.
 */
public class InvalidConfigurationSecurityException extends FatalSecurityException {

    private static final long serialVersionUID = 1L;

    public InvalidConfigurationSecurityException() {
        this(null, null);
    }

    public InvalidConfigurationSecurityException(String msg) {
        this(msg, null);
    }

    public InvalidConfigurationSecurityException(Throwable cause) {
        this(null, cause);
    }

    public InvalidConfigurationSecurityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}