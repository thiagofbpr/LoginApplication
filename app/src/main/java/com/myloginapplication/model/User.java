package com.myloginapplication.model;

import androidx.annotation.NonNull;

public class User implements Comparable<User> {

    private int _id;
    private String _fullName;
    private String _userName;
    private String _password;

    public User() {
        this._fullName = "";
        this._userName = "";
        this._password = "";
    }

    public User(String fullName, String userName, String password) {
        this._fullName = fullName;
        this._userName = userName;
        this._password = password;
    }

    public int get_id() {
        return _id;
    }

    public String get_fullName() {
        return _fullName;
    }

    public String get_userName() {
        return _userName;
    }

    public String get_password() {
        return _password;
    }

    public void set_fullName(String _fullName) {
        this._fullName = _fullName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    @Override
    public int compareTo(User user) {
        return this.get_userName().compareTo(user.get_userName());
    }

    @NonNull
    @Override
    public String toString() {
        return "User {full name = " + _fullName + ", username = " + _userName + "}";
    }


}
