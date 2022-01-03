package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;

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

    public static void setInfo(PlayerBean p){
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            player.setPlayerInfo(p.getUsername(),p.getDescrizione(),p.getFb(),p.getIg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
