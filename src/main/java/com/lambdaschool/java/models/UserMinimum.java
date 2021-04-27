package com.lambdaschool.java.models;

import javax.validation.constraints.*;

/**
 * A model used to create a new user. The minimum information needed to create a user.
 * Note the role will default to USER.
 */
public class UserMinimum
{
    /**
     * The username (String)
     */
    private String username;

    /**
     * The user's password (String)
     */
    private String password;

    /**
     * The user's phoneNumber (String)
     */
    @Size(min = 10, max = 10, message = "phone number must be 10 digits")
    @Digits(fraction = 0, integer = 10, message = "phone number only contain numbers between 0-9")
    private String phoneNumber;

    /**
     * Getter for the username
     *
     * @return the username (String) associated with this user
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Setter for the username
     *
     * @param username the new username (String) associated with this user
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Getter for the password of this user
     *
     * @return the password (String) for this user
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Setter for the password of this user. This object is a temporary model used to create a new user.
     * The password must remain in clear text until saved into the database.
     *
     * @param password the new password (String in clear texts) for this user
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
    }
}
