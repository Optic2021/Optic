package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.GiornataDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.ValutazioneDAO;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;

import java.io.IOException;
import java.util.ArrayList;

public class UserProfileAppController {

    public static Player getPlayer(PlayerBean p){
        Player player = null;
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            player = dao.getPlayer(p.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    public static ArrayList<Valutazione> getReviewList(PlayerBean p) throws IOException {
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        //utilizzo la dao del player dove è creata la connessisone
        try {
            PlayerDAO daoP = PlayerDAO.getInstance();
            ValutazioneDAO dao = new ValutazioneDAO(daoP);
            list = dao.getPlayerReviewList(p.getUsername());
        }catch (IOException e){
           e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Giornata> getRecentPlayList(UserBean user){
        ArrayList<Giornata> list = new ArrayList<Giornata>();
        try{
            PlayerDAO daoP = PlayerDAO.getInstance();
            GiornataDAO dao = new GiornataDAO(daoP);
            list = dao.getRecentPlayList(user.getUsername());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void setInfo(PlayerBean p){
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            player.setPlayerInfo(p.getUsername(),p.getDescrizione(),p.getFb(),p.getIg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
