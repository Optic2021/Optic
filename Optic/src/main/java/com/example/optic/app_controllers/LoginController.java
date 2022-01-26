package com.example.optic.app_controllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Player;
import com.example.optic.entities.Referee;
import com.example.optic.utilities.ImportCloseConn;

public class LoginController {

    private LoginController(){
        //does np
    }

    public static boolean playerLogin(PlayerBean p) {
        boolean res = false;
        //controllo username e password
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            Player user = dao.getPlayer(p.getBUsername());
            //controllo se il player esiste
            if(user != null && user.getPassword().equals(p.getBPassword())){//controllo se esiste se la password è uguale
                //password uguale
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean adminLogin(AdminBean a) {
        boolean res = false;
        //controllo username e password
        try {
            AdminDAO dao = AdminDAO.getInstance();
            Admin user = dao.getAdmin(a.getUsername());
            //controllo se il player esiste
            if(user != null && user.getPassword().equals(a.getPassword())) {//controllo se esiste e se la password è uguale
                //password uguale
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean refereeLogin(RefereeBean r){
        boolean res=false;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            Referee ref = dao.getReferee(r.getUsername());
            if(ref!=null && ref.getPassword().equals(r.getPassword())){//controllo se esiste e se la password è uguale
                //password uguale
                res = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void closeConn(int user) {
        ImportCloseConn.closeConn(user);
    }
}
