package com.foliofn.verification.mfa.domain;

public class MfaUserInfo implements java.io.Serializable {

    private static final long serialVersionUID = -1929738780267677663L;
    private String userId = null;
    private String emailAddress1 = null;
    private String emailAddress2 = null;
    private String homeTelephone = null;
    private String workTelephone = null;

    public MfaUserInfo(String userId, String emailAddress1, String emailAddress2, String homeTelephone,
            String workTelephone) {
        this.userId = userId;
        this.emailAddress1 = emailAddress1;
        this.emailAddress2 = emailAddress2;
        this.homeTelephone = homeTelephone;
        this.workTelephone = workTelephone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress1() {
        return emailAddress1;
    }

    public void setEmailAddress1(String emailAddress1) {
        this.emailAddress1 = emailAddress1;
    }

    public String getEmailAddress2() {
        return emailAddress2;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public String getHomeTelephone() {
        return homeTelephone;
    }

    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    public String getWorkTelephone() {
        return workTelephone;
    }

    public void setWorkTelephone(String workTelephone) {
        this.workTelephone = workTelephone;
    }

}
