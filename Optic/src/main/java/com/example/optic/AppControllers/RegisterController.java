package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Player;
import com.example.optic.entities.Referee;

import java.io.IOException;

public class RegisterController {

    public static boolean isUsernameUsed(UserBean user, int userType) throws Exception {
        boolean res = false;
        switch (userType){
            case 1 ->   {
                PlayerDAO p = PlayerDAO.getInstance();
                Player player = null;
                player = p.getPlayer(user.getUsername());
                if(player != null){
                    res = true;
                }
            }
            case 2 -> {
                AdminDAO a = AdminDAO.getInstance();
                Admin admin = null;
                admin = a.getAdmin(user.getUsername());
                if(admin != null) {
                    res = true;
                }
            }
            case 3 -> {
                RefereeDAO r= RefereeDAO.getInstance();
                Referee referee=null;
                referee=r.getReferee(user.getUsername());
                if(referee != null){
                    res = true;
                }
                //res = true; res = RefereeDAO.isReferee(username);
            }
            default ->{

            }
        }
        return res;
    }

    public static void userRegister(UserBean user,int userType) throws Exception {
        try {
            switch (userType) {
                case 1 -> {
                    PlayerDAO player = PlayerDAO.getInstance();
                    player.newPlayer(user.getUsername(), user.getPassword());
                }
                case 2 -> {
                    AdminDAO admin = AdminDAO.getInstance();
                    admin.newAdmin(user.getUsername(),user.getPassword(),user.getVia(),user.getNomeC());
                }
                case 3 -> {
                    System.out.println("Scrittura definitiva");
                    RefereeDAO referee = RefereeDAO.getInstance();
                    referee.newReferee(user.getUsername(), user.getPassword());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
