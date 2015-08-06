package com.foliofn.verification.repositories.ldap;

import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.CN_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.FOLIOFN_MEMBERSHIP_TYPE_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.FOLIOFN_PARTNER_CODE_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.IDENTITY_USER_OBJCLS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.INET_ORG_PERSON_OBJCLS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.NUXEO_DCOUMENT_GROUP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.OBJECTCLASS_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.ORGANIZATIONAL_PERSON_OBJCLS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.PERSON_OBJCLS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_AUTHENTICATED_USER_OBJCLS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_BAD_LOGIN_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_CHANGED_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_CHANGE_BY_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_INSTATED_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_SETUP_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SN_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.TOP_OBJCLS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.UID_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.USER_PASSWORD_POLICY_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_OBJCLS;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_POLICY_REFDN_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_UNLOCKED_ATTR;

import java.util.Date;
import java.util.List;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import com.foliofn.verification.entities.SeckeySettings;
import com.foliofn.verification.entities.VerificationUser;

public class VerificationUserAttributesSerializer {

    /**
     * Serializes the user as a stream of directory attributes, key/value pairs. Performs a shallow
     * serialization not traversing any object references such as the associated VerificationPolicy,
     * the caller must do this explicitly.
     * 
     * @param user
     * @return
     */
    public Attributes serialize(VerificationUser user) {
        Attributes attrs = new BasicAttributes();
        SeckeySettings settings;

        String username = user.getUsername().toLowerCase().trim();
        
        // inetOrgPerson attributes
        encodeObject(SN_ATTR, username, attrs);
        encodeObject(CN_ATTR, username, attrs);
        encodeObject(UID_ATTR, username, attrs);
        encodeObject(USER_PASSWORD_POLICY_ATTR, "cn=SeckeyPasswordPolicy,ou=system,dc=verification,dc=foliofn,dc=com", attrs);

        // identityUser attributes
        encodeObject(FOLIOFN_PARTNER_CODE_ATTR, user.getPartnerCode(), attrs);
        encodeObject(FOLIOFN_MEMBERSHIP_TYPE_ATTR, user.getMembershipType(), attrs);

        // verificationUser attributes
        encodeBoolean(VERIFICATION_USER_UNLOCKED_ATTR, !user.isLocked(), attrs);
        attrs.put(VERIFICATION_USER_POLICY_REFDN_ATTR, user.getVerificationPolicyId());

        // seckeyAuthenticatedUser attributes
        settings = user.getSeckeySettings();
        encodeTimestamp(SECKEY_INSTATED_TIMESTAMP_ATTR, settings.getInstatedTimestamp(), attrs);
        encodeTimestamp(SECKEY_SETUP_TIMESTAMP_ATTR, settings.getSetupTimestamp(), attrs);
        encodeTimestamp(SECKEY_CHANGE_BY_TIMESTAMP_ATTR, settings.getChangeByTimestamp(), attrs);
        encodeTimestamp(SECKEY_CHANGED_TIMESTAMP_ATTR, settings.getChangedTimestamp(), attrs);
        encodeTimestamp(SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR, settings.getGoodLoginTimestamp(), attrs);
        encodeTimestamps(SECKEY_BAD_LOGIN_TIMESTAMP_ATTR, settings.getBadLoginTimestamps(), attrs);

        // object classes
        encodeObjectClasses(attrs, VERIFICATION_USER_OBJCLS, SECKEY_AUTHENTICATED_USER_OBJCLS, IDENTITY_USER_OBJCLS,
            INET_ORG_PERSON_OBJCLS, ORGANIZATIONAL_PERSON_OBJCLS, PERSON_OBJCLS, TOP_OBJCLS);

        List<String> nuxeoDocumentGroups = user.getNuxeoDocumentGroups();
        if (nuxeoDocumentGroups != null && nuxeoDocumentGroups.size() > 0) {        	
        	encodeStrings(NUXEO_DCOUMENT_GROUP_ATTR, user.getNuxeoDocumentGroups(), attrs);
        }
        return attrs;
    }

    private void encodeObject(String id, Object val, Attributes attrs) {
        if (val != null)
            attrs.put(id, val);
    }

    private void encodeBoolean(String id, Object val, Attributes attrs) {
        attrs.put(id, String.valueOf(val));
    }

    private void encodeObjectClasses(Attributes attrs, String... classes) {
        BasicAttribute attr;

        if (classes == null || classes.length < 1)
            return;

        attr = new BasicAttribute(OBJECTCLASS_ATTR);
        for (String o : classes)
            attr.add(o);
        attrs.put(attr);
    }

    private void encodeTimestamp(String name, Date date, Attributes attrs) {
        String str;

        if (date != null) {
            str = LdapFormats.formatGeneralizedTime(date);
            attrs.put(name, str);
        }
    }

    private void encodeTimestamps(String name, List<Date> dates, Attributes attrs) {
        Attribute attr;
        String str;
        boolean empty = true;

        if (dates != null) {
            attr = new BasicAttribute(name);
            for (Date o : dates) {
                if (empty)
                    empty = false;
                str = LdapFormats.formatGeneralizedTime(o);
                attr.add(str);
            }

            if (!empty)
                attrs.put(attr);
        }
    }
    private void encodeStrings(String name, List<String> strings, Attributes attrs) {
        Attribute attr;

        attr = new BasicAttribute(name);
        if (strings != null) {
            for (String str : strings) {
                attr.add(str);
            }
        }
        attrs.put(attr);
    }
}