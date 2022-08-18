package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class InsufficientAmountException extends RuntimeException {
    public InsufficientAmountException(String message) {
        super(message);
    }
}
