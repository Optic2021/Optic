package com.example.optic.dao;

import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Player;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class PlayerDAO {
    //i parametri dovrebbero essere presi dal file properties
    /*
    private static String USER = "root";
    private static String PW = "17moneC*";
    private static String DB_URL = "jdbc:mysql://localhost:3306/optic";
    private static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    */

    private static String USER;
    private static String PW;
    private static String DB_URL;
    private static String DRIVER_CLASS_NAME;

    private static PlayerDAO instance = null;
    private Connection conn;


    protected PlayerDAO() throws IOException {
        this.conn = null;
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("prop.properties");
            Properties prop = new Properties();
            prop.load(input);
            this.USER = prop.getProperty("USER");
            this.PW = prop.getProperty("PW");
            this.DB_URL = prop.getProperty("DB_URL");
            this.DRIVER_CLASS_NAME = prop.getProperty("DRIVER_CLASS_NAME");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void newPlayer(String user, String password) throws Exception {
        Statement stmt = null;
        int ret;
        Player p = new Player(user,password);
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "INSERT INTO player VALUES (?,?,?,?,?,?,?)";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,p.getUsername());
            prepStmt.setString(2,p.getPassword());
            prepStmt.setString(3,"");
            prepStmt.setString(4,"0");
            prepStmt.setString(5,"");
            prepStmt.setString(6,"");
            prepStmt.setString(7,p.getStato());
            prepStmt.executeUpdate();
            /*
            if(ret != 0){
                Exception e = new Exception("Player "+user+" not inserted");
                throw e;
            }*/
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (instance.conn != null)
                    instance.conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void setPlayerInfo(String user, String desc, String fb, String ig){
        Statement stmt = null;
        Player p = new Player(user,"");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "UPDATE player SET Descrizione=?, Instagram=?, Facebook=? WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,desc);
            prepStmt.setString(2,fb);
            prepStmt.setString(3,ig);
            prepStmt.setString(4,user);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (instance.conn != null)
                    instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Player getPlayer(String user) throws Exception {
        Statement stmt = null;
        Player p = new Player(user,"");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM player WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
                p.setStato(rs.getString("Stato"));

                //chiudo result set
                rs.close();
            }
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (instance.conn != null)
                    instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    public boolean isPlayerNameUsed(String username) throws Exception {
        boolean res = false;
        Statement stmt = null;
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT Username FROM player WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,username);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                res = true;
            }
            rs.close();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (instance.conn != null)
                    instance.conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return res;
    }

    public static PlayerDAO getInstance() throws IOException {
        if(PlayerDAO.instance == null){
            PlayerDAO.instance = new PlayerDAO();
        }
        return instance;
    }

    public void getConn(){
        try{
            Class.forName(DRIVER_CLASS_NAME);
            instance.conn = DriverManager.getConnection(DB_URL, USER, PW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConn(){
        try{
            if (instance.conn != null)
                instance.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
