package com.example.optic.app_controllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.GiornataDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.PrenotazioneDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.entities.Referee;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSessionAppController {

    public BookSessionAppController(){
        //does np
    }

    //restituisce le informazioni di un campo dato l'admin associato
    public AdminBean getCampoInfo(AdminBean campo){
        AdminDAO dao = null;
        dao = AdminDAO.getInstance();
        String nomeC = campo.getNomeC();
        Admin x = null;
        try {
            x = dao.getCampo(nomeC);
        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
        AdminBean y = new AdminBean();
        Referee ref = null;
        if(x != null) {
            y.setUsername(x.getUsername());
            y.setPassword(x.getPassword());
            y.setDescrizione(x.getDescrizioneC());
            y.setFaceb(x.getFb());
            y.setInsta(x.getIg());
            y.setWhats(x.getWa());
            y.setNomeC(x.getNomeC());
            try {
                ref = dao.getRefereeFromAdmin(x.getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ref != null) {
                y.setReferee(ref.getUsername());
            }
            y.setVia(x.getVia());
            y.setProv(x.getProvincia());
        }
        return y;
    }

    //verifica che un player sia prenotato per uno specifico giorno
    public boolean isPlayerBooked(UserBean player, GiornataBean play){
        boolean res = true;
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            PrenotazioneDAO prenDao = new PrenotazioneDAO(dao);
            res = prenDao.isPlayerBooked(player.getUsername(), play.getIdPlay());
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    //Prenota effettivamente la giornata
    public void bookPlay(UserBean player, GiornataBean play){
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            PrenotazioneDAO prenDao = new PrenotazioneDAO(dao);
            prenDao.bookPlay(player.getUsername(), play.getIdPlay());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Prende la prima giornata disponibile da prenotare
    public Giornata getFirstPlay(UserBean bean){
        Giornata play = null;
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getFirstPlay(bean.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return play;
    }

    //restituisce la giornata di gioco passata più recente
    public Giornata getRecentPlay(UserBean bean){
        Giornata play = null;
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getRecentPlay(bean.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return play;
    }

    //Scorre in avanti le giornate
    public Giornata getNextPlay(GiornataBean bean){
        Giornata play = null;
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getNextPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    //Scorre indietro le giornate
    public Giornata getLastPlay(GiornataBean bean){
        Giornata play = null;
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getLastPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    //Restituisce la lista dei giocatori prenotati per una giornata
    public List<Player> getPlayersList(GiornataBean bean){
        List<Player> list;
        PlayerDAO dao = PlayerDAO.getInstance();
        GiornataDAO playDao = new GiornataDAO(dao);
        list = playDao.getPlayersList(bean.getIdPlay());
        return list;
    }

    public List<Admin> getCampi(){
        PlayerDAO dao = null;
        dao = PlayerDAO.getInstance();
        List<Admin> lista = new ArrayList<>();
        try{
            lista=dao.getCampoList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lista;
    }


    public void closeConn() throws IOException {
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Errore chiusura connessione al db");
            err.show();
        }
    }
}
