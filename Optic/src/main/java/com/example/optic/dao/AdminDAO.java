package com.example.optic.dao;

import com.example.optic.entities.Admin;
import com.example.optic.entities.Referee;
import com.example.optic.utilities.ImportAdminDAO;
import com.example.optic.utilities.ImportDAO;

import java.io.IOException;
import java.sql.*;

public class AdminDAO {

    private static AdminDAO instance = null;
    private Connection conn;

    //costruttore
    protected AdminDAO(){
        this.conn = null;
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
        Admin admin ;
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "SELECT * FROM admin WHERE Username=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            admin= ImportAdminDAO.setAdmin(rs);
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
                String name = rs.getString("Username");
                String pw = rs.getString("Password");
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
                String name = rs.getString("Username");
                String pw = rs.getString("Password");
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
        Admin admin= null;
        PreparedStatement prepStmt = null;
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            String sql = "SELECT * FROM admin WHERE NomeC=?";
            prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,nomeC);
            ResultSet rs = prepStmt.executeQuery();
            admin= ImportAdminDAO.setAdmin(rs);
        } finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return admin;
    }



    public void getConn() throws ClassNotFoundException,SQLException {
        ImportDAO imp=new ImportDAO();
        Class.forName(imp.getDriverClassName());
        instance.conn = DriverManager.getConnection(imp.getDbUrl(), imp.getUser(), imp.getPassWord());
    }

    public void closeConn() throws SQLException {
        if (instance.conn != null) {
            instance.conn.close();
        }
    }
}