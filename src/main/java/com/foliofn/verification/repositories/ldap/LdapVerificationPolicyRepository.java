package com.foliofn.verification.repositories.ldap;

import static java.lang.String.format;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.InvalidNameException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.LdapTemplate;

import com.foliofn.security.InvalidConfigurationSecurityException;
import com.foliofn.verification.entities.VerificationPolicy;
import com.foliofn.verification.repositories.VerificationPolicyNotFoundException;
import com.foliofn.verification.repositories.VerificationPolicyRepository;
import com.foliofn.verification.repositories.VerificationUserNotFoundException;

public class LdapVerificationPolicyRepository implements VerificationPolicyRepository, InitializingBean {

    private LdapTemplate ldapTemplate;
    private String policyDistinguishedNameFormat;
    private VerificationPolicyAttributesMapper policyAttributesMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (getLdapTemplate() == null)
            throw new InvalidConfigurationSecurityException(
                "The ldapTemplate property must be initialized, provide an initialized Spring LdapTemplate.");

        if (getPolicyDistinguishedNameFormat() == null)
            throw new InvalidConfigurationSecurityException(
                "The policyDistinguishedNameFormat property must be initialized, provide a printf style format string.");

        policyAttributesMapper = new VerificationPolicyAttributesMapper();
    }

    @Override
    public VerificationPolicy findById(String id) {
        VerificationPolicy policy;

        if (id == null)
            throw new NullPointerException(
                "The policy ID to find may not be null, provide a non-null string containing a valid policy ID.");

        try {
            policy = (VerificationPolicy)getLdapTemplate().lookup(id, policyAttributesMapper);
        } catch (NameNotFoundException e) {
            throw new VerificationPolicyNotFoundException("No policy with the DN '" + id + "' could be found.");
        } catch (InvalidNameException e) {
            throw new VerificationUserNotFoundException("No policy with the DN '" + id + "' could be found.", e);
        }
        policy.setId(id);

        return policy;
    }

    @Override
    public VerificationPolicy findByName(String name) {
        VerificationPolicy policy;
        String dn;

        if (name == null)
            throw new NullPointerException(
                "The policy name to find may not be null, provide a non-null string containing a valid policy name.");

        dn = format(getPolicyDistinguishedNameFormat(), name);
        policy = findById(dn);

        return policy;
    }

    /**
     * Retrieves the Java printf format string used to form a verification policy's DN
     * (Distinguished Name). The format must contain a single format specifier requiring a string
     * containing the respective CN. For example:
     * <code>"cn=%s,ou=system,dc=verification,dc=foliofn,dc=com"</code>.
     * 
     * @return the format string used to form policy DN's.
     */
    public String getPolicyDistinguishedNameFormat() {
        return policyDistinguishedNameFormat;
    }

    /**
     * Sets the Java printf format string used to form a verification policy's DN (Distinguished
     * Name). See {@link #getPolicyDistinguishedNameFormat()} for further documentation.
     * 
     * @param fmt
     *            the printf format string.
     */
    public void setPolicyDistinguishedNameFormat(String fmt) {
        policyDistinguishedNameFormat = fmt;
    }

    /**
     * Retrieves the Spring LdapTemplate used for communicating with LDAP server.
     * 
     * @return the Spring LdapTemplate used for communicating with LDAP server.
     */
    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    /**
     * Sets the Spring LdapTemplate used to communicate with the LDAP server.
     * 
     * @param tmpl
     *            the Spring LdapTemplate to use.
     */
    public void setLdapTemplate(LdapTemplate tmpl) {
        ldapTemplate = tmpl;
    }
}