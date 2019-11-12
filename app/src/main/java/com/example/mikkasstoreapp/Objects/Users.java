package com.example.mikkasstoreapp.Objects;

public class Users {
    private String lastname, firstname, username, password;

    public Users() {
    }

    public Users(String lastname, String firstname, String username, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
