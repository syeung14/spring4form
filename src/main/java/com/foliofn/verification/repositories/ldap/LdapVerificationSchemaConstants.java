package com.foliofn.verification.repositories.ldap;

import com.foliofn.verification.entities.SeckeySettings;
import com.foliofn.verification.entities.VerificationPolicy;
import com.foliofn.verification.entities.VerificationUser;

/**
 * Constants related to the persistence of verification entities in an LDAP directory.
 */
public interface LdapVerificationSchemaConstants {

    // OBJECT CLASSES

    /**
     * Standard attribute.
     */
    public static final String TOP_OBJCLS = "top";

    /**
     * Standard attribute.
     */
    public static final String PERSON_OBJCLS = "person";

    /**
     * Standard attribute.
     */
    public static final String ORGANIZATIONAL_PERSON_OBJCLS = "organizationalPerson";

    /**
     * Standard attribute.
     */
    public static final String INET_ORG_PERSON_OBJCLS = "inetOrgPerson";

    /**
     * Standard attribute.
     */
    public static final String GROUP_OF_UNIQUE_NAMES_OBJCLS = "groupOfUniqueNames";

    /**
     * Standard attribute.
     */
    public static final String GROUP_OF_URLS_OBJCLS = "groupOfUrls";

    /**
     * The LDAP object class for identity related attributes, cached by verification for ease of
     * management.
     */
    public static final String IDENTITY_USER_OBJCLS = "identityUser";

    /**
     * The object class corresponding to the {@link VerificationUser} entity.
     */
    public static final String VERIFICATION_USER_OBJCLS = "verificationUser";

    /**
     * The object class corresponding to the {@link VerificationPolicy} entity.
     */
    public static final String VERIFICATION_POLICY_OBJCLS = "verificationPolicy";

    /**
     * The object class corresponding to the {@link SeckeyPolicy} entity.
     */
    public static final String SECKEY_POLICY_OBJCLS = "seckeyPolicy";

    /**
     * The object class corresponding to the {@link SeckeySettings} object.
     */
    public static final String SECKEY_AUTHENTICATED_USER_OBJCLS = "seckeyAuthenticatedUser";

    // ATTRIBUTES

    /**
     * The object class attribute name.
     * <p>
     * Requires a {@link String} value.
     */
    public static final String OBJECTCLASS_ATTR = "objectClass";
    
    /*
     * GROUPOFUNIQUENAMES ATTRIBUTES
     */
    
    public static final String UNIQUE_MEMBER_ATTR = "uniqueMember";

    /*
     * GROUPOFURLS ATTRIBUTES
     */
    
    public static final String MEMBER_URL_ATTR = "memberUrl";

    /*
     * INETORGPERSON ATTRIBUTES
     */

    /**
     * The UID (User ID) attribute ID used to log into the system, not the same as a UNIX UID.
     * <p>
     * Requires a {@link String} value.
     */
    public static final String UID_ATTR = "uid";

    /**
     * The CN (Common Name) attribute ID, the non changing canonical name of the record typically
     * used for lookups and searches.
     * <p>
     * Requires a {@link String} value.
     * <p>
     * Used by the {@value #VERIFICATION_USER_OBJCLS}, {@value #VERIFICATION_POLICY_OBJCLS} and
     * {@value #GROUP_OF_UNIQUE_NAMES_OBJCLS} 
     * object classes.
     */
    public static final String CN_ATTR = "cn";

    /**
     * The SN (Surname) attribute ID, the non changing canonical name of the record typically used
     * for lookups and searches.
     * <p>
     * Requires a {@link String} value.
     * <p>
     * Used by the {@value #VERIFICATION_USER_OBJCLS} and {@value #VERIFICATION_POLICY_OBJCLS}
     * object classes.
     */
    public static final String SN_ATTR = "sn";
    

    public static final String USER_PASSWORD_ATTR = "userPassword";

