package com.example.optic.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrenotazioneDAO {
    private PlayerDAO daoP;

    public PrenotazioneDAO(PlayerDAO daoP){
        this.daoP = daoP;
    }

    public boolean isPlayerBooked(String player, int idPlay){
        boolean res = true;
        PreparedStatement prepStmt = null;
        try{
            String sql = "SELECT * FROM prenotazione WHERE fk_Username =? AND fk_idGiornata=?";
            prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,player);
            prepStmt.setInt(2,idPlay);
            ResultSet rs = prepStmt.executeQuery();
            if(!(rs.first())){
                res = false;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public void bookPlay(String player, int idPlay){
        PreparedStatement prepStmt = null;
        try{
            String sql = "INSERT INTO prenotazione VALUES(?,?)";
            prepStmt = this.daoP.getConnection().prepareStatement(sql);
            prepStmt.setString(1,player);
            prepStmt.setInt(2,idPlay);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (prepStmt!= null)
                    prepStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
