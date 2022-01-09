package com.example.optic.dao;

import com.example.optic.entities.Event;
import com.example.optic.entities.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class EventDAO {
    private static AdminDAO daoA;
    //private static RefereeDAO daoR;

    public EventDAO(AdminDAO daoA){
        this.daoA = daoA;
    }

    public ArrayList<Event> getEventList(){
        ArrayList<Event> list = new ArrayList<Event>();
        Statement stmt = null;
        String evento;
        String desc;
        int numGiocatori;
        try{
            stmt = this.daoA.getConnection().createStatement();
            String sql = "SELECT * FROM evento";
            PreparedStatement prepStmt = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
