package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
