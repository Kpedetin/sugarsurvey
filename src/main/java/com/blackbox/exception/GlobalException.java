package com.blackbox.exception;


public class GlobalException extends RuntimeException{

	private Enum<?> errorCode;
	private Object [] msgArgs;


	public Enum<?> getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Enum<?> errorCode) {
		this.errorCode = errorCode;
	}

	public Object[] getMsgArgs() {
		return msgArgs;
	}

	public void setMsgArgs(Object[] msgArgs) {
		this.msgArgs = msgArgs;
	}

	public GlobalException(Enum <?> errorCode, Object ... args){
		this.errorCode=errorCode;
		this.msgArgs=args;
	}

}
