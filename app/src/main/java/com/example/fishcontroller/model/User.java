package com.example.fishcontroller.model;

public class User {

    private String username;
    private String email;
    private String password;
    private String number_telephone;

    public User(String username, String email, String password, String number_telephone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.number_telephone = number_telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber_telephone() {
        return number_telephone;
    }

    public void setNumber_telephone(String number_telephone) {
        this.number_telephone = number_telephone;
    }
}
