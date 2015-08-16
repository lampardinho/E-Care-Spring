package com.tsystems.javaschool.ecare.rest;

import java.io.Serializable;

/**
 * Created by Kolia on 15.08.2015.
 */
public class UserInfo implements Serializable
{
    private String name;
    private String surname;
    private String address;
    private String email;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }


    public UserInfo(String name, String surname, String address, String email)
    {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
    }
}

