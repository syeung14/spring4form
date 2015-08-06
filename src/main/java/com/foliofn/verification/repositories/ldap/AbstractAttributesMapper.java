package com.foliofn.verification.repositories.ldap;

import static java.lang.String.format;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.support.LdapUtils;

/**
 * AbstractAttributesMapper provides helper methods for mapping simple data types from LDAP
 * attributes.
 */
public abstract class AbstractAttributesMapper implements AttributesMapper {

    private final Logger log = Logger.getLogger(getClass());
   /**
     * Retrieves a Date corresponding to the date/time value of the attribute. The attribute's value
     * must be an ISO 8601/LDAP formatted string.
     * 
     * @param attr
     *            the attribute to read.
     * @return a Date corresponding to the attribute's value.
     * @throws NamingException
     */
    protected Date mapFromDateAttribute(Attribute attr) throws NamingException {
        Date date = null;
        String str;

        str = mapObjectFromAttribute(attr);
        if (!StringUtils.isBlank(str)) {
            try {
                date = LdapFormats.parseGeneralizedTime(str);
            } catch (ParseException e) {
                throw new NamingException(format(
                    "Invalid date/time received from LDAP server '%s' for attribute ID '%s'.", str, attr.getID()));
            }
        }

        return date;
    }

    protected List<Date> mapFromDateAttributes(String name, Attributes attrs) throws NamingException {
        List<String> strings = new ArrayList<String>();
        List<Date> dates = new ArrayList<Date>();
        Attribute attr;
        Date date;

        attr = attrs.get(name);
        if (attr != null) {
             LdapUtils.collectAttributeValues(attrs, name, strings);
            try {
                for (String o : strings) {
                	if (!StringUtils.isBlank(o)) {                		
                		date = LdapFormats.parseGeneralizedTime(o);
                		dates.add(date);
                	} else {
                		log.warn("empty attribute value found for attribute " + name);
                	}
                }
            } catch (ParseException e) {
                throw new NamingException(format("Invalid date/time received from LDAP server for attribute ID '%s'.",
                    attr.getID()));
            }
        }

        return dates;
    }

    protected List<String> mapFromStringAttributes(String name, Attributes attrs) throws NamingException {
        List<String> strings = new ArrayList<String>();
        Attribute attr;

        attr = attrs.get(name);
        LdapUtils.collectAttributeValues(attrs, name, strings);

        return strings;
    }

    /**
     * Retrieves the value of the attribute or <code>null</code> if the argument or its value is
     * <code>null</code>.
     * 
     * @param <T>
     *            type of the attribute's value.
     * @param attr
     *            the attribute to retrieve the value of.
     * @return the value of the attribute or <code>null</code> if the argument or its value is
     *         <code>null</code>.
     * @throws NamingException
     *             if an error occurs getting the value of the attribute.
     */
    @SuppressWarnings("unchecked")
    protected <T> T mapObjectFromAttribute(Attribute attr) throws NamingException {
        return attr != null ? (T)attr.get() : null;
    }

    protected boolean mapFromBooleanAttribute(String id, Attributes attrs, boolean defaultVal) throws NamingException {
        boolean val = defaultVal;
        Attribute attr;
        String str;

        attr = attrs.get(id);
        if (attr != null) {
            str = (String)attr.get();
            if (str != null)
                val = Boolean.parseBoolean(str);
        }

        return val;
    }

    protected int mapFromIntAttribute(String id, Attributes attrs, int defaultVal) throws NamingException {
        int val = defaultVal;
        Attribute attr;
        String str;

        attr = attrs.get(id);
        if (attr != null) {
            str = (String)attr.get();
            if (str != null)
                val = Integer.parseInt(str);
        }

        return val;
    }

    @SuppressWarnings("unchecked")
    protected <T> T mapObjectFromAttributes(String id, Attributes attrs, T defaultVal) throws NamingException {
        Attribute attr;
        T val = defaultVal;

        attr = attrs.get(id);
        if (attr != null)
            val = (T)attr.get();

        return val;
    }
}
