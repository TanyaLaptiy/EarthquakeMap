package com.lineate.buscompany.errors;

public class ServerException extends Exception {
    private ErrorCodeApplication errorCode;

    public ServerException(ErrorCodeApplication errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCodeApplication getErrorCode() {
        return errorCode;
    }
}
