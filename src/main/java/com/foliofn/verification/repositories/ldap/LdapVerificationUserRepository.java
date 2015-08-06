package com.foliofn.verification.repositories.ldap;

import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.OBJECTCLASS_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.UID_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.USER_PASSWORD_ATTR;
import static com.foliofn.verification.repositories.ldap.LdapVerificationSchemaConstants.VERIFICATION_USER_OBJCLS;
import static java.lang.String.format;

import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.InvalidNameException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import com.foliofn.security.InvalidConfigurationSecurityException;
import com.foliofn.verification.entities.VerificationPolicy;
import com.foliofn.verification.entities.VerificationUser;
import com.foliofn.verification.repositories.VerificationPolicyRepository;
import com.foliofn.verification.repositories.VerificationUserNotFoundException;
import com.foliofn.verification.repositories.VerificationUserRepository;

/**
 * <strong>Warning!!</strong><br>
 * This implementation DOES NOT support JTA transactions.
 * <p>
 * <strong>Using DNs or RDNs for References</strong><br>
 * Distinguished Names make the entire directory available to addressing by
 * object references. Use of RDNs is encouraged to limit the scope of an
 * application's access to a subtree containing only its data, limiting the
 * scope of possible attacks.
 */
public class LdapVerificationUserRepository implements
		VerificationUserRepository, InitializingBean {

	private VerificationUserAttributesMapper userAttributesMapper;
	private VerificationUserAttributesSerializer userAttributesSerializer;
	private VerificationUserModifiedsItemSerializer userModifiedItemsSerializer;
	private String validateSeckeyFilterFormat;

	// injected

	private LdapTemplate ldapTemplate;
	private String userDistinguishedNameFormat;
	private VerificationPolicyRepository policyRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (getLdapTemplate() == null)
			throw new InvalidConfigurationSecurityException(
					"The ldapTemplate property must be initialized, provide an initialized Spring LdapTemplate.");

		if (getPolicyRepository() == null)
			throw new InvalidConfigurationSecurityException(
					"The policyRepository property must be initialized, provide an initialized VerificationPolicyRepository implementation.");

		if (getUserDistinguishedNameFormat() == null)
			throw new InvalidConfigurationSecurityException(
					"The userDistinguishedNameFormat property must be initialized, provide a printf style format string.");

		userAttributesMapper = new VerificationUserAttributesMapper();
		userAttributesSerializer = new VerificationUserAttributesSerializer();
		userModifiedItemsSerializer = new VerificationUserModifiedsItemSerializer();
		// makes a printf-style format from another
		validateSeckeyFilterFormat = format("(&(%s=%s)(%s=%%s))",
				OBJECTCLASS_ATTR, VERIFICATION_USER_OBJCLS, UID_ATTR);
	}

	@Override
	public VerificationUser findByUsername(String username) {
		VerificationUser user;
		VerificationPolicy policy;
		String dn;

		if (username == null)
			throw new NullPointerException(
					"Username to find is null, provide a fully initialized String containing a valid username.");

		dn = format(getUserDistinguishedNameFormat(), username.toLowerCase().trim());
		try {
			user = (VerificationUser) getLdapTemplate().lookup(dn,
					userAttributesMapper);
		} catch (NameNotFoundException e) {
			throw new VerificationUserNotFoundException("No user with the DN '"
					+ dn + "' could be found.", e);
		} catch (InvalidNameException e) {
			throw new VerificationUserNotFoundException("No user with the DN '"
					+ dn + "' could be found.", e);
		}

		user.setId(dn);

		// TODO externalize object construction logic
		if (user.getVerificationPolicyId() != null) {
			policy = policyRepository.findById(user.getVerificationPolicyId());
			user.setVerificationPolicy(policy);
		}

		return user;
    }

	@Override
	public void saveOrUpdate(VerificationUser user) {
		saveOrUpdate(user, null);
	}

	@Override
	public void saveOrUpdate(VerificationUser user, String secret) {
	    
		if (user.getId() == null)
			save(user, secret);
		else
			update(user, secret);
	}

	@Override
	public void save(VerificationUser user) {
		save(user, null);
	}

	@Override
	public void save(VerificationUser user, String secret) {
		Attributes attrs;
		String username;
		String dn;

		username = user.getUsername().toLowerCase().trim();
		if (username == null)
			throw new NullPointerException(
					"Cannot persist user with null username, provide a user with an initialized username property.");

		dn = format(getUserDistinguishedNameFormat(), username);
		attrs = userAttributesSerializer.serialize(user);
		if (secret != null)
			attrs.put(USER_PASSWORD_ATTR, secret);
		getLdapTemplate().bind(dn, null, attrs);

		// set ID for newly created users
		if (user.getId() == null)
			user.setId(dn);
	}

	@Override
	public void update(VerificationUser user) {
		update(user, null);
	}

	@Override
	public void update(VerificationUser user, String secret) {
		String username;
		String dn;
		List<ModificationItem> mods;
		
		username = user.getUsername().toLowerCase().trim();
		if (username == null)
			throw new NullPointerException(
					"Cannot persist user with null username, provide a user with an initialized username property.");

		dn = format(getUserDistinguishedNameFormat(), username);
		mods = userModifiedItemsSerializer.serialize(user);
		if (secret != null) {
			BasicAttribute attr = new BasicAttribute(USER_PASSWORD_ATTR, secret);
			ModificationItem item = new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE, attr);
			mods.add(item);
		}
		getLdapTemplate().modifyAttributes(dn,
				mods.toArray(new ModificationItem[mods.size()]));
	}

	@Override
	public boolean validateSeckeySecret(String username, String secret) {
		Boolean valid = Boolean.FALSE;
		String dn;
		AndFilter filter;

		if (username == null)
			throw new NullPointerException(
					"Cannot validate seckey for null username, provide a user with an initialized username property.");

		if (secret == null)
			throw new NullPointerException(
					"Cannot validate seckey for user with null secret, provide a fully initialized string.");

		filter = new AndFilter();
		filter.and(new EqualsFilter(OBJECTCLASS_ATTR, VERIFICATION_USER_OBJCLS));
		filter.and(new EqualsFilter(UID_ATTR, username));

		dn = format(getUserDistinguishedNameFormat(), username);
		valid = ldapTemplate.authenticate(dn, filter.encode(), secret);

		return valid;
	}

	/**
	 * Retrieves the Java printf format string used to form a user's DN
	 * (Distinguished Name). The format must contain a single format specifier
	 * requiring a string containing the respective UID. For example:
	 * <code>"uid=%s,ou=users,dc=verification,dc=foliofn,dc=com"</code>.
	 * 
	 * @return the format string used to form user DN's.
	 */
	public String getUserDistinguishedNameFormat() {
		return userDistinguishedNameFormat;
	}

	/**
	 * Sets the Java printf format string used to form a user's DN
	 * (Distinguished Name). See {@link #getUserDistinguishedNameFormat()} for
	 * further documentation.
	 * 
	 * @param fmt
	 *            the printf format string.
	 */
	public void setUserDistinguishedNameFormat(String fmt) {
		userDistinguishedNameFormat = fmt;
	}

	public VerificationPolicyRepository getPolicyRepository() {
		return policyRepository;
	}

	public void setPolicyRepository(VerificationPolicyRepository rep) {
		policyRepository = rep;
	}

	/**
	 * Retrieves the Spring LdapTemplate used for communicating with LDAP
	 * server.
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