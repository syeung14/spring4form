package com.foliofn.verification.repositories.ldap;

import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.CN_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_POLICY_ALLOWED_CHARACTERS_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_POLICY_DISABLED_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_POLICY_MAX_BAD_LOGIN_COUNT_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_POLICY_SCRAMBLE_DISABLED_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_POLICY_SECRET_LENGTH_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_POLICY_SECRET_VALIDATION_REGEX_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_POLICY_SETUP_GRACE_PERIOD_DAYS_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_DISABLED_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_LOCKOUT_DISABLED_ATTR;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import com.foliofn.verification.entities.SeckeyPolicy;
import com.foliofn.verification.entities.VerificationPolicy;

public class VerificationPolicyAttributesMapper extends AbstractAttributesMapper {

    @Override
    public Object mapFromAttributes(Attributes attrs) throws NamingException {
        VerificationPolicy policy;
        SeckeyPolicy seckey;
        String name;
        boolean disabled;
        boolean userLockout;
        boolean seckeyDisabled;
        int seckeyGracePeriodDays;
        int seckeyMaxBadLogins;

        String secretAllowedCharacters;
        boolean scrambleUiDisabled;
        String secretValidationRegex;
        int secretLength;
        String secretRequirements;

        name = mapObjectFromAttribute(attrs.get(CN_ATTR));
        disabled = mapFromBooleanAttribute(VERIFICATION_DISABLED_ATTR, attrs, false);
        userLockout = mapFromBooleanAttribute(VERIFICATION_LOCKOUT_DISABLED_ATTR, attrs, true);

        seckeyDisabled = mapFromBooleanAttribute(SECKEY_POLICY_DISABLED_ATTR, attrs, false);
        seckeyGracePeriodDays = mapFromIntAttribute(SECKEY_POLICY_SETUP_GRACE_PERIOD_DAYS_ATTR, attrs, -1);
        seckeyMaxBadLogins = mapFromIntAttribute(SECKEY_POLICY_MAX_BAD_LOGIN_COUNT_ATTR, attrs, -1);
        secretAllowedCharacters = mapObjectFromAttributes(SECKEY_POLICY_ALLOWED_CHARACTERS_ATTR, attrs, null);
        scrambleUiDisabled = mapFromBooleanAttribute(SECKEY_POLICY_SCRAMBLE_DISABLED_ATTR, attrs, false);
        secretValidationRegex = mapObjectFromAttributes(SECKEY_POLICY_SECRET_VALIDATION_REGEX_ATTR, attrs, null);
        secretLength = mapFromIntAttribute(SECKEY_POLICY_SECRET_LENGTH_ATTR, attrs, 0);
        secretRequirements =
                mapObjectFromAttributes(LdapVerificationSchemaConstants.SECKEY_POLICY_SECRET_REQUIREMENTS_TEXT_ATTR,
                    attrs, null);

        seckey =
                new SeckeyPolicy(!seckeyDisabled, seckeyGracePeriodDays, seckeyMaxBadLogins, 10,
                    secretAllowedCharacters, secretValidationRegex, secretLength, !scrambleUiDisabled,
                    secretRequirements);
        policy = new VerificationPolicy(null, name, !disabled, userLockout, seckey);

        return policy;
    }
}