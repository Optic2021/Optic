package com.example.optic.app_controllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.*;
import com.example.optic.entities.Event;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;
import com.example.optic.entities.Giornata;
import com.example.optic.utilities.EmptyReportListException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileAppController {

    private UserProfileAppController(){
        //does np
    }

    //Restituisce info player dato uno username
    public static Player getPlayer(PlayerBean p){
        Player player = null;
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            player = dao.getPlayer(p.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    public static List<Valutazione> getReviewList(PlayerBean p) {
        List<Valutazione> list;
        //utilizzo la dao del player dove è creata la connessisone
        PlayerDAO daoP = PlayerDAO.getInstance();
        ValutazioneDAO dao = new ValutazioneDAO(daoP);
        list = dao.getPlayerReviewList(p.getUsername());
        return list;
    }

    //Modifica informazioni del profilo
    public static void setInfo(PlayerBean p){
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            player.setPlayerInfo(p.getUsername(),p.getBDescrizione(),p.getBFb(),p.getBIg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Salva review nel profilo
    public static void saveReview(ValutazioneBean val){
        PlayerDAO playerDAO= null;
        playerDAO = PlayerDAO.getInstance();
        ValutazioneDAO dao = new ValutazioneDAO(playerDAO);
        if(dao.getValutazione(val,1)){
            dao.deleteValutazione(val,1);
        }
        dao.saveReview(val,1);
    }

    //Restituisce lo storico partite
    public static List<Giornata> getRecentPlayList(UserBean user) {
        List<Giornata> list;
        PlayerDAO daoP = PlayerDAO.getInstance();
        GiornataDAO dao = new GiornataDAO(daoP);
        list = dao.getRecentPlayList(user.getUsername());
        return list;
    }

    //Restituisce la lista di eventi
    public static List<Event> getEventList(){
        List<Event> list = new ArrayList<>();
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getEventList();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    //Restituisce la lista di report ricevuti
    public static List<ReportBean> getReportList(String user) throws EmptyReportListException {
        List<ReportBean> list;
        PlayerDAO dao = PlayerDAO.getInstance();
        list = dao.getPlayerReportList(user);
        if(list.isEmpty()){
            throw new EmptyReportListException();
        }
        return list;
    }
}
