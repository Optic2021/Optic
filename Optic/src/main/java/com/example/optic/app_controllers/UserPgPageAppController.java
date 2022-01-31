package com.example.optic.app_controllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.dao.*;
import com.example.optic.entities.*;
import java.sql.SQLException;
import java.util.List;

public class UserPgPageAppController {

    private UserPgPageAppController(){
        //does np
    }

    public static AdminBean getCampoInfo(AdminBean campo){
        AdminDAO dao = null;
        dao = AdminDAO.getInstance();
        String nomeC = campo.getNomeCampo();
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
            y.setNomeCampo(x.getNomeC());
            try {
                ref = dao.getRefereeFromAdmin(x.getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ref != null) {
                y.setReferee(ref.getUsername());
            }
            y.setVia(x.getVia());
            y.setProvincia(x.getProvincia());
        }
        return y;
    }

    public static boolean isPlayerBooked(UserBean player, GiornataBean play){
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

    public static void bookPlay(UserBean player, GiornataBean play){
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            PrenotazioneDAO prenDao = new PrenotazioneDAO(dao);
            prenDao.bookPlay(player.getUsername(), play.getIdPlay());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Giornata getFirstPlay(UserBean bean){
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

    public static Giornata getNextPlay(GiornataBean bean){
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

    public static Giornata getLastPlay(GiornataBean bean){
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

    public static List<Player> getPlayersList(GiornataBean bean){
        List<Player> list;
        PlayerDAO dao = PlayerDAO.getInstance();
        GiornataDAO playDao = new GiornataDAO(dao);
        list = playDao.getPlayersList(bean.getIdPlay());
        return list;
    }

    public static void saveReview(ValutazioneBean val) {
        PlayerDAO p= PlayerDAO.getInstance();
        ValutazioneDAO dao=new ValutazioneDAO(p);
        if(dao.getValutazione(val,0)){
            dao.deleteValutazione(val,0);
        }
        dao.saveReview(val,0);
    }

    public static List<Valutazione> reviewList(AdminBean admin){
        List<Valutazione> list;

        PlayerDAO player= PlayerDAO.getInstance();
        ValutazioneDAO dao= new ValutazioneDAO(player);
        list=dao.getAdminReviewList1(admin.getNomeCampo());

        return list;
    }
}