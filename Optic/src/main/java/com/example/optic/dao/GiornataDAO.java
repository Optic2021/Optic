package com.example.optic.dao;

import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GiornataDAO {
    private PlayerDAO daoP;
    private AdminDAO daoA;
    private RefereeDAO daoR;
    private String formato = "yyyy-MM-dd";

    public GiornataDAO(PlayerDAO daoP){
        this.daoP = daoP;
        this.daoA = null;
        this.daoR = null;
    }

    public GiornataDAO(AdminDAO daoA){
        this.daoA = daoA;
        this.daoP = null;
        this.daoR = null;
    }

    public GiornataDAO(RefereeDAO daoR){
        this.daoR = daoR;
        this.daoA = null;
        this.daoP = null;
    }

    public Giornata getPlay(String admin,Calendar cal, String sql) throws SQLException {
        PreparedStatement prepStmt = null;
        Giornata play = null;
        Date data = null;
        try{
            if(this.daoA != null) {
                prepStmt = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }else if(this.daoP != null){
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }else if(this.daoR != null){
                prepStmt = this.daoR.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
            if(prepStmt != null) {
                DateFormat dateFormat = new SimpleDateFormat(formato);//creo formato per la data
                data = cal.getTime();//converto il calendar in data
                prepStmt.setString(1, dateFormat.format(data));//converto la data in string
                prepStmt.setString(2, admin);
                ResultSet rs = prepStmt.executeQuery();
                if (rs.first()) { //giornata trovata
                    rs.first();
                    //trasformo il formato di data di mysql
                    int idPlay = rs.getInt("idGiornata");
                    Calendar dateCalendar = Calendar.getInstance();
                    dateCalendar.setTime(rs.getDate("Data"));
                    int nGiocatori = rs.getInt("NumGiocatori");
                    String evento = rs.getString("fk_Nome");
                    play = new Giornata(idPlay, dateCalendar, nGiocatori, evento);
                    //chiudo result set
                    rs.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return play;
    }

    //recupero la prima giornata disponibile per la prenotazione dei giocatori
    public Giornata getFirstPlay(String admin) throws SQLException {
        String sql = "SELECT * FROM giornata WHERE Data=(SELECT min(Data) FROM giornata WHERE ? < Data AND fk_UsernameA2 =?)";
        Calendar cal = Calendar.getInstance();
        return this.getPlay(admin,cal,sql);
    }

    public Giornata getNextPlay(String admin, Calendar cal) throws SQLException {
        String sql = "SELECT * FROM giornata WHERE Data=(SELECT min(G.Data) FROM giornata G WHERE ?<G.Data AND G.fk_UsernameA2 =?)";
        return this.getPlay(admin,cal,sql);
    }

    public Giornata getLastPlay(String admin, Calendar cal) throws SQLException {
        String sql = "SELECT * FROM giornata WHERE Data=(SELECT max(G.Data) FROM giornata G WHERE ?>G.Data AND G.fk_UsernameA2 =?)";
        return this.getPlay(admin,cal,sql);
    }

    public List<Giornata> getRecentPlayList(String user) throws SQLException {
        ArrayList<Giornata> list = new ArrayList<>();
        PreparedStatement prepStmt = null;
        Calendar dateCalendar = Calendar.getInstance();
        Giornata play = null;
        String campo;
        try{
            String sql = "SELECT G.Data, C.NomeC FROM ((prenotazione P JOIN giornata G ON P.fk_idGiornata = G.idGiornata AND P.fk_Username = ?) JOIN admin C ON G.fk_UsernameA2 = C.Username) WHERE G.Data < curdate()";
            prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()) {
                rs.first();
                do {
                    dateCalendar.setTime(rs.getDate("G.Data"));
                    campo = rs.getString("C.NomeC");
                    play = new Giornata(dateCalendar,campo);
                    list.add(play);
                } while(rs.next());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (prepStmt != null)
                prepStmt.close();
        }
        return list;
    }

    public List<Player> getPlayersList(int playId){
        ArrayList<Player> list = new ArrayList<>();
        PreparedStatement prepStmt = null;
        String sql = "SELECT Username,Valutazione FROM (player JOIN prenotazione ON Username = fk_Username) WHERE fk_idGiornata =?";
        String nome;
        int stelle;
        try{
            //controllo quale connessione è attualmente attiva e la utilizzo
            if(this.daoA != null) {
                prepStmt = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }else if(this.daoP != null){
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }else if(this.daoR != null){
                prepStmt = this.daoR.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
            if(prepStmt != null) {
                prepStmt.setString(1, Integer.toString(playId));
                ResultSet rs = prepStmt.executeQuery();
                if (rs.first()) {
                    rs.first();
                    do {
                        nome = rs.getString("Username");
                        stelle = rs.getInt("Valutazione");
                        Player p = new Player(nome, stelle);
                        list.add(p);
                    } while (rs.next());
                }
                rs.close();
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

    public boolean isDateValid(String admin, Calendar cal){
        boolean res = true;
        PreparedStatement prepStmt = null;
        try{
            DateFormat dateFormat = new SimpleDateFormat(formato);//creo formato per la data
            Date data = cal.getTime();//converto il calendar in data
            String sql = "SELECT * FROM giornata WHERE Data =? AND fk_UsernameA2 =?";
            prepStmt = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1, dateFormat.format(data));
            prepStmt.setString(2, admin);
            ResultSet rs = prepStmt.executeQuery();
            if(!rs.first()){
                res = false;
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
        return res;
    }

    public void insertPlay(String admin, String evento, Calendar cal){
        PreparedStatement prepStmt = null;
        try{
            DateFormat dateFormat = new SimpleDateFormat(formato);//creo formato per la data
            Date data = cal.getTime();//converto il calendar in data
            String sql = "INSERT INTO giornata (Data, NumGiocatori, fk_Nome, fk_UsernameA2) VALUES(?,?,?,?)";
            prepStmt = this.daoA.getConnection().prepareStatement(sql);
            prepStmt.setString(1, dateFormat.format(data));
            prepStmt.setNull(2, Types.NULL);
            prepStmt.setString(3, evento);
            prepStmt.setString(4, admin);
            prepStmt.executeUpdate();
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
    }

}
