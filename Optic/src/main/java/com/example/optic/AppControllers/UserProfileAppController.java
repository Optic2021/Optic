package com.example.optic.AppControllers;

import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;

public class UserProfileAppController {

    public static Player getPlayer(String username){
        Player p = null;
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            p = player.getPlayer(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