    public static final String USER_PASSWORD_POLICY_ATTR = "passwordpolicysubentry";

    /*
     * IDENTITY ATTRIBUTES
     */

    /**
     * The FOLIOfn issued Partner Code.
     */
    public static final String FOLIOFN_PARTNER_CODE_ATTR = "identityUserPartnerCode";

    /**
     * The FOLIOfn defined membership type indicator.
     */
    public static final String FOLIOFN_MEMBERSHIP_TYPE_ATTR = "identityUserMembershipType";

    /*
     * VERIFICATIONUSER ATTRIBUTES
     */

    /**
     * Indicates the entry has been locked out of the verification process completely, disallowing
     * them from authenticating.
     */
    public static final String VERIFICATION_USER_UNLOCKED_ATTR = "verificationUserUnlocked";

    /**
     * The DN (Distinguished Name) of the verification policy in effect for an entry.
     * <p>
     * Requires a distinguished name, {@link String}, value pointing to a
     * {@value #VERIFICATION_POLICY_OBJCLS} entry.
     * <p>
     * Used by the {@value #VERIFICATION_USER_OBJCLS} object class.
     */
    public static final String VERIFICATION_USER_POLICY_REFDN_ATTR = "verificationUserPolicyRefDN";

    /*
     * SECKEYAUTHENTICATEDUSER ATTRIBUTES
     */

    public static final String SECKEY_SECRET_ATTR = "userSeckey";

    /**
     * The timestamp on which seckey authentication will be enforced for the user.
     * <p>
     * Requires a {@link java.util.Date} value.
     * <p>
     * Used by the {@value #SECKEY_AUTHENTICATED_USER_OBJCLS} object class.
     */
    public static final String SECKEY_INSTATED_TIMESTAMP_ATTR = "seckeyUserInstatedTimestamp";

    /**
     * The timestamp on which seckey setup was performed by the user.
     * <p>
     * Requires a {@link java.util.Date} value.
     * <p>
     * Used by the {@value #SECKEY_AUTHENTICATED_USER_OBJCLS} object class.
     */
    public static final String SECKEY_SETUP_TIMESTAMP_ATTR = "seckeyUserSetupTimestamp";

    /**
     * The date/time of the last update to the associated <code>userSeckey</code> attribute. The
     * value of this attribute can be used to determine if a required seckey secret change has been
     * fulfilled.
     * <p>
     * Requires a {@link java.util.Date} value.
     * <p>
     * Used by the {@value #SECKEY_AUTHENTICATED_USER_OBJCLS} object class.
     */
    public static final String SECKEY_CHANGED_TIMESTAMP_ATTR = "seckeyUserChangedTimestamp";

    /**
     * The timestamp by which the the userSeckey attribute must be changed before the user will be
     * forced to do so before authenticating. Used together with
     * {@value #SECKEY_CHANGED_TIMESTAMP_ATTR} to determine if a change is required before allowing
     * authentication.
     * <p>
     * Requires a {@link java.util.Date} value.
     * <p>
     * Used by the {@value #SECKEY_AUTHENTICATED_USER_OBJCLS} object class.
     */
    public static final String SECKEY_CHANGE_BY_TIMESTAMP_ATTR = "seckeyUserChangeByTimestamp";

    /**
     * The timestamp of the user's last successful seckey authentication. Used together with
     * {@value #SECKEY_BAD_LOGIN_TIMESTAMP_ATTR} and
     * {@value #SECKEY_POLICY_MAX_BAD_LOGIN_COUNT_ATTR} to determine if an entry should be locked.
     * <p>
     * Requires a {@link java.util.Date} value.
     * <p>
     * Used by the {@value #SECKEY_AUTHENTICATED_USER_OBJCLS} object class.
     */
    public static final String SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR = "seckeyUserGoodLoginTimestamp";

