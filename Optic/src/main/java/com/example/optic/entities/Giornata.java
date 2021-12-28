package com.example.optic.entities;

import java.util.Date;

public class Giornata {
    private int idGiornata;
    private Date data;
    private int num_Giocatori ;
    private String fk_Nome;
    private String fk_Username;

    public void setIdGiornata(int newIdGiornata){
        this.idGiornata=newIdGiornata;
    }
    public void setData(Date newData){
        this.data=newData;
    }
    public void setNum_Giocatori(int newNum_Giocatori){
        this.num_Giocatori=newNum_Giocatori;
    }
    public void setFk_Nome(String newFk_Nome){
        this.fk_Nome=newFk_Nome;
    }
    public void setFk_Username(String newFk_Username){
        this.fk_Username=newFk_Username;
    }

    /**************************/

    public int getIdGiornata(){
        return this.idGiornata;
    }

    public Date getData(){
        return this.data;
    }

    public int getNum_Giocatori(){
        return this.num_Giocatori;
    }

    public String getFk_Nome(){
        return this.fk_Nome;
    }

    public String getFk_Username(){
        return this.fk_Username;
    }
}
