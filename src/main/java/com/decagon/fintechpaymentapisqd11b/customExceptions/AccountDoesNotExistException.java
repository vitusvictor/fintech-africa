package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class AccountDoesNotExistException extends RuntimeException{

    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
