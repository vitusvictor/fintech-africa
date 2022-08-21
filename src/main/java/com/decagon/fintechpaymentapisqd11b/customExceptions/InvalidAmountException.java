package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class InvalidAmountException extends RuntimeException{

    public InvalidAmountException(String message) {
        super(message);
    }
}
