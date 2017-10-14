package com.example.divyankitharaghavaurs.reminderapp;

/**
 * Created by divyankithaRaghavaUrs on 4/20/17.
 */

public class LoginInfo
{
    String fname,lname,email,uname,password, passcode,username, empty = "not";

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
