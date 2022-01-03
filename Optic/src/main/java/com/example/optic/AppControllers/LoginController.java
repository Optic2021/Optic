package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;
import javafx.scene.control.Alert;

public class LoginController {

    public static boolean playerLogin(PlayerBean p) throws Exception {
        boolean res = false;
        //devo controllare lo username e la password
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            Player user = dao.getPlayer(p.getUsername());
            //controllo se il player esiste
            if(user != null){
                //esiste
                if(user.getPassword().equals(p.getPassword())){//controllo se la password è uguale
                    //password uguale
                    res = true;
                }
            }
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
