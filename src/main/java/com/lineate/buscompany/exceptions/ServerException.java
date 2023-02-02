package com.lineate.buscompany.exceptions;

public class ServerException extends Exception {
	private ErrorCode errorCode;

	public ServerException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
