package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.ValutazioneDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Campo;
import com.example.optic.dao.AdminDAO;
import com.example.optic.entities.Valutazione;

import java.io.IOException;
import java.util.ArrayList;

public class UserPgPageAppController {

    public static AdminBean getCampoInfo(AdminBean campo) throws Exception{
        AdminDAO dao= AdminDAO.getInstance();

        String nomeC=campo.getNomeCampo();
        System.out.println("Nome Campo: "+nomeC);

        Admin x = dao.getCampo(nomeC);
        AdminBean y = new AdminBean();

        /*y.setUsername(x.getUsername());
        y.setUsername(x.getUsername());*/

        y.setPassword(x.getPassword());
        y.setDescrizione(x.getDescrizioneC());
        y.setFb(x.getFb());
        y.setIg(x.getIg());
        y.setWa(x.getWa());
        y.setNomeCampo(x.getNomeC());
        //y.setReferee(x.getReferee());
        //y.setVia(x.getVia());*/
        return y;
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
            list=dao.getAdminReviewList1(admin.getUsername());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}