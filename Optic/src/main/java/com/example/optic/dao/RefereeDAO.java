package com.example.optic.dao;

import com.example.optic.bean.ReportBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Referee;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class RefereeDAO {
    private String user;
    private String passWord;
    private String dbUrl;
    private String driverClassName;

    private static RefereeDAO instance = null;
    private Connection conn;

    protected RefereeDAO() {
        this.conn = null;
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("prop.properties");
            Properties prop = new Properties();
            prop.load(input);
            this.user = prop.getProperty("USER");
            this.passWord = prop.getProperty("PW");
            this.dbUrl = prop.getProperty("DB_URL");
            this.driverClassName = prop.getProperty("DRIVER_CLASS_NAME");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void newReferee(String username,String password) throws SQLException {
        PreparedStatement prepStmt = null;
        Referee r = new Referee(username, password);
        try {
            String sql = "INSERT INTO referee VALUES(?,?,?)";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, r.getUsername());
            prepStmt.setString(2, r.getPassword());
            prepStmt.setNull(3, Types.NULL);
            prepStmt.executeUpdate();
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Admin getAdminFromRef(String refUsername) throws SQLException {
        PreparedStatement prepStmt = null;
        Admin a = new Admin();
        try{
            String sql = "SELECT A.Username,A.Instagram,A.Facebook,A.Whatsapp,A.NomeC,A.DescrizioneC,A.Via FROM (referee R JOIN admin A ON R.fk_UsernameA1 = A.Username) WHERE R.Username=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,refUsername);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.first()){ // trovato admin
                rs.first();
                a.setUsername(rs.getString("A.Username"));
                a.setIg(rs.getString("A.Instagram"));
                a.setFb(rs.getString("A.Facebook"));
                a.setWa(rs.getString("A.Whatsapp"));
                a.setNomeC(rs.getString("A.NomeC"));
                a.setDescrizioneC(rs.getString("A.DescrizioneC"));
                a.setVia(rs.getString("A.Via"));
                //chiudo result set
                rs.close();
            }else{
                a = null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return a;
    }

    //chiede l aggiunta di una catch clause
    public Referee getReferee(String user) throws SQLException {
        PreparedStatement prepStmt = null;
        Referee ref = new Referee(user,"");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "SELECT * FROM referee WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
               ref=null;
            }else {
                //il player deve presente in quanto la label da dove prendo il nome non può essere vuota
                rs.first();
                ref.setUsername(rs.getString("Username"));
                ref.setPassword(rs.getString("Password")); //probabilmente non servirà
                ref.setAdminCampo(rs.getString("fk_UsernameA1"));
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
        return ref;
    }

    public static RefereeDAO getInstance() {
        if(instance == null){
            instance = new RefereeDAO();
        }
        return instance;
    }

    public void saveReport(ReportBean report) {
        String sql="Insert INTO Report values(null,?,?,?)";
        PreparedStatement prepStmt = null;
        try {
            prepStmt = instance.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1, report.getMotivazione());
            prepStmt.setString(2, report.getReferee());
            prepStmt.setString(3, report.getPlayer());
            prepStmt.executeUpdate();
        }catch (SQLException e){
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

    public void getConn(){
        try{
            Class.forName(driverClassName);
            instance.conn = DriverManager.getConnection(dbUrl, user, passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.conn;
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
