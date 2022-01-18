package com.example.optic.entities;

public class Valutazione {
    private int idValutazione;
    private String descrizione;
    private int stelle;
    private String fkUsernameP1;
    private String fkUsernameP2;
    private String fkUsernameA;

    //costruttore per recensione da utente a utente
    public Valutazione(String descrizione, String recensore, String recensito, int stelle){
        this.descrizione = descrizione;
        this.fkUsernameP1 = recensore;
        this.fkUsernameP2 = recensito;
        this.stelle = stelle;
    }

    public int getIdValutazione() {
        return idValutazione;
    }

    public void setIdValutazione(int idValutazione) {
        this.idValutazione = idValutazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getStelle() {
        return stelle;
    }

    public void setStelle(int stelle) {
        this.stelle = stelle;
    }

    public String getFkUsernameP1() {
        return fkUsernameP1;
    }

    public void setFkUsernameP1(String fkUsernameP1) {
        this.fkUsernameP1 = fkUsernameP1;
    }

    public String getFkUsernameP2() {
        return fkUsernameP2;
    }

    public void setFkUsernameP2(String fkUsernameP2) {
        this.fkUsernameP2 = fkUsernameP2;
    }

    public String getFkUsernameA() {
        return fkUsernameA;
    }

    public void setFkUsernameA(String fkUsernameA) {
        this.fkUsernameA = fkUsernameA;
    }

    public String getFormattedText(){
        return this.fkUsernameP1+" :\n"+descrizione;
    }
}
