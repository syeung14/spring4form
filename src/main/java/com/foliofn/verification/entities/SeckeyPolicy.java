package com.foliofn.verification.entities;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SeckeyPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean seckeyEnabled;
    private int seckeySetupGracePeriodDays;
    private int maxBadLogins;
    // TODO remove, use maxBadLogins to trim
    private int maxBadLoginHistory = 10;

    private String secretAllowedCharacters;
    private boolean scrambleUiEnabled;
    private String secretValidationRegex;
    private int secretLength;
    private String secretRequirementsText;

    public SeckeyPolicy(boolean enabled, int gracePeriod, int maxBad, int historyLen, String chars, String regex,
            int len, boolean scramble, String reqs) {
        seckeyEnabled = enabled;
        seckeySetupGracePeriodDays = gracePeriod;
        maxBadLogins = maxBad;
        maxBadLoginHistory = historyLen;
        secretAllowedCharacters = chars;
        secretValidationRegex = regex;
        secretLength = len;
        scrambleUiEnabled = scramble;
        secretRequirementsText = reqs;
    }

    /**
     * A simple flag allowing the seckey to be temporarily enabled/disabled without modifying
     * complex policy attributes.
     * 
     * @return <code>true</code> if seckey login should be performed, <code>false</code> otherwise.
     */
    public boolean isSeckeyEnabled() {
        return seckeyEnabled;
    }

    public void setSeckeyEnabled(boolean val) {
        seckeyEnabled = val;
    }

    public String getSecretAllowedCharacters() {
        return secretAllowedCharacters;
    }

    public void setSecretAllowedCharacters(String chars) {
        secretAllowedCharacters = chars;
    }

    public boolean isScrambleUiEnabled() {
        return scrambleUiEnabled;
    }

    public void setScrambleUiEnabled(boolean enabled) {
        scrambleUiEnabled = enabled;
    }

    public String getSecretValidationRegex() {
        return secretValidationRegex;
    }

    public void setSecretValidationRegex(String regex) {
        secretValidationRegex = regex;
    }

    public int getSecretLength() {
        return secretLength;
    }

    public void setSecretLength(int len) {
        secretLength = len;
    }

    /**
     * Retrieves the number of days users should be provided before being forced to setup Seckey
     * after instated or -1 if the seckey is optional.
     */
    public int getSeckeySetupGracePeriodDays() {
        return seckeySetupGracePeriodDays;
    }

    public void setSeckeySetupGracePeriodDays(int days) {
        seckeySetupGracePeriodDays = days;
    }

    /**
     * Retrieves the maximum number of bad login attempts before a user will be locked out if
     * lockout is enabled.
     * 
     * @return the maximum number of bad login attempts before user will be locked out.
     */
    public int getSeckeyMaxBadLogins() {
        return maxBadLogins;
    }

    public void setSeckeyMaxBadLogins(int max) {
        maxBadLogins = max;
    }

    public int getMaxBadLoginHistory() {
        return maxBadLoginHistory;
    }

    public void setMaxBadLoginHistory(int max) {
        maxBadLoginHistory = max;
    }

    public String getSecretRequirementsText() {
        return secretRequirementsText;
    }

    public void setSecretRequirementsText(String txt) {
        secretRequirementsText = txt;
    }
    
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}