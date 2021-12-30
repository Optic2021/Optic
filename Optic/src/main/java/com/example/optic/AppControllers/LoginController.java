package com.example.optic.AppControllers;

import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;

public class LoginController {

    public static boolean playerLogin(String user, String pw) throws Exception {
        boolean res = false;
        //devo controllare lo username e la password
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            res = player.authentication(user, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean adminLogin(String user, String pw){
        boolean bool = true;
        return bool;
    }

    public static boolean refereeLogin(String user, String pw){
        boolean bool = true;
        return bool;
    }
}
