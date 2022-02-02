package com.example.optic.entities;

import com.example.optic.app_controllers.ReviewAppController;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Giornata {
    private int idGiornata;
    private Calendar data;
    private int numGiocatori ;
    private String fkNome;
    private String fkUsername;
    private String nomeC;
    private String dataString;

    public Giornata(Calendar data, String nomeC){
        this.data = data;
        this.nomeC = nomeC;
        SimpleDateFormat dataGiornata = new SimpleDateFormat("yyyy-MM-dd");
        this.dataString = dataGiornata.format(this.data.getTime());
    }
    public Giornata(int idGiornata,Calendar data,int numGiocatori,String evento){
        this.idGiornata = idGiornata;
        this.data = data;
        this.numGiocatori = numGiocatori;
        this.fkNome = evento;
        SimpleDateFormat dataGiornata = new SimpleDateFormat("yyyy-MM-dd");
        this.dataString = dataGiornata.format(this.data.getTime());
    }
    public void setIdGiornata(int newIdGiornata){
        this.idGiornata=newIdGiornata;
    }
    public void setData(Calendar newData){
        this.data=newData;
    }
    public void setNumGiocatori(int newNumGiocatori){
        this.numGiocatori=newNumGiocatori;
    }
    public void setFkNome(String newFkNome){
        this.fkNome=newFkNome;
    }
    public void setFkUsername(String newFkUsername){
        this.fkUsername=newFkUsername;
    }
    public void setNomeC(String nomeC) {
        this.nomeC = nomeC;
    }

    /**************************/

    public int getIdGiornata(){
        return this.idGiornata;
    }

    public Calendar getData(){
        return this.data;
    }

    public int getNumGiocatori(){
        return this.numGiocatori;
    }

    public String getFkNome(){
        return this.fkNome;
    }

    public String getFkUsername(){
        return this.fkUsername;
    }

    public String getNomeC() {
        return this.nomeC;
    }
    public String getDataString(){
        return this.dataString;
    }
}
