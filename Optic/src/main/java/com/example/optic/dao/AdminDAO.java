package com.example.optic.dao;

import com.example.optic.bean.AdminBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Referee;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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

    public void newAdmin(String username,String password,String via, String nomeC) throws Exception {
        Statement stmt = null;
        Admin admin = new Admin(username, password, via, nomeC);
        try {
            if (instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "INSERT INTO admin VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, admin.getUsername());
            prepStmt.setString(2, admin.getPassword());
            prepStmt.setString(3, admin.getIg());
            prepStmt.setString(4, admin.getFb());
            prepStmt.setString(5, admin.getWa());
            prepStmt.setString(6, admin.getDescrizioneC());
            prepStmt.setString(7, admin.getNomeC());
            prepStmt.setString(8, admin.getVia());
            prepStmt.executeUpdate();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
        }
    }

    //recupero la prima giornata disponibile per la prenotazione dei giocatori
    public Giornata getFirstPlay(String admin) throws Exception{
        Statement stmt = null;
        Giornata play = null;
        try{
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM giornata WHERE Data=(SELECT min(Data) FROM giornata WHERE curdate() < Data AND fk_UsernameA2 =?)";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,admin);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.first()){ //giornata trovata
                rs.first();
                //trasformo il formato di data di mysql
                Calendar data = Calendar.getInstance();
                data.setTime(rs.getDate("Data"));
                int nGiocatori = rs.getInt("NumGiocatori");
                String evento = rs.getString("fk_Nome");
                play = new Giornata(data,nGiocatori,evento);
                //chiudo result set
                rs.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return play;
    }

    public static void setAdmin1(String user,String instagram,String facebook,String whatsapp,String DescrizioneC,String NomeC) {
        //qui possiamo dividere i due tipi di update
    }

    public static void setInfoPg(String user,String instagram,String facebook,String whatsapp,String DescrizioneC) throws SQLException {
        Statement stmt = null;
        int ret;
        Admin a = new Admin(user,"");
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Admin getAdmin(String user) throws Exception {
        Statement stmt = null;
        Admin admin = new Admin(user,"");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM admin WHERE Username=?";
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
                admin.setVia(rs.getString("Via"));

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
        }
        return admin;
    }

    //recupero l'arbitro utilizzando il nome dell'admin
    public static Referee getRefereeFromAdmin(String user)throws Exception{
        Statement stmt = null;
        Referee ref = null;
        try{
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM referee WHERE fk_UsernameA1=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ref;
    }

    //recupero l'arbitro utilizzando il nome dello stesso
    public static Referee getReferee(String user)throws Exception{
        Statement stmt = null;
        Referee ref = null;
        try{
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM referee WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ref;
    }

    public void setReferee(String admin, String ref) throws SQLException {
        Statement stmt = null;
        try{
            stmt = instance.conn.createStatement();
            String sql = "UPDATE referee SET fk_UsernameA1=? WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1,admin);
            prepStmt.setString(2,ref);
            prepStmt.executeUpdate();
            System.out.println(admin+" "+ref);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void freeReferee(String username) throws SQLException{
        Statement stmt = null;
        try{
            stmt = instance.conn.createStatement();
            String sql = "UPDATE referee SET fk_UsernameA1=? WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setNull(1, Types.NULL);
            prepStmt.setString(2,username);
            prepStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static AdminDAO getInstance() throws IOException {
        if(AdminDAO.instance == null){
            AdminDAO.instance = new AdminDAO();
        }
        return instance;
    }

    //ritorno l'ogetto Connection
    public Connection getConnection(){
        return this.conn;
    }

    //Da finire con adminDao
    public Admin getCampo(String nomeC) throws Exception {
        Statement stmt = null;
        Admin admin = new Admin("","");
        admin.setNomeC(nomeC);
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            //String sql = "SELECT * FROM admin join referee on referee.fk_UsernameA1=admin.Username WHERE NomeC=?";
            String sql = "SELECT * FROM admin WHERE NomeC=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,admin.getNomeC());
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
                //admin.setVia(rs.getString("Via"));
                //admin.setReferee(rs.getString("referee.Username"));

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
        }
        return admin;
    }

    public ArrayList<AdminBean> getCampoList() throws Exception {
        /*String user;
        String pass;
        String ig;
        String fb;
        String wa;
        String desc;
        String via;
        String referee;*/
        System.out.println("dioghane fai sto commit");
        String nomec;
        String desc;

        ArrayList<AdminBean> list= new ArrayList<AdminBean>();

        Statement stmt = null;
        //AdminBean admin = new AdminBean();
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            //String sql = "SELECT * FROM admin join referee on referee.fk_UsernameA1=admin.Username WHERE NomeC=?";
            String sql = "SELECT NomeC, DescrizioneC FROM admin";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                AdminBean admin = null;
            }else {
                rs.first();
                do {
                    AdminBean admin = new AdminBean();
                    admin.setNomeCampo((rs.getString("NomeC")));
                    admin.setDescrizione((rs.getString("DescrizioneC")));
                    list.add(admin);

                }while (rs.next());
                rs.close();
            }
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public void getConn(){
        try{
            Class.forName(DRIVER_CLASS_NAME);
            instance.conn = DriverManager.getConnection(DB_URL, USER, PW);
            System.out.println("Connessione al db effettuata");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConn(){
        try{
            if (instance.conn != null) {
                instance.conn.close();
                System.out.println("Connessione al db chiusa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}