package com.foliofn.verification.repositories.ldap;

import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.CN_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.FOLIOFN_MEMBERSHIP_TYPE_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.FOLIOFN_PARTNER_CODE_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.NUXEO_DCOUMENT_GROUP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_BAD_LOGIN_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_CHANGED_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_CHANGE_BY_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_INSTATED_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SECKEY_SETUP_TIMESTAMP_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.SN_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.UID_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_UNLOCKED_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_POLICY_REFDN_ATTR;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import com.foliofn.verification.entities.SeckeySettings;
import com.foliofn.verification.entities.VerificationUser;

public class VerificationUserModifiedsItemSerializer {

    /**
     * Serializes the user as a stream of directory attributes, key/value pairs. Performs a shallow
     * serialization not traversing any object references such as the associated VerificationPolicy,
     * the caller must do this explicitly.
     * 
     * @param user
     * @return
     */
    public List<ModificationItem> serialize(VerificationUser user) {
        List<ModificationItem> mods = new ArrayList<ModificationItem>();
        SeckeySettings settings;

        // inetOrgPerson attributes
        encodeObject(SN_ATTR, user.getUsername(), mods);
        encodeObject(CN_ATTR, user.getUsername(), mods);
        encodeObject(UID_ATTR, user.getUsername(), mods);

        // identityUser attributes
        encodeObject(FOLIOFN_PARTNER_CODE_ATTR, user.getPartnerCode(), mods);
        encodeObject(FOLIOFN_MEMBERSHIP_TYPE_ATTR, user.getMembershipType(), mods);

        // verificationUser attributes
        encodeBoolean(VERIFICATION_USER_UNLOCKED_ATTR, !user.isLocked(), mods);
        encodeObject(VERIFICATION_USER_POLICY_REFDN_ATTR, user.getVerificationPolicyId(), mods);
        encodeStrings(NUXEO_DCOUMENT_GROUP_ATTR, user.getNuxeoDocumentGroups(), mods);

        // seckeyAuthenticatedUser attributes
        settings = user.getSeckeySettings();
        encodeTimestamp(SECKEY_INSTATED_TIMESTAMP_ATTR, settings.getInstatedTimestamp(), mods);
        encodeTimestamp(SECKEY_SETUP_TIMESTAMP_ATTR, settings.getSetupTimestamp(), mods);
        encodeTimestamp(SECKEY_CHANGE_BY_TIMESTAMP_ATTR, settings.getChangeByTimestamp(), mods);
        encodeTimestamp(SECKEY_CHANGED_TIMESTAMP_ATTR, settings.getChangedTimestamp(), mods);
        encodeTimestamp(SECKEY_GOOD_LOGIN_TIMESTAMP_ATTR, settings.getGoodLoginTimestamp(), mods);
        encodeTimestamps(SECKEY_BAD_LOGIN_TIMESTAMP_ATTR, settings.getBadLoginTimestamps(), mods);

        
        // object classes
        // encodeObjectClasses(mods, VERIFICATION_USER_OBJCLS, SECKEY_AUTHENTICATED_USER_OBJCLS,
        // IDENTITY_USER_OBJCLS,
        // INET_ORG_PERSON_OBJCLS, ORGANIZATIONAL_PERSON_OBJCLS, PERSON_OBJCLS, TOP_OBJCLS);

        return mods;
    }

    private void encodeObject(String id, Object val, List<ModificationItem> mods) {
        BasicAttribute attr;
        ModificationItem item;

        attr = new BasicAttribute(id);
        if (val != null)
            attr.add(val);
        item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
        mods.add(item);
    }

    private void encodeBoolean(String id, Boolean val, List<ModificationItem> mods) {
        encodeObject(id, val != null ? String.valueOf(val) : null, mods);
    }

    private void encodeTimestamp(String id, Date date, List<ModificationItem> mods) {
        String str;

        if (date != null) {
            str = LdapFormats.formatGeneralizedTime(date);
            encodeObject(id, str, mods);
        } else {
            encodeObject(id, null, mods);
        }
    }

    private void encodeTimestamps(String name, List<Date> dates, List<ModificationItem> mods) {
        Attribute attr;
        String str;
        boolean empty = true;

        attr = new BasicAttribute(name);
        if (dates != null) {
            for (Date o : dates) {
                if (empty)
                    empty = false;
                str = LdapFormats.formatGeneralizedTime(o);
                attr.add(str);
            }
        }
        mods.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr));
    }
    
    private void encodeStrings(String name, List<String> strings, List<ModificationItem> mods) {
        Attribute attr;

        attr = new BasicAttribute(name);
        if (strings != null) {
            for (String str : strings) {
                attr.add(str);
            }
        }
        mods.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr));
    }

}