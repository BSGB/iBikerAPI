package com.ibiker.ibiker.Models;

import javax.validation.constraints.NotNull;

public class AuthData {

    @NotNull(message = "User Email cannot be null.")
    private String userEmail;

    @NotNull(message = "User Password cannot be null.")
    private String userPassword;

    public AuthData() {
    }

    public AuthData(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
