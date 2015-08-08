package com.foliofn.verification.mfa.domain;

import java.util.Calendar;
import java.util.Date;

public class UserDeviceInfo implements java.io.Serializable{
    private static final int DEVICE_COOKIE_EXPIRED_DAY = 60;
    public static final String YES = "Y";
    public static final String NO = "N";
    private static final long serialVersionUID = 7396793555697120951L;
    private String loginId;
    private String deviceid;
    private Date lastverificationDate;
    private Date nextVerificationDate;
    private String ispersonal;
    private String active;

    public UserDeviceInfo(String loginId, String deviceid, Date lastverificationDate, Date nextVerificationDate,
            String ispersonal, String active) {
        this.loginId = loginId;
        this.deviceid = deviceid;
        this.lastverificationDate = lastverificationDate;
        this.nextVerificationDate = nextVerificationDate;
        this.ispersonal = ispersonal;
        this.active = active;
    }
    
    public static UserDeviceInfo createNewDeviceInfo(String loginId, String deviceid){
        Date  current = new Date();
        return new UserDeviceInfo(loginId, deviceid, current, addDays(current, DEVICE_COOKIE_EXPIRED_DAY), YES, YES);
    }
    
    public static UserDeviceInfo createUserLoginRecord(String loginId, String deviceid){
        Date  current = new Date();
        return new UserDeviceInfo(loginId, deviceid, current, current, NO, NO );
    }
    
    
    private static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); 
        return cal.getTime();
    }
    

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Date getLastverificationDate() {
        return lastverificationDate;
    }

    public void setLastverificationDate(Date lastverificationDate) {
        this.lastverificationDate = lastverificationDate;
    }

    public Date getNextVerificationDate() {
        return nextVerificationDate;
    }

    public void setNextVerificationDate(Date nextVerificationDate) {
        this.nextVerificationDate = nextVerificationDate;
    }

    public String getIspersonal() {
        return ispersonal;
    }

    public void setIspersonal(String ispersonal) {
        this.ispersonal = ispersonal;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
