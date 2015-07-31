package com.foliofn.mvc.mfa.model;

import java.util.Date;

public class MfaDeviceInfo {
	private String loginId;
	private String deviceId;

	private boolean isPersonal;
	private Date lastVerificationTs;
	private Date nextVerificationTs;

	private boolean isActive;

	private MfaDeviceInfo(Builder build) {
		this.loginId = build.loginId;
		this.deviceId = build.deviceId;
		this.isActive = build.isActive;
		this.isPersonal = build.isPersonal;
		this.lastVerificationTs = build.lastVerificationTs;
		this.nextVerificationTs = build.nextVerificationTs;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public boolean isPersonal() {
		return isPersonal;
	}

	public Date getLastVerificationTs() {
		return lastVerificationTs;
	}

	public Date getNextVerificationTs() {
		return nextVerificationTs;
	}

	public boolean isActive() {
		return isActive;
	}

	public static class Builder {
		private String loginId;
		private String deviceId;

		private boolean isPersonal;
		private Date lastVerificationTs;
		private Date nextVerificationTs;

		private boolean isActive;

		public Builder(String deviceId, String loginId) {
			this.deviceId = deviceId;
			this.loginId = loginId;
		}
		public Builder setLoginId(String loginId) {
			this.loginId = loginId;
			return this;
		}

		public Builder setDeviceId(String deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder setPersonal(boolean isPersonal) {
			this.isPersonal = isPersonal;
			return this;
		}

		public Builder setLastVerificationTs(Date lastVerificationTs) {
			this.lastVerificationTs = lastVerificationTs;
			return this;
		}

		public Builder setNextVerificationTs(Date nextVerificationTs) {
			this.nextVerificationTs = nextVerificationTs;
			return this;
		}

		public Builder setActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}

		public MfaDeviceInfo build() {
			return new MfaDeviceInfo(this);
		}
	}

}
