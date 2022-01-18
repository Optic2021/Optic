package com.example.optic.bean;

public class RefereeBean {
    private String username;
    private String password;
    private String fkUsernameA1;

    public RefereeBean(){
        //costruttore vuoto
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

    public String getFkUsernameA1() {
        return fkUsernameA1;
    }

    public void setFkUsernameA1(String fkUsernameA1) {
        this.fkUsernameA1 = fkUsernameA1;
    }
}

