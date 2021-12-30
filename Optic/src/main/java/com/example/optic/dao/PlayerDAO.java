package com.example.optic.dao;

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

    public boolean authentication(String user, String pw) throws Exception {
        boolean res = false;
        String verPass;
        Statement stmt = null;

        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT Password FROM player WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                verPass = rs.getString("Password");
                if (pw.equals(verPass)) {
                    res = true;
                }
            }
            rs.close();
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
        return res;
    }
}
