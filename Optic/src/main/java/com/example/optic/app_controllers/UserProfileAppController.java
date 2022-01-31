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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileAppController {

    public UserProfileAppController(){
        //does np
    }

    public static Player getPlayer(PlayerBean p){
        Player player = null;
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            player = dao.getPlayer(p.getBUsername());
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
        list = dao.getPlayerReviewList(p.getBUsername());
        return list;
    }

    public static void setInfo(PlayerBean p){
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            player.setPlayerInfo(p.getBUsername(),p.getBDescrizione(),p.getBFb(),p.getBIg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveReview(ValutazioneBean val){
        PlayerDAO playerDAO= null;
        playerDAO = PlayerDAO.getInstance();
        ValutazioneDAO dao = new ValutazioneDAO(playerDAO);
        if(dao.getValutazione(val,1)){
            dao.deleteValutazione(val,1);
        }
        dao.saveReview(val,1);
    }
    public static List<Giornata> getRecentPlayList(UserBean user) throws SQLException {
        List<Giornata> list;
        PlayerDAO daoP = PlayerDAO.getInstance();
        GiornataDAO dao = new GiornataDAO(daoP);
        list = dao.getRecentPlayList(user.getUsername());
        return list;
    }

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

    public static List<ReportBean> getReportList(String user) {
        List<ReportBean> list;
        PlayerDAO dao = PlayerDAO.getInstance();
        list = dao.getPlayerReportList(user);
        return list;
    }
}
