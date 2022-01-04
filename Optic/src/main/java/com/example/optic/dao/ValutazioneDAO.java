package com.example.optic.dao;

import com.example.optic.entities.Valutazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ValutazioneDAO {
    private static PlayerDAO daoP;
    //private static AdminDAO daoA;
    //private static RefereeDAO daoR;

    public ValutazioneDAO(PlayerDAO daoP){
        this.daoP = daoP;
    }
    /*
    public ValutazioneDAO(AdminDAO daoA){
        this.daoA = daoA;
    }
    public ValutazioneDAO(RefreeDAO daoR){
        this.daoR = daoR;
    }
    */
    public ArrayList<Valutazione> getReviewList(String user){
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        Statement stmt = null;
        try{
            stmt = this.daoP.getConnection().createStatement();
            String sql = "SELECT * FROM valutazione WHERE fk_UsernameP2 =?";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()){
                rs.first();
                do{
                    String descrizione = rs.getString("Descrizione");
                    String recensore = rs.getString("fk_UsernameP1");
                    String recensito = rs.getString("fk_UsernameP2");
                    int stelle = rs.getInt("Stelle");
                    Valutazione val = new Valutazione(descrizione,recensore,recensito,stelle);
                    list.add(val);
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
