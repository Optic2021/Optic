package com.example.optic.bean;

public class PlayerBean implements UserBean{
    private String username;
    private String password;
    private String descrizione;
    private int valutazione = 0;
    private String ig;
    private String fb;

    //metodi set e get
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

    public String getBDescrizione() {
        return descrizione;
    }

    public void setBDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public String getBIg() {
        return ig;
    }

    public void setBIg(String ig) {
        this.ig = ig;
    }

    public String getBFb() {
        return fb;
    }

    public void setBFb(String fb) {
        this.fb = fb;
    }
}
