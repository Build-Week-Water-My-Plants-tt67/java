package com.lambdaschool.java.exceptions;

/**
 * A custom exception to be used when a resource is not but is suppose to be
 */
public class ResourceNotFoundException
        extends RuntimeException
{
    public ResourceNotFoundException(String message)
    {
        super("Java " + message);
    }
}