package com.lineate.buscompany.exceptions;

public enum ErrorCode {
	WRONG_PROPERTIES_KEY("Properties is wrong");

	String errorString;

	ErrorCode(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString(String errorString) {
		return errorString;
	}

}
