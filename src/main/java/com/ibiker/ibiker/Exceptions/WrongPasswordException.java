package com.ibiker.ibiker.Exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(){
        super("Password is incorrect.");
    }
}
