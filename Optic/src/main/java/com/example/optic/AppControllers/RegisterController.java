package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;

public class RegisterController {

    public static boolean isUsernameUsed(PlayerBean user, int userType) throws Exception {
        boolean res = false;
        PlayerDAO p = PlayerDAO.getInstance();
        Player player = null;
        switch (userType){
            case 1 ->   {
                player = p.getPlayer(user.getUsername());
                if(player != null){
                    res = true;
                }
            }
            case 2 -> res = true; //res = AdminDAO.isAdmin(username);
            case 3 -> res = true; //res = RefereeDAO.isReferee(username);
            default ->{

            }
        }
        return res;
    }

    public static void playerRegister(PlayerBean user) throws Exception {
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            player.newPlayer(user.getUsername(),user.getPassword());
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
