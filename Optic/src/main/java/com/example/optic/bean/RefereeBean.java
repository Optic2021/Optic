package com.example.optic.bean;

public class RefereeBean implements UserBean {
    private String username;
    private String password;
    private String fkUsernameA1;

    public RefereeBean(){
        //costruttore vuoto
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getVia() {
        return null;
    }

    @Override
    public void setVia(String via) {
        //Does np only needed to support Factory
    }

    @Override
    public String getNomeC() {
        return null;
    }

    @Override
    public void setNomeC(String nomeC) {
        //Does np only needed to support Factory
    }

    @Override
    public String getProv() {
        return null;
    }

    @Override
    public void setProv(String prov) {
        //Does np only needed to support Factory
    }

    public String getFkUsernameA1() {
        return fkUsernameA1;
    }

    public void setFkUsernameA1(String fkUsernameA1) {
        this.fkUsernameA1 = fkUsernameA1;
    }
}

