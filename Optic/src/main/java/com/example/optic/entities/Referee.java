package com.example.optic.entities;

public class Referee {
    private String username;
    private String password;
    private String fk_Username;

    public Referee(String username, String password){
        this.username = username;
        this.password = password;
        this.fk_Username="";
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

    public String getFk_Username() {
        return fk_Username;
    }

    public void setFk_Username(String fk_Username) {
        this.fk_Username = fk_Username;
    }
}
