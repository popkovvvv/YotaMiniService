package com.billing.yota.exception;

public class TransactionException extends Exception{
    private static final long serialVersionUID = -3128681006635769411L;

    public TransactionException(String message) {
        super(message);
    }
}
