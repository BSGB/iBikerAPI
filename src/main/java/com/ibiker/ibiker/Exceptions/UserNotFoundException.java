package com.ibiker.ibiker.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("No user corresponding to this email.");
    }
}
