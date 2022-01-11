package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.dao.GiornataDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.ValutazioneDAO;
import com.example.optic.entities.*;
import com.example.optic.dao.AdminDAO;

import java.io.IOException;
import java.util.ArrayList;

public class UserPgPageAppController {

    public static AdminBean getCampoInfo(AdminBean campo) throws Exception{
        AdminDAO dao = AdminDAO.getInstance();
        String nomeC = campo.getNomeCampo();;
        Admin x = dao.getCampo(nomeC);
        System.out.println(nomeC);
        AdminBean y = new AdminBean();

        y.setUsername(x.getUsername());
        y.setPassword(x.getPassword());
        y.setDescrizione(x.getDescrizioneC());
        y.setFb(x.getFb());
        y.setIg(x.getIg());
        y.setWa(x.getWa());
        y.setNomeCampo(x.getNomeC());
        Referee ref = dao.getRefereeFromAdmin(x.getUsername());
        y.setReferee(ref.getUsername());
        y.setVia(x.getVia());
        y.setProvincia(x.getProvincia());
        return y;
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

    public static ArrayList<Player> getPlayersList(GiornataBean bean) throws IOException{
        ArrayList<Player> list = new ArrayList<Player>();
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            list = playDao.getPlayersList(bean.getIdPlay());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void saveReview(ValutazioneBean val) throws IOException {
        PlayerDAO p= PlayerDAO.getInstance();
        ValutazioneDAO dao=new ValutazioneDAO(p);
        if(dao.getValutazione(val)){
            dao.deleteValutazione(val);
        }
        dao.saveReview(val);
    }

    public static ArrayList<Valutazione> reviewList(AdminBean admin){
        ArrayList<Valutazione> list = null;
        try {

            PlayerDAO player= PlayerDAO.getInstance();
            ValutazioneDAO dao= new ValutazioneDAO(player);
            list=dao.getAdminReviewList1(admin.getNomeCampo());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
