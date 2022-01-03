package com.example.optic.dao;

import com.example.optic.entities.Referee;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class RefereeDAO {
    private static String USER;
    private static String PW;
    private static String DB_URL;
    private static String DRIVER_CLASS_NAME;

    private static RefereeDAO instance = null;
    private Connection conn;

    protected RefereeDAO() throws IOException {
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

    public void newReferee(String username,String password) throws Exception {
        Statement stmt = null;
        int ret;
        Referee r = new Referee(username, password);
        try {
            if (instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "INSERT INTO referee VALUES(?,?,?)";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, r.getUsername());
            prepStmt.setString(2, r.getPassword());
            prepStmt.setString(3, "");
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

    //Possiamo modificare effettivamente un username?
    /*public void setReferee(String user,String pass){
        Statement stmt = null;
        Referee ref = new Referee(user);
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
    }*/

    //chiede l aggiunta di una catch clause
    public Referee getReferee(String user){
        Statement stmt = null;
        Referee ref = new Referee(user);
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM referee WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                Exception e = new Exception("Referee "+user+" not found");
                throw e;
            }
            //il player deve presente in quanto la label da dove prendo il nome non può essere vuota
            rs.first();
            ref.setUsername(rs.getString("Username"));
            ref.setPassword(rs.getString("Password")); //probabilmente non servirà

            //chiudo result set
            rs.close();
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
        return ref;
    }

    public static RefereeDAO getInstance() throws IOException {
        if(RefereeDAO.instance == null){
            RefereeDAO.instance = new RefereeDAO();
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
