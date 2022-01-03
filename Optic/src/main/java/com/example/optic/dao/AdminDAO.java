package com.example.optic.dao;

import com.example.optic.entities.Admin;
import com.example.optic.entities.Player;
import com.example.optic.entities.Referee;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class AdminDAO {
    private static String USER;
    private static String PW;
    private static String DB_URL;
    private static String DRIVER_CLASS_NAME;

    private static AdminDAO instance = null;
    private Connection conn;

    //costruttore
    protected AdminDAO() throws IOException{
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

    public static void newAdmin(String username,String password) throws Exception {
        Statement stmt = null;
        int ret;
        Admin admin = new Admin(username, password);
        try {
            if (instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "INSERT INTO Admin VALUES(?,?,?,?,?,?,?)";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, admin.getUsername());
            prepStmt.setString(2, admin.getPassword());
            prepStmt.setString(3, admin.getIg());
            prepStmt.setString(4, admin.getFb());
            prepStmt.setString(5, admin.getWa());
            prepStmt.setString(6, admin.getDescrizioneC());
            prepStmt.setString(7, admin.getNomeC());
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

    public static void setAdmin1(String user,String instagram,String facebook,String whatsapp,String DescrizioneC,String NomeC) {
        //qui possiamo dividere i due tipi di update
    }

    public static void setInfoPg(String user,String instagram,String facebook,String whatsapp,String DescrizioneC,String NomeC) throws SQLException {
        Statement stmt = null;
        int ret;
        Admin a= new Admin(user,"");
        try {
            if (instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "UPDATE referee SET Instagram=?, Facebook=?, Whatsapp=?,DescrizioneC=?, NomeC=? WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(6, a.getUsername());
            prepStmt.setString(1, a.getIg());
            prepStmt.setString(2, a.getFb());
            prepStmt.setString(3, a.getWa());
            prepStmt.setString(4, a.getDescrizioneC());
            prepStmt.setString(5, a.getNomeC());

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

    public static Admin getAdmin(String user) throws Exception {
        Statement stmt = null;
        Admin admin = new Admin(user,"");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM Admin WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,admin.getUsername());
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                admin = null;
            }else {
                rs.first();
                admin.setUsername(rs.getString("Username"));
                admin.setPassword(rs.getString("Password"));
                admin.setIg(rs.getString("Instagram"));
                admin.setFb(rs.getString("Facebook"));
                admin.setWa(rs.getString("Whatsapp"));
                admin.setDescrizioneC(rs.getString("DescrizioneC"));
                admin.setNomeC(rs.getString("NomeC"));

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
        return admin;
    }

    public static AdminDAO getInstance() throws IOException {
        if(AdminDAO.instance == null){
            AdminDAO.instance = new AdminDAO();
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