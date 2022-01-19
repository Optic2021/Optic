package com.example.optic.dao;

import com.example.optic.bean.ReportBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    //utilizzo pattern singleton per rendere unica la connessione
    private static PlayerDAO instance = null;
    private Connection conn;

    protected PlayerDAO(){
        this.conn = null;
    }

    public void newPlayer(String usern, String password) throws SQLException {
        PreparedStatement prepStmt = null;
        Player p = new Player(usern,password);
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "INSERT INTO player VALUES (?,?,?,?,?,?)";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,p.getUsername());
            prepStmt.setString(2,p.getPassword());
            prepStmt.setString(3,"");
            prepStmt.setString(4,"0");
            prepStmt.setString(5,"");
            prepStmt.setString(6,"");
            prepStmt.executeUpdate();
        } finally {
            try {
                if (prepStmt != null){
                    prepStmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlayerInfo(String user, String desc, String fb, String ig){
        PreparedStatement prepStmt = null;
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "UPDATE player SET Descrizione=?, Instagram=?, Facebook=? WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,desc);
            prepStmt.setString(2,ig);
            prepStmt.setString(3,fb);
            prepStmt.setString(4,user);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Player getPlayer(String user) throws SQLException {
        PreparedStatement prepStmt = null;
        Player p = new Player(user);
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "SELECT * FROM player WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                p = null;
            }else {
                rs.first();
                p.setUsername(rs.getString("Username"));
                p.setPassword(rs.getString("Password"));
                p.setDescrizione(rs.getString("Descrizione"));
                p.setValutazione(rs.getInt("Valutazione"));
                p.setIg(rs.getString("Instagram"));
                p.setFb(rs.getString("Facebook"));

                //chiudo result set
                rs.close();
            }
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    public static PlayerDAO getInstance() throws IOException {
        if(instance == null){
            instance = new PlayerDAO();
        }
        return instance;
    }

    //ritorno l'ogetto Connection
    public Connection getConnection(){
        return this.conn;
    }

    //apro la connessione
    public void getConn(){
        ImportDAO imp= new ImportDAO();
        try{
            Class.forName(imp.getDriverClassName());
            instance.conn = DriverManager.getConnection(imp.getDbUrl(), imp.getUser(), imp.getPassWord());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConn(){
        try{
            if (instance.conn != null) {
                instance.conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ReportBean> getPlayerReportList(String user) {
        ArrayList<ReportBean> list = new ArrayList<>();
        PreparedStatement prepStmt = null;
        try{
            String sql = "SELECT fk_UsernameR,Motivazione FROM report WHERE fk_UsernameP=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()){
                ReportBean e = new ReportBean();
                rs.first();
                do{
                    e.setReferee(rs.getString("fk_UsernameR"));
                    e.setMotivazione(rs.getString("Motivazione"));
                    list.add(e);
                }while(rs.next());
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
        return list;
    }

    public List<Admin> getCampoList()throws SQLException {
        ArrayList<Admin> list= new ArrayList<>();
        PreparedStatement prepStmt = null;
        Admin admin = null;
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "SELECT NomeC, Provincia FROM admin";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.first()){ // rs empty
                rs.first();
                do {
                    admin = new Admin();
                    admin.setNomeC((rs.getString("NomeC")));
                    admin.setProvincia((rs.getString("Provincia")));
                    list.add(admin);

                } while (rs.next());
                rs.close();
            }
        }finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return list;
    }

}
