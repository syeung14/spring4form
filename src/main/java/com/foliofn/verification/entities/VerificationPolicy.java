package com.foliofn.verification.entities;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * VerificationPolicy is an immutable, externally managed entity for policy information related to
 * user identity verification.
 */
public class VerificationPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    private String policyId;
    private String displayName;
    private boolean verificationEnabled;
    private boolean lockoutEnabled;
    private SeckeyPolicy seckeyPolicy;

    public VerificationPolicy() {
        this(null, null, true, true, null);
    }

    public VerificationPolicy(String id, String name, boolean enabled, boolean lockout, SeckeyPolicy seckey) {
        policyId = id;
        displayName = name;
        verificationEnabled = enabled;
        lockoutEnabled = lockout;
        seckeyPolicy = seckey;
    }

    public String getId() {
        return policyId;
    }

    public void setId(String id) {
        policyId = id;
    }

    /**
     * Retrieves the policy's display name.
     * 
     * @return the policy's display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        displayName = name;
    }

    /**
     * Indicates users should be locked out after {@link #getSeckeyMaxBadLogins()}.
     * 
     * @return <code>true</code> if users should be locked out after
     *         {@link #getSeckeyMaxBadLogins()}, <code>false</code> otherwise.
     */
    public boolean isLockoutEnabled() {
        return lockoutEnabled;
    }

    public void setLockoutEnabled(boolean enabled) {
        lockoutEnabled = enabled;
    }

    /**
     * Retrieves a boolean indicating if verification is enabled for the user. If verification is
     * disabled the user may login transparently without providing any credentials indefinitely.
     * <p>
     * <strong>Warning</strong><br>
     * Disabling verification requires both verificationEnabled and seckeyEnabled are false, an
     * exception will be thrown by the VerificationPolicyEnforcer if this condition occurs. This a
     * protective measure to ensure verification isn't unintentionally disabled.
     * 
     * @return a boolean indicaiting if verification is enabled for user.
     */
    public boolean isVerificationEnabled() {
        return verificationEnabled;
    }

    public void setVerificationEnabled(boolean enabled) {
        verificationEnabled = enabled;
    }

    public SeckeyPolicy getSeckeyPolicy() {
        return seckeyPolicy;
    }

    public void setSeckeyPolicy(SeckeyPolicy seckey) {
        seckeyPolicy = seckey;
    }
    
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}