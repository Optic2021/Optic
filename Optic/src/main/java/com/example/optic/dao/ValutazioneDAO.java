package com.example.optic.dao;

import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Valutazione;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ValutazioneDAO {
    private PlayerDAO daoP;
    private AdminDAO daoA;
    private String selectUsername = "SELECT Username from ADMIN where NomeC=?";
    private String userField = "Username";

    public ValutazioneDAO(PlayerDAO daoP){
        this.daoP = daoP;
        this.daoA = null;
    }

    public ValutazioneDAO(AdminDAO daoA){
        this.daoA = daoA;
        this.daoP = null;
    }

    public List<Valutazione> getPlayerReviewList(String user){
        ArrayList<Valutazione> list = new ArrayList<>();
        PreparedStatement prepStmt2 = null;
        String descrizione;
        String recensore;
        String recensito;
        int stelle;
        try{
            String sql = "SELECT * FROM valutazione WHERE fk_UsernameP2 =?";
            prepStmt2 = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt2.setString(1,user);
            ResultSet rs = prepStmt2.executeQuery();
            if(rs.first()){
                rs.first();
                do{
                    descrizione = rs.getString("Descrizione");
                    recensore = rs.getString("fk_UsernameP1");
                    recensito = rs.getString("fk_UsernameP2");
                    stelle = rs.getInt("Stelle");
                    Valutazione val = new Valutazione(descrizione,recensore,recensito,stelle);
                    list.add(val);
                }while(rs.next());
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (prepStmt2 != null)
                    prepStmt2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Valutazione> getAdminReviewList1(String user){
        String sql= "SELECT fk_UsernameA from valutazione join admin on fk_UsernameA=Username WHERE NomeC=?";
        String usernameA = null;
        PreparedStatement prepStmt = null;
        try {
            prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()){
                rs.first();
                usernameA = rs.getString("fk_UsernameA");
            }
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
        return getAdminReviewList(usernameA);
    }

    public List<Valutazione> getAdminReviewList(String user){
        ArrayList<Valutazione> list = new ArrayList<>();
        PreparedStatement prepStmt3 = null;
        try{
            String sql = "SELECT * FROM valutazione WHERE fk_UsernameA =?";
            if (daoA==null){
                prepStmt3 = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }else {
                prepStmt3 = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }
            prepStmt3.setString(1,user);
            ResultSet rs = prepStmt3.executeQuery();
            if(rs.first()){
                rs.first();
                do{
                    String descrizione = rs.getString("Descrizione");
                    String recensore = rs.getString("fk_UsernameP1");
                    String recensito = rs.getString("fk_UsernameA");
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
                if (prepStmt3 != null)
                    prepStmt3.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void saveReview(ValutazioneBean val,int campoPlayer) {
        PreparedStatement prepStmt = null;
        ResultSet rs2 = null;
        String sql1;
        try {
            if(campoPlayer==0) {
                String sql = selectUsername;
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepStmt.setString(1, val.getRiceve());
                rs2 = prepStmt.executeQuery();
                rs2.first();//in teoria impossibile che non trovo admin
                sql1 = "INSERT into valutazione (Descrizione,Stelle,fk_UsernameP1,fk_UsernameA) values (?,?,?,?)";
            }else{
                sql1 = "INSERT into valutazione (Descrizione,Stelle,fk_UsernameP1,fk_UsernameP2) values (?,?,?,?)";
            }
            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,val.getRecensione());
            prepStmt.setInt(2,val.getStelle());
            prepStmt.setString(3,val.getUsernameP1());
            //Campo inteso come username admin
            if(campoPlayer==0) {
                prepStmt.setString(4, rs2.getString(userField));
            }else{
                prepStmt.setString(4, val.getRiceve());
            }
            prepStmt.executeUpdate();
            if(campoPlayer == 1){
                String sql3 = "update player set Valutazione = (select avg(stelle) as val from valutazione where fk_UsernameP2 = ?) where Username = ?";
                prepStmt = this.daoP.getConnection().prepareStatement(sql3);
                prepStmt.setString(1, val.getRiceve());
                prepStmt.setString(2, val.getRiceve());
                prepStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean getValutazione(ValutazioneBean val,int campoPlayer) {
        //restituisce una valutazione dato player e campo
        boolean esiste=false;
        PreparedStatement prepStmt = null;
        try {
            //get usernameAdmin
            ResultSet rs = null;
            String sql1;
            if(campoPlayer==0) {
                String sql = selectUsername;
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepStmt.setString(1, val.getRiceve());
                rs = prepStmt.executeQuery();
                rs.first();
                sql1 = "SELECT * FROM valutazione WHERE fk_UsernameA=? AND fk_UsernameP1=?";
            }else{
                sql1 = "SELECT * FROM valutazione WHERE fk_UsernameP2=? AND fk_UsernameP1=?";
            }
            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(2,val.getUsernameP1());
            if(campoPlayer==0) {
                prepStmt.setString(1, rs.getString(userField));
            }else{
                prepStmt.setString(1, val.getRiceve());
            }
            ResultSet rs1 = prepStmt.executeQuery();
            if(rs1.first()){
                esiste=true;
            }
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
        return esiste;
    }


    public void deleteValutazione(ValutazioneBean val,int campoPlayer) {
        //restituisce una valutazione dato player e campo
        PreparedStatement prepStmt = null;
        ResultSet resSet = null;
        String sql1;
        try {
            //get usernameAdmin
            if (campoPlayer==0) {
                String sql = selectUsername;
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepStmt.setString(1, val.getRiceve());
                resSet = prepStmt.executeQuery();
                resSet.first();
                sql1 = "DELETE from Valutazione where fk_UsernameP1=? AND fk_UsernameA=?";
            }else{
                sql1 = "DELETE from Valutazione where fk_UsernameP1=? AND fk_UsernameP2=?";
            }
            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (campoPlayer==0) {
                prepStmt.setString(2, resSet.getString(userField));
            }else{
                prepStmt.setString(2, val.getRiceve());
            }
            prepStmt.setString(1, val.getUsernameP1());
            prepStmt.executeUpdate();

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
    }
}