    /**
     * The timestamps of the user's last 10 failed seckey authentication attempts. Used together
     * with {@value #SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR} and
     * {@value #SECKEY_POLICY_MAX_BAD_LOGIN_COUNT_ATTR} to determine if an entry should be locked.
     * <p>
     * Requires zero or more {@link java.util.Date} values.
     * <p>
     * Used by the {@value #SECKEY_AUTHENTICATED_USER_OBJCLS} object class.
     */
    public static final String SECKEY_BAD_LOGIN_TIMESTAMP_ATTR = "seckeyUserBadLoginTimestamp";

    /*
     * VERIFICATIONPOLICY ATTRIBUTES
     */

    /**
     * Indicates if locking out of users after max failed logins is enabled.
     * <p>
     * Requires a boolean value.
     * <p>
     * Used by the {@value #VERIFICATION_POLICY_OBJCLS} object class.
     */
    public static final String VERIFICATION_DISABLED_ATTR = "verificationDisabled";

    /**
     * Indicates if locking out of users after max failed logins is enabled.
     * <p>
     * Requires a boolean value.
     * <p>
     * Used by the {@value #VERIFICATION_POLICY_OBJCLS} object class.
     */
    public static final String VERIFICATION_LOCKOUT_DISABLED_ATTR = "verificationLockoutDisabled";
    
    // SECKEYPOLICY ATTRIBUTES
    										
    public static final String SECKEY_POLICY_ALLOWED_CHARACTERS_ATTR = "seckeyPolicyAllowedCharacters";
    
    public static final String SECKEY_POLICY_SCRAMBLE_DISABLED_ATTR = "seckeyPolicyScrambleDisabled";
    
    public static final String SECKEY_POLICY_SECRET_VALIDATION_REGEX_ATTR = "seckeyPolicySecretValidationRegex";
    
    public static final String SECKEY_POLICY_SECRET_LENGTH_ATTR = "seckeyPolicySecretLength";

    public static final String SECKEY_POLICY_SECRET_REQUIREMENTS_TEXT_ATTR = "seckeyPolicySecretRequirementsText";

    /**
     * Indicates if Seckey authentication should skipped.
     * <p>
     * Requires a boolean value.
     * <p>
     * Used by the {@value #VERIFICATION_POLICY_OBJCLS} object class.
     */
    public static final String SECKEY_POLICY_DISABLED_ATTR = "seckeyPolicyDisabled";

    /**
     * The grace period in days allowed a user before they're forced to perform Seckey setup. Used
     * together with {@value #SECKEY_INSTATED_TIMESTAMP_ATTR} to determine if a user should be
     * forced to perform Seckey setup.
     * <p>
     * Requires an int value.
     * <p>
     * Used by the {@value #VERIFICATION_POLICY_OBJCLS} object class.
     */
    public static final String SECKEY_POLICY_SETUP_GRACE_PERIOD_DAYS_ATTR = "seckeyPolicySetupGracePeriodDays";

    /**
     * The maximum number of failed login attempts a user may perform before being locked. Used
     * together with {@value #SECKEY_BAD_LOGIN_TIMESTAMP_ATTR} to determine if a user should be
     * locked.
     * <p>
     * Requires an int value.
     * <p>
     * Used by the {@value #VERIFICATION_POLICY_OBJCLS} object class.
     */
    public static final String SECKEY_POLICY_MAX_BAD_LOGIN_COUNT_ATTR = "seckeyPolicyMaxBadLoginCount";

    /**
     * The document group(s) this user belongs to
     * mainly used by Nuxeo
     * <p>
     * Requires an String value.
     * <p>
     */
    public static final String NUXEO_DCOUMENT_GROUP_ATTR = "nuxeoDocumentGroup";
    
    /**
     * RESTful API specific values
     */
    public static final String REST_ACCESS_KEY = "restAccessKey";
    public static final String REST_ACCESS_KEY_STATUS = "restAccessKeyStatus";
    public static final String REST_SHARED_SECRET = "restSharedSecret";
    public static final String REST_INTERNAL_FLAG = "restInternalFlag";

}