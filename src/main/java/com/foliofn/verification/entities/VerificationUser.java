package com.foliofn.verification.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * VerificationUser is an entity encapsulating information used for the purpose of verifying a
 * person's identity. Verification in this context refers to auxiliary authentication of credentials
 * beyond the primary tier.
 */
public class VerificationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String username;
    private String partnerCode;
    private String membershipType;
    private Boolean verificationLocked;
    // seckeySettings is an auxiliary object class, not a reference, no tracking needed
    private SeckeySettings seckeySettings;
    private String verificationPolicyId;
    private VerificationPolicy verificationPolicy;
    private List<String> nuxeoDocumentGroups;
    private String restAccessKey;
    private String restAccessKeyStatus;
    private String restSharedSecret;
    private String restInternalFlag;

    public VerificationUser() {
        this(null, null, null, null, false, null, (String)null);
    }

    public VerificationUser(String id, String name, String code, String type, boolean locked, SeckeySettings settings,
            String policyId) {
        userId = id;
        username = name;
        partnerCode = code;
        membershipType = type;
        verificationLocked = locked;
        seckeySettings = settings;
        verificationPolicyId = policyId;
    }

    public VerificationUser(String id, String name, String code, String type, boolean locked, SeckeySettings settings,
            VerificationPolicy policy) {
        userId = id;
        username = name;
        partnerCode = code;
        membershipType = type;
        verificationLocked = locked;
        seckeySettings = settings;
        verificationPolicyId = policy.getId();
        verificationPolicy = policy;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String code) {
        partnerCode = code;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String type) {
        membershipType = type;
    }

    public boolean isLocked() {
        return verificationLocked;
    }

    public void setLocked(boolean locked) {
        this.verificationLocked = locked;
    }

    public SeckeySettings getSeckeySettings() {
        return seckeySettings;
    }

    public void setSeckeySettings(SeckeySettings settings) {
        this.seckeySettings = settings;
    }

    public String getVerificationPolicyId() {
        return verificationPolicyId;
    }

    public void setVerificationPolicyId(String id) {
        verificationPolicyId = id;
        if (verificationPolicy != null && verificationPolicy.getId() != null && verificationPolicy.getId().equals(id))
            verificationPolicy = null;
    }

    public VerificationPolicy getVerificationPolicy() {
        return verificationPolicy;
    }

    public void setVerificationPolicy(VerificationPolicy policy) {
        verificationPolicy = policy;
        if (policy != null)
            verificationPolicyId = policy.getId();
    }

	public List<String> getNuxeoDocumentGroups() {
		return nuxeoDocumentGroups;
	}

	public void setNuxeoDocumentGroup(List<String> nuxeoDocumentGroups) {
		this.nuxeoDocumentGroups = nuxeoDocumentGroups;
	}
	
	public void addNuxeoDocumentGroup(String group) {
		if (!StringUtils.isBlank(group)) {
			if (nuxeoDocumentGroups == null) {
				nuxeoDocumentGroups = new ArrayList<String>();
			}
			nuxeoDocumentGroups.add(group);
		}
	}
	
	public void removeNuxeoDocumentGroup(String group) {
		if (nuxeoDocumentGroups != null) {
			nuxeoDocumentGroups.remove(group);
		}
	}

	public String getRestAccessKey() {
		return restAccessKey;
	}

	public void setRestAccessKey(String restAccessKey) {
		this.restAccessKey = restAccessKey;
	}

	public String getRestAccessKeyStatus() {
		return restAccessKeyStatus;
	}

	public void setRestAccessKeyStatus(String restAccessKeyStatus) {
		this.restAccessKeyStatus = restAccessKeyStatus;
	}

	public String getRestSharedSecret() {
		return restSharedSecret;
	}

	public void setRestSharedSecret(String restSharedSecret) {
		this.restSharedSecret = restSharedSecret;
	}

	public String getRestInternalFlag() {
		return restInternalFlag;
	}

	public void setRestInternalFlag(String restInternalFlag) {
		this.restInternalFlag = restInternalFlag;
	}
	
	public String toString(){
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
        
}