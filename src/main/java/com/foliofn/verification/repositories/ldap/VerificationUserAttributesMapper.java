package com.foliofn.verification.repositories.ldap;

import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.FOLIOFN_MEMBERSHIP_TYPE_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.FOLIOFN_PARTNER_CODE_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.NUXEO_DCOUMENT_GROUP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.REST_ACCESS_KEY;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.REST_ACCESS_KEY_STATUS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.REST_INTERNAL_FLAG;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.REST_SHARED_SECRET;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_BAD_LOGIN_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_CHANGED_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_CHANGE_BY_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_INSTATED_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_SETUP_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.UID_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_POLICY_REFDN_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_UNLOCKED_ATTR;
import static java.lang.String.format;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.NoSuchAttributeException;

import com.foliofn.security.InvalidConfigurationSecurityException;
import com.foliofn.verification.entities.SeckeySettings;
import com.foliofn.verification.entities.VerificationUser;

/**
 * VerificationUserAttributesMapper implements Spring's LDAP AttributesMapper interface handling the
 * creation of VerificationUser instances and the binding of LDAP attributes to them.
 * VerificationPolicyAttributesMapper is used to load the user's policy.
 */
public class VerificationUserAttributesMapper extends AbstractAttributesMapper {

    @Override
    public Object mapFromAttributes(Attributes attrs) throws NamingException {
        VerificationUser user = new VerificationUser();

        mapFromVerificationUserAttributes(user, attrs);
        mapFromSeckeyAuthenticatedUserAttributes(user, attrs);
        bindVerificationPolicy(user, attrs.get(VERIFICATION_USER_POLICY_REFDN_ATTR));

        return user;
    }

    /**
     * Binds attributes defined by the verificationUser object class.
     * 
     * @param attrs
     *            the defined attributes and their values.
     */
    private void mapFromVerificationUserAttributes(VerificationUser user, Attributes attrs) throws NamingException {
        String username;
        String code;
        String type;
        List<String> nuxeoDocumentGroups = null;
        boolean unlocked;

        username = mapObjectFromAttribute(attrs.get(UID_ATTR));
        
        //Fix for DEV-7654: found a user with trailing white spaces in 'uid' in EAP environment.
        if (username != null) {
        	username = username.trim();
        }
        
        code = mapObjectFromAttribute(attrs.get(FOLIOFN_PARTNER_CODE_ATTR));
        type = mapObjectFromAttribute(attrs.get(FOLIOFN_MEMBERSHIP_TYPE_ATTR));
        unlocked = mapFromBooleanAttribute(VERIFICATION_USER_UNLOCKED_ATTR, attrs, true);
        try {
        	nuxeoDocumentGroups = this.mapFromStringAttributes(NUXEO_DCOUMENT_GROUP_ATTR, attrs);
        } catch (NoSuchAttributeException e) {
        	; // do nothing, the user's 'nuxeoDocumentGroup' might not be initialized yet.
        }

        user.setUsername(username);
        user.setPartnerCode(code);
        user.setMembershipType(type);
        user.setLocked(!unlocked);
        user.setNuxeoDocumentGroup(nuxeoDocumentGroups);
        user.setRestAccessKey((String) mapObjectFromAttribute(attrs.get(REST_ACCESS_KEY)));
        user.setRestAccessKeyStatus((String) mapObjectFromAttribute(attrs.get(REST_ACCESS_KEY_STATUS))); 
        user.setRestSharedSecret((String) mapObjectFromAttribute(attrs.get(REST_SHARED_SECRET))); 
        user.setRestInternalFlag((String) mapObjectFromAttribute(attrs.get(REST_INTERNAL_FLAG))); 
    }

    /**
     * Binds attributes defined by the seckeyAuthenticatedUser object class.
     * 
     * @param attrs
     *            the defined attributes and their values.
     */
    private void mapFromSeckeyAuthenticatedUserAttributes(VerificationUser user, Attributes attrs)
            throws NamingException {
        SeckeySettings settings;
        Date instatedTs;
        Date setupTs;
        Date modifiedTs;
        Date changeByTs;
        Date loginTs;
        List<Date> failedLoginSet;

        instatedTs = mapFromDateAttribute(attrs.get(SECKEY_INSTATED_TIMESTAMP_ATTR));
        setupTs = mapFromDateAttribute(attrs.get(SECKEY_SETUP_TIMESTAMP_ATTR));
        modifiedTs = mapFromDateAttribute(attrs.get(SECKEY_CHANGED_TIMESTAMP_ATTR));
        changeByTs = mapFromDateAttribute(attrs.get(SECKEY_CHANGE_BY_TIMESTAMP_ATTR));
        loginTs = mapFromDateAttribute(attrs.get(SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR));
        failedLoginSet = mapFromDateAttributes(SECKEY_BAD_LOGIN_TIMESTAMP_ATTR, attrs);

        settings = new SeckeySettings(instatedTs, setupTs, modifiedTs, changeByTs, loginTs, failedLoginSet);
        user.setSeckeySettings(settings);
    }

    /**
     * Binds the VerificationPolicy for the user, possibly reading it from the LDAP directory.
     * 
     * @param attrs
     *            the defined attributes and their values.
     * @throws NamingException
     *             if an error occurs communicating with the LDAP server.
     * @throws InvalidConfigurationSecurityException
     *             if the user has an invalid verification policy value.
     */
    private void bindVerificationPolicy(VerificationUser user, Attribute attr) throws NamingException {
        String dn;

        // get the policy name
        dn = mapObjectFromAttribute(attr);
        if (dn == null)
            throw new InvalidConfigurationSecurityException(
                format(
                    "The user '%s' does not have an associated verification policy, associate a verification policy with the user's LDAP entry.",
                    user.getUsername()));

        user.setVerificationPolicyId((String)attr.get());
    }
}