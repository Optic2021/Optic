package com.example.optic.bean;

public class PlayerBean implements java.io.Serializable{
    private String username;
    private String password;

    public void PlayerBean(){}

    //metodi set e get
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
