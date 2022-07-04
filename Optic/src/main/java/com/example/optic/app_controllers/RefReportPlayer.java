package com.example.optic.app_controllers;

import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.*;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Event;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefReportPlayer {

    public RefReportPlayer(){
        //does np
    }

    public void saveReport(ReportBean report) {
        RefereeDAO dao=RefereeDAO.getInstance();
        dao.getConn();
        dao.saveReport(report);
    }

    public Admin getAdminFromRef(UserBean user) {
        Admin a = null;
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.getConn();
            a = dao.getAdminFromRef(user.getUsername());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    public List<Event> getEventList(){
        List<Event> list = null;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getEventList();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    //Prende la prossima giornata da giocare
    public Giornata getFirstPlay(UserBean bean){
        Giornata play = null;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getFirstPlay(bean.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return play;
    }

    //Prende la giornata successiva
    public Giornata getNextPlay(GiornataBean bean){
        Giornata play = null;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getNextPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    //Prende la giornata precedente
    public Giornata getLastPlay(GiornataBean bean){
        Giornata play = null;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getLastPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    public List<Player> getPlayersList(GiornataBean bean){
        List<Player> list;
        RefereeDAO dao = RefereeDAO.getInstance();
        GiornataDAO playDao = new GiornataDAO(dao);
        list = playDao.getPlayersList(bean.getIdPlay());
        return list;
    }

    public void closeConn() throws IOException {
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            Alert err = new Alert(AlertType.ERROR);
            err.setContentText("errore chiusura connessione");
            err.show();
        }
    }
}
