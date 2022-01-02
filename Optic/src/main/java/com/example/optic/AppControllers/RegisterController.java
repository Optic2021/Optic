package com.example.optic.AppControllers;

import com.example.optic.dao.PlayerDAO;

public class RegisterController {

    public static boolean isUsernameUsed(String username, int userType) throws Exception {
        boolean res = false;
        PlayerDAO p = PlayerDAO.getInstance();
        switch (userType){
            case 1 -> res = p.isPlayerNameUsed(username);
            case 2 -> res = true; //res = AdminDAO.isAdmin(username);
            case 3 -> res = true; //res = RefereeDAO.isReferee(username);
            default ->{

            }
        }
        return res;
    }

    public static void playerRegister(String user, String pw) throws Exception {
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            player.newPlayer(user,pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean adminRegister(String user, String pw){
        boolean bool = true;
        return bool;
    }

    public static boolean refereeRegister(String user, String pw){
        boolean bool = true;
        return bool;
    }
}
