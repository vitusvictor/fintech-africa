package com.decagon.fintechpaymentapisqd11b.customExceptions;

public class InvalidPinException extends RuntimeException{
    public InvalidPinException(String message){
        super(message);
    }
}
