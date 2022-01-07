package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.dao.ValutazioneDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Referee;
import com.example.optic.entities.Valutazione;
import java.io.IOException;
import java.util.ArrayList;

public class ModPGPageAppController {

    public static Admin getAdmin(AdminBean a){
        Admin admin = null;
        try {
            AdminDAO dao = AdminDAO.getInstance();
            admin = dao.getAdmin(a.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
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

    //cerco arbitro tramite il suo nome
    public static Referee getReferee(UserBean u){
        Referee ref = null;
        try {
            AdminDAO dao = AdminDAO.getInstance();
            ref = dao.getReferee(u.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ref;
    }

    public static ArrayList<Valutazione> getReviewList(AdminBean a) throws IOException {
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        //utilizzo la dao dell'admin dove è creata la connessisone
        try {
            AdminDAO daoA = AdminDAO.getInstance();
            ValutazioneDAO dao = new ValutazioneDAO(daoA);
            list = dao.getAdminReviewList(a.getUsername());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    //setto all'admin (bean) il corrispettivo arbitro (bean2)
    public static void setReferee(UserBean admin, UserBean referee) {
        try{
            AdminDAO dao = AdminDAO.getInstance();
            System.out.println("ok3");
            dao.setReferee(admin.getUsername(),referee.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void freeReferee(UserBean u) {
        try{
            AdminDAO dao = AdminDAO.getInstance();
            dao.freeReferee(u.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeConn() throws IOException {
        try {
            AdminDAO dao = AdminDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
