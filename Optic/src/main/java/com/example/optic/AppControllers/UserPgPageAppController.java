package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.ValutazioneDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Campo;
import com.example.optic.dao.AdminDAO;
import java.io.IOException;
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
            System.out.println("Gia presente");
            dao.deleteValutazione(val);
        }
        dao.saveReview(val);
    }
}
