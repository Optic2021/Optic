package com.example.optic.entities;

import java.sql.Blob;

public class Foto {

    private int idFoto;
    private Blob immagine;
    private String fkUsername;

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public Blob getImmagine() {
        return immagine;
    }

    public void setImmagine(Blob immagine) {
        this.immagine = immagine;
    }

    public String getFkUsername() {
        return fkUsername;
    }

    public void setFkUsername(String fkUsername) {
        this.fkUsername = fkUsername;
    }
}
