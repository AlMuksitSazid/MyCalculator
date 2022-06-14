package com.mycalculator;

public class UserModel {
    String fullname, email, password, gender;

    public UserModel(String fullname, String email, String password, String gender) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserModel() {
    }
}
