package com.lineate.buscompany.errors;

public enum ErrorCodeApplication {
    WRONG_FIRSTNAME("Firstname is wrong"),
    WRONG_LASTNAME("Lastname is wrong"),
    WRONG_LOGIN("Login is wront"),
    WRONG_PASSWORD("Wrong password"),
    ERROR_INSETR("Wrong insert"),
    ERROR_GET("Can't get"),
    ERROR_UPDATE("Can't update"),
    ERROR_DELETE("Can't delete"),
    INTERNAL_ERROR("intermal error"),
    YOU_ARE_NOT_CLIENT("You don't have rights to this"),
    YOU_ARE_NOT_ADMIN("You don't have rights to this"),


    WRONG_TOKEN("Wrong token");
    String errorString;

    ErrorCodeApplication(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
