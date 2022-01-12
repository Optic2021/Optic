package com.example.optic.entities;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Giornata {
    private int idGiornata;
    private Calendar data;
    private int num_Giocatori ;
    private String fk_Nome;
    private String fk_Username;

    public Giornata(int idGiornata,Calendar data,int num_Giocatori,String evento){
        this.idGiornata = idGiornata;
        this.data = data;
        this.num_Giocatori = num_Giocatori;
        this.fk_Nome = evento;
    }
    public void setIdGiornata(int newIdGiornata){
        this.idGiornata=newIdGiornata;
    }
    public void setData(Calendar newData){
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

    public Calendar getData(){
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
