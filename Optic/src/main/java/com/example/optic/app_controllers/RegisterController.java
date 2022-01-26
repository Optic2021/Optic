package com.example.optic.app_controllers;

import com.example.optic.bean.UserBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Player;
import com.example.optic.entities.Referee;
import com.example.optic.utilities.ImportCloseConn;
import java.sql.SQLException;

public class RegisterController {

    private RegisterController(){
        //does np
    }

    public static boolean isUsernameUsed(UserBean user, int userType) throws SQLException, ClassNotFoundException {
        boolean res = false;
        switch (userType){
            case 1 ->   {
                PlayerDAO p = PlayerDAO.getInstance();
                Player player ;
                player = p.getPlayer(user.getUsername());
                if(player != null){
                    res = true;
                }
            }
            case 2 -> {
                AdminDAO a = AdminDAO.getInstance();
                Admin admin ;
                admin = a.getAdmin(user.getUsername());
                if(admin != null) {
                    res = true;
                }
            }
            default ->{
                RefereeDAO r = RefereeDAO.getInstance();
                Referee referee ;
                r.getConn();
                referee = r.getReferee(user.getUsername());
                if(referee != null){
                    res = true;
                }
            }
        }
        return res;
    }

    public static void userRegister(UserBean user,int userType) {
        String username;
        String password;
        try {
            switch (userType) {
                case 1 -> {
                    PlayerDAO player = PlayerDAO.getInstance();
                    username = user.getUsername();
                    password = user.getPassword();
                    player.newPlayer(username, password);
                }
                case 2 -> {
                    AdminDAO admin = AdminDAO.getInstance();
                    String via = user.getVia();
                    String nomeC = user.getNomeC();
                    String prov = user.getProv();
                    username = user.getUsername();
                    password = user.getPassword();
                    admin.newAdmin(username,password,via,nomeC,prov);
                }
                default -> {
                    RefereeDAO referee = RefereeDAO.getInstance();
                    username = user.getUsername();
                    password = user.getPassword();
                    referee.getConn();
                    referee.newReferee(username, password);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeConn(int user) {
        ImportCloseConn.closeConn(user);
    }
}
