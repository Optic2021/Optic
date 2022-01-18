package com.example.optic.dao;

import com.example.optic.entities.Admin;
import com.example.optic.entities.Referee;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class AdminDAO {
    private String user;
    private String passWord;
    private String dbUrl;
    private String driverClassName;
    private String userField = "Username";
    private String pwField = "Password";


    private static AdminDAO instance = null;
    private Connection conn;

    //costruttore
    protected AdminDAO() throws IOException {
        this.conn = null;
        InputStream input = getClass().getClassLoader().getResourceAsStream("prop.properties");
        Properties prop = new Properties();
        prop.load(input);
        this.user = prop.getProperty("USER");
        this.passWord = prop.getProperty("PW");
        this.dbUrl = prop.getProperty("DB_URL");
        this.driverClassName = prop.getProperty("DRIVER_CLASS_NAME");
    }

    public void newAdmin(String username,String password,String via, String nomeC, String prov) throws SQLException,ClassNotFoundException {
        Admin admin = new Admin(username, password, via, nomeC, prov);
        PreparedStatement prepStmt = null;
        try {
            if (instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "INSERT INTO admin VALUES(?,?,?,?,?,?,?,?,?)";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, admin.getUsername());
            prepStmt.setString(2, admin.getPassword());
            prepStmt.setString(3, admin.getIg());
            prepStmt.setString(4, admin.getFb());
            prepStmt.setString(5, admin.getWa());
            prepStmt.setString(6, admin.getDescrizioneC());
            prepStmt.setString(7, admin.getNomeC());
            prepStmt.setString(8, admin.getVia());
            prepStmt.setString(9, admin.getProvincia());
            prepStmt.executeUpdate();
        } finally {
            if (prepStmt != null) {
                prepStmt.close();
            }
        }
    }

    public void setAdminSocial(String user,String instagram,String facebook,String whatsapp) throws SQLException, ClassNotFoundException {
        PreparedStatement prepStmt = null;
        try {
            if (instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "UPDATE admin SET Instagram=?, Facebook=?, Whatsapp=? WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, instagram);
            prepStmt.setString(2, facebook);
            prepStmt.setString(3, whatsapp);
            prepStmt.setString(4, user);
            prepStmt.executeUpdate();
        } finally {
            if (prepStmt != null)
                prepStmt.close();
        }
    }

    public void setDescription(String admin, String desc) throws SQLException {
        PreparedStatement prepStmt = null;
        try{
            String sql = "UPDATE admin SET DescrizioneC=? WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, desc);
            prepStmt.setString(2, admin);
            prepStmt.executeUpdate();
        } finally {
            if (prepStmt != null)
                prepStmt.close();
        }
    }

    public Admin getAdmin(String user) throws SQLException,ClassNotFoundException{
        PreparedStatement prepStmt = null;
        Admin admin = new Admin(user,"");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "SELECT * FROM admin WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,admin.getUsername());
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                admin = null;
            }else {
                rs.first();
                admin.setUsername(rs.getString(userField));
                admin.setPassword(rs.getString(pwField));
                admin.setIg(rs.getString("Instagram"));
                admin.setFb(rs.getString("Facebook"));
                admin.setWa(rs.getString("Whatsapp"));
                admin.setDescrizioneC(rs.getString("DescrizioneC"));
                admin.setNomeC(rs.getString("NomeC"));
                admin.setVia(rs.getString("Via"));
                admin.setProvincia(rs.getString("Provincia"));
                //chiudo result set
                rs.close();
            }
        } finally {
            if (prepStmt != null) {
                prepStmt.close();
            }
        }
        return admin;
    }

    //recupero l'arbitro utilizzando il nome dell'admin
    public Referee getRefereeFromAdmin(String user)throws SQLException {
        PreparedStatement prepStmt = null;
        Referee ref;
        try{
            String sql = "SELECT * FROM referee WHERE fk_UsernameA1=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                ref=null;
            }else {
                rs.first();
                String name = rs.getString(userField);
                String pw = rs.getString(pwField);
                String admin = rs.getString("fk_UsernameA1");
                ref = new Referee(name,pw,admin);
                //chiudo result set
                rs.close();
            }
        } finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return ref;
    }

    //recupero l'arbitro utilizzando il nome dello stesso
    public Referee getReferee(String user)throws SQLException {
        PreparedStatement prepStmt = null;
        Referee ref;
        try{
            String sql = "SELECT * FROM referee WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                ref=null;
            }else {
                rs.first();
                String name = rs.getString(userField);
                String pw = rs.getString(pwField);
                String admin = rs.getString("fk_UsernameA1");
                ref = new Referee(name,pw,admin);
                //chiudo result set
                rs.close();
            }
        } finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return ref;
    }

    public void setReferee(String admin, String ref) throws SQLException {
        PreparedStatement prepStmt = null;
        try{
            String sql = "UPDATE referee SET fk_UsernameA1=? WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,admin);
            prepStmt.setString(2,ref);
            prepStmt.executeUpdate();
        } finally{
            if (prepStmt != null)
                prepStmt.close();
        }
    }

    public void freeReferee(String username) throws SQLException{
        PreparedStatement prepStmt = null;
        try{
            String sql = "UPDATE referee SET fk_UsernameA1=? WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setNull(1, Types.NULL);
            prepStmt.setString(2,username);
            prepStmt.executeUpdate();
        } finally{
            if (prepStmt != null)
                prepStmt.close();
        }
    }

    public static AdminDAO getInstance() throws IOException {
        if(instance == null){
            instance = new AdminDAO();
        }
        return instance;
    }

    //ritorno l'ogetto Connection
    public Connection getConnection(){
        return this.conn;
    }

    //Da finire con adminDao
    public Admin getCampo(String nomeC)throws ClassNotFoundException,SQLException {
        PreparedStatement prepStmt = null;
        Admin admin = new Admin("","");
        admin.setNomeC(nomeC);
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "SELECT * FROM admin WHERE NomeC=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,admin.getNomeC());
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                admin = null;
            }else {
                rs.first();
                admin.setUsername(rs.getString(userField));
                admin.setPassword(rs.getString(pwField));
                admin.setIg(rs.getString("Instagram"));
                admin.setFb(rs.getString("Facebook"));
                admin.setWa(rs.getString("Whatsapp"));
                admin.setDescrizioneC(rs.getString("DescrizioneC"));
                admin.setNomeC(rs.getString("NomeC"));
                admin.setVia(rs.getString("Via"));
                admin.setProvincia(rs.getString("Provincia"));

                //chiudo result set
                rs.close();
            }
        } finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return admin;
    }



    public void getConn() throws ClassNotFoundException,SQLException {
        Class.forName(driverClassName);
        instance.conn = DriverManager.getConnection(dbUrl, user, passWord);
    }

    public void closeConn() throws SQLException {
        if (instance.conn != null) {
            instance.conn.close();
        }
    }
}