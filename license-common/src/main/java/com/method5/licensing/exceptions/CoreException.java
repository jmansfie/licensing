package com.method5.licensing.exceptions;

public class CoreException extends Exception {
    public CoreException(String message)
    {
        super(message);
    }

    public CoreException(String message, Exception e)
    {
        super(message, e);
    }
}
