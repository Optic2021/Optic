package com.example.optic.entities;

public class Valutazione {
    private int idValutazione;
    private String Descrizione;
    private int Stelle;
    private String fk_UsernameP1;
    private String fk_UsernameP2;
    private String fk_UsernameA;

    public Valutazione(){

    }

    public int getIdValutazione() {
        return idValutazione;
    }

    public void setIdValutazione(int idValutazione) {
        this.idValutazione = idValutazione;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public int getStelle() {
        return Stelle;
    }

    public void setStelle(int stelle) {
        Stelle = stelle;
    }

    public String getFk_UsernameP1() {
        return fk_UsernameP1;
    }

    public void setFk_UsernameP1(String fk_UsernameP1) {
        this.fk_UsernameP1 = fk_UsernameP1;
    }

    public String getFk_UsernameP2() {
        return fk_UsernameP2;
    }

    public void setFk_UsernameP2(String fk_UsernameP2) {
        this.fk_UsernameP2 = fk_UsernameP2;
    }

    public String getFk_UsernameA() {
        return fk_UsernameA;
    }

    public void setFk_UsernameA(String fk_UsernameA) {
        this.fk_UsernameA = fk_UsernameA;
    }
}
