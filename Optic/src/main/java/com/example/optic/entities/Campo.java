package com.example.optic.entities;

public class Campo {
    private String nomeC;
    private String provincia;

    public Campo(String nomeC,String provincia){
        this.nomeC=nomeC;
        this.provincia=provincia;
    }

    public String getNomec() {
        return nomeC;
    }

    public void setNomec(String nomec) {
        nomeC = nomec;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
