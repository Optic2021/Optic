package com.example.optic.entities;

public class Report {
    private int idReport;
    private String motivazione;
    private String fkUsernameR;
    private String fkUsernameP;

    public int getIdReport() {
        return idReport;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public String getFkUsernameP() {
        return fkUsernameP;
    }

    public String getFkUsernameR() {
        return fkUsernameR;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public void setFkUsernameR(String fkUsernameR) {
        this.fkUsernameR = fkUsernameR;
    }

    public void setFkUsernameP(String fkUsernameP) {
        this.fkUsernameP = fkUsernameP;
    }

}
