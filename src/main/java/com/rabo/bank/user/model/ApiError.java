package com.rabo.bank.user.model;

public class ApiError {
	public ApiError(String reason, ReasonCode reasonCode) {
		super();
		this.reason = reason;
		this.reasonCode = reasonCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public ReasonCode getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(ReasonCode reasonCode) {
		this.reasonCode = reasonCode;
	}
	String reason;
	ReasonCode reasonCode;
	
}
