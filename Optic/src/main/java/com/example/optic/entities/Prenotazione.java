package com.example.optic.entities;

public class Prenotazione {
    private String fkUsername;
    private String fkIdGiornata;

    public String getFkUsername() {
        return fkUsername;
    }

    public void setFkUsername(String fkUsername) {
        this.fkUsername = fkUsername;
    }

    public String getFkIdGiornata() {
        return fkIdGiornata;
    }

    public void setFkIdGiornata(String fkIdGiornata) {
        this.fkIdGiornata = fkIdGiornata;
    }
}
