package com.example.optic.dao;

import com.example.optic.entities.Event;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private AdminDAO daoA;
    private RefereeDAO daoR;
    private PlayerDAO daoP;

    public EventDAO(AdminDAO daoA){
        this.daoA = daoA;
        this.daoR = null;
        this.daoP = null;
    }
    public EventDAO(RefereeDAO daoR){
        this.daoR = daoR;
        this.daoP = null;
        this.daoA = null;
    }

    public EventDAO(PlayerDAO dao) {
        this.daoP = dao;
        this.daoR = null;
        this.daoA = null;
    }

    //Assolutamente da fare, parametrizzare i tre sottostanti metodi in un solo metodo

    public List<Event> getEventList() throws SQLException {
        ArrayList<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM evento";
        PreparedStatement prepStmt = null;
        String evento;
        String desc;
        int numGiocatori;
        try{
            if (daoP!=null){
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }else if(daoA != null){
                prepStmt = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            } else if(daoR != null){
                prepStmt = this.daoR.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()){
                rs.first();
                do{
                    evento = rs.getString("Nome");
                    desc = rs.getString("Descrizione");
                    numGiocatori = rs.getInt("GiocatoriCons");
                    Event e = new Event(evento,desc,numGiocatori);
                    list.add(e);
                }while(rs.next());
            }
            rs.close();
        } finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return list;
    }
}
