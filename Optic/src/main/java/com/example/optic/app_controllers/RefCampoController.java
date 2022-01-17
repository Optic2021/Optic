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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefCampoController {

    private RefCampoController(){
        //does np
    }

    public static void saveReport(ReportBean report) throws IOException {
        RefereeDAO dao=RefereeDAO.getInstance();
        dao.getConn();
        try {
            dao.saveReport(report);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Admin getAdminFromRef(UserBean user) throws IOException {
        Admin a = null;
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.getConn();
            a = dao.getAdminFromRef(user.getUsername());
        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    public static List<Event> getEventList(){
        List<Event> list = null;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getRefereeEventList();
        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public static Giornata getFirstPlay(UserBean bean){
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

    public static Giornata getNextPlay(GiornataBean bean){
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

    public static Giornata getLastPlay(GiornataBean bean){
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

    public static List<Player> getPlayersList(GiornataBean bean){
        ArrayList<Player> list = new ArrayList<>();
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            list = playDao.getPlayersList(bean.getIdPlay());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void closeConn() throws IOException {
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            Alert err = new Alert(Alert.AlertType.CONFIRMATION);
            err.setContentText("errore chiusura connessione");
            err.show();
        }
    }
}
