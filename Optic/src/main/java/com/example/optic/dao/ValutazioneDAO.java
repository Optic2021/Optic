package com.example.optic.dao;

import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Valutazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ValutazioneDAO {
    private static PlayerDAO daoP;
    private static AdminDAO daoA;
    //private static RefereeDAO daoR;

    public ValutazioneDAO(PlayerDAO daoP){
        this.daoP = daoP;
    }

    public ValutazioneDAO(AdminDAO daoA){
        this.daoA = daoA;
    }
    /*
    public ValutazioneDAO(RefreeDAO daoR){
        this.daoR = daoR;
    }
    */


    public ArrayList<Valutazione> getPlayerReviewList(String user){
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        Statement stmt = null;
        String descrizione;
        String recensore;
        String recensito;
        int stelle;
        try{
            stmt = this.daoP.getConnection().createStatement();
            String sql = "SELECT * FROM valutazione WHERE fk_UsernameP2 =?";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
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
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Valutazione> getAdminReviewList1(String user){
        String sql= "SELECT fk_UsernameA from valutazione join admin on fk_UsernameA=Username";
        String usernameA = null;
        try {
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = prepStmt.executeQuery();
            rs.first();
            usernameA = rs.getString("fk_UsernameA");
            System.out.println("Username admin: "+usernameA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getAdminReviewList(usernameA);
    }

    public ArrayList<Valutazione> getAdminReviewList(String user){
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        Statement stmt = null;
        try{
            String sql = "SELECT * FROM valutazione WHERE fk_UsernameA =?";
            PreparedStatement prepStmt=null;
            if (daoA==null){
                stmt = this.daoP.getConnection().createStatement();
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }else {
                stmt = this.daoA.getConnection().createStatement();
                prepStmt = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
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
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void saveReview(ValutazioneBean val){
        Statement stmt;

        try {
            stmt = this.daoP.getConnection().createStatement();
            String sql = "SELECT Username from ADMIN where NomeC=?";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,val.getCampo());
            ResultSet rs=prepStmt.executeQuery();
            rs.first();
            //in teoria impossibile che non trovo admin

            stmt = this.daoP.getConnection().createStatement();
            String sql1 = "INSERT into valutazione (Descrizione,Stelle,fk_UsernameP1,fk_UsernameA) values (?,?,?,?)";
            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            prepStmt.setString(1,val.getRecensione());
            prepStmt.setInt(2,val.getStelle());
            prepStmt.setString(3,val.getUsernameP1());
            //Campo inteso come username admin
            prepStmt.setString(4,rs.getString("Username"));

            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getValutazione(ValutazioneBean val) {
        //restituisce una valutazione dato player e campo
        boolean esiste=false;
        Statement stmt;
        try {
            //get usernameAdmin
            stmt = this.daoP.getConnection().createStatement();
            String sql = "SELECT Username from ADMIN where NomeC=?";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,val.getCampo());
            ResultSet rs=prepStmt.executeQuery();
            rs.first();

            stmt = this.daoP.getConnection().createStatement();
            String sql1 = "SELECT * FROM valutazione WHERE fk_UsernameA=? AND fk_UsernameP1=?";
            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(2,val.getUsernameP1());
            prepStmt.setString(1,rs.getString("Username"));
            ResultSet rs1 = prepStmt.executeQuery();
            if(rs1.first()){
                esiste=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esiste;
    }

    public void deleteValutazione(ValutazioneBean val) {
        //restituisce una valutazione dato player e campo
        boolean esiste = false;
        Statement stmt;
        try {
            //get usernameAdmin
            stmt = this.daoP.getConnection().createStatement();
            String sql = "SELECT Username from ADMIN where NomeC=?";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1, val.getCampo());
            ResultSet rs = prepStmt.executeQuery();
            rs.first();
            System.out.println("Username admin : "+rs.getString("Username"));

            String sql1 = "DELETE from Valutazione where fk_UsernameP1=? AND fk_UsernameA=?";
            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(2, rs.getString("Username"));
            prepStmt.setString(1, val.getUsernameP1());
            prepStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


