package com.example.optic.app_controllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.*;
import com.example.optic.entities.*;
import com.example.optic.utilities.NotARefereeException;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModPGPageAppController {

    private ModPGPageAppController(){
        //does np
    }

    //Restituisce admin dato uno username admin
    public static Admin getAdmin(AdminBean a){
        Admin admin = new Admin();
        try {
            AdminDAO dao = AdminDAO.getInstance();
            admin = dao.getAdmin(a.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    //modifica profili social
    public static void setAdminSocial(AdminBean a){
        String admin = a.getUsername();
        String fb = a.getFaceb();
        String ig = a.getInsta();
        String wa = a.getWhats();
        try{
            AdminDAO dao = AdminDAO.getInstance();
            dao.setAdminSocial(admin,ig,fb,wa);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //modifica descrizione campo
    public static void setDescription(AdminBean a){
        try{
            AdminDAO dao = AdminDAO.getInstance();
            String admin = a.getUsername();
            String desc = a.getDescrizione();
            dao.setDescription(admin,desc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //cerco arbitro tramite admin username
    public static Referee getRefereeFromAdmin(UserBean u){
        Referee ref = null;
        try {
            AdminDAO dao = AdminDAO.getInstance();
            ref = dao.getRefereeFromAdmin(u.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ref;
    }

    //restituisce prima giornata disponibile
    public static Giornata getFirstPlay(UserBean bean){
        Giornata play = null;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getFirstPlay(bean.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return play;
    }

    //restituisce la giornata di gioco passata più recente
    public static Giornata getRecentPlay(UserBean bean){
        Giornata play = null;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getRecentPlay(bean.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return play;
    }

    //restituisce giornata successiva
    public static Giornata getNextPlay(GiornataBean bean){
        Giornata play = null;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getNextPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    //restituisce giornata precedente
    public static Giornata getLastPlay(GiornataBean bean){
        Giornata play = null;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getLastPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    //cerco arbitro tramite il suo nome
    public static Referee getReferee(UserBean u) throws NotARefereeException{
        Referee ref = null;
        try {
            AdminDAO dao = AdminDAO.getInstance();
            ref = dao.getReferee(u.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ref == null){
            throw new NotARefereeException();
        }
        return ref;
    }

    //Lista review di un admin
    public static List<Valutazione> getReviewList(AdminBean a) {
        List<Valutazione> list;
        //utilizzo la dao dell'admin dove è creata la connessisone
        AdminDAO daoA = AdminDAO.getInstance();
        ValutazioneDAO dao = new ValutazioneDAO(daoA);
        list = dao.getAdminReviewList(a.getUsername());
        return list;
    }

    //Restituisce lista player prenotati
    public static List<Player> getPlayersList(GiornataBean bean) {
        List<Player> list;
        AdminDAO dao = AdminDAO.getInstance();
        GiornataDAO playDao = new GiornataDAO(dao);
        list = playDao.getPlayersList(bean.getIdPlay());
        return list;
    }

    //Restituisce lista eventi
    public static List<Event> getEventList() {
        List<Event> list = new ArrayList<>();
        try{
            AdminDAO dao = AdminDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getEventList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    //Verifica che una data sia valida
    public static boolean isDateValid(GiornataBean bean){
        boolean res ;
        AdminDAO dao = AdminDAO.getInstance();
        GiornataDAO playDao = new GiornataDAO(dao);
        String admin = bean.getAdmin();
        Calendar cal = bean.getData();
        res = playDao.isDateValid(admin,cal);
        return res;
    }

    //Inserisce una nuova giornata
    public static void insertPlay(GiornataBean bean){
        AdminDAO dao = AdminDAO.getInstance();
        GiornataDAO playDao = new GiornataDAO(dao);
        String event = bean.getEvento();
        String admin = bean.getAdmin();
        Calendar cal = bean.getData();
        playDao.insertPlay(admin,event,cal);
    }

    //setto all'admin (bean) il corrispettivo arbitro (bean2)
    public static void setReferee(UserBean admin, UserBean referee) {
        try{
            AdminDAO dao = AdminDAO.getInstance();
            dao.setReferee(admin.getUsername(),referee.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //"licenzia" referee dal campo
    public static void freeReferee(UserBean u) {
        try{
            AdminDAO dao = AdminDAO.getInstance();
            dao.freeReferee(u.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //chiude la connessione con il db
    public static void closeConn() throws IOException {
        try {
            AdminDAO dao = AdminDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            Alert err = new Alert(Alert.AlertType.CONFIRMATION);
            err.setContentText("errore chiusura connessione");
            err.show();
        }
    }
}
