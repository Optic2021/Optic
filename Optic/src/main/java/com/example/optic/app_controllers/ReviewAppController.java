package com.example.optic.app_controllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.ValutazioneDAO;
import com.example.optic.entities.Valutazione;
import java.util.List;

public class ReviewAppController {

    private ReviewAppController(){
        //does np
    }

    //salva una valutazione per il campo
    public static void saveReview(ValutazioneBean val) {
        PlayerDAO p= PlayerDAO.getInstance();
        ValutazioneDAO dao=new ValutazioneDAO(p);
        if(dao.getValutazione(val,0)){
            dao.deleteValutazione(val,0);
        }
        dao.saveReview(val, 0);

    }

    //Prende la lista di valutazioni
    public static List<Valutazione> reviewList(AdminBean admin){
        List<Valutazione> list;

        PlayerDAO player= PlayerDAO.getInstance();
        ValutazioneDAO dao= new ValutazioneDAO(player);
        list=dao.getAdminReviewList1(admin.getNomeCampo());

        return list;
    }
}
