package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;
import java.io.IOException;

public class LoginController {

    public static boolean playerLogin(PlayerBean p) throws Exception {
        boolean res = false;
        //controllo username e password
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

    public static void closeConn() throws IOException {
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
