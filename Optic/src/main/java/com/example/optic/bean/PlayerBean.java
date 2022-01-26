package com.example.optic.bean;

public class PlayerBean implements java.io.Serializable{
    private String username;
    private String password;
    private String descrizione;
    private int valutazione = 0;
    private String ig;
    private String fb;
    private String stato = "nullo";

    //metodi set e get
    public String getBUsername() {
        return username;
    }

    public void setBUsername(String username) {
        this.username = username;
    }

    public String getBPassword() {
        return password;
    }

    public void setBPassword(String password) {
        this.password = password;
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
