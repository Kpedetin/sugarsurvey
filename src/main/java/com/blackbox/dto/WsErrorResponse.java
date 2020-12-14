package com.blackbox.dto;

public class WsErrorResponse {

	private String code;
	private String message;
	private String additionnalMessage;

	public WsErrorResponse() {
	}

	public WsErrorResponse(String code, String message, String additionnalMessage) {
		this.code = code;
		this.message = message;
		this.additionnalMessage = additionnalMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAdditionnalMessage() {
		return additionnalMessage;
	}

	public void setAdditionnalMessage(String additionnalMessage) {
		this.additionnalMessage = additionnalMessage;
	}
}
