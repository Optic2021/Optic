package com.example.optic.bean;

public class RefereeBean implements UserBean {
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

    @Override
    public String getVia() {
        return null;
    }

    @Override
    public void setVia(String via) {

    }

    @Override
    public String getNomeC() {
        return null;
    }

    @Override
    public void setNomeC(String nomeC) {

    }

    @Override
    public String getProv() {
        return null;
    }

    @Override
    public void setProv(String prov) {
    }

    public String getFkUsernameA1() {
        return fkUsernameA1;
    }

    public void setFkUsernameA1(String fkUsernameA1) {
        this.fkUsernameA1 = fkUsernameA1;
    }
}

