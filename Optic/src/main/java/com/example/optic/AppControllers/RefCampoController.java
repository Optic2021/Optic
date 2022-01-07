package com.example.optic.AppControllers;

import com.example.optic.bean.UserBean;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.entities.Admin;
import java.io.IOException;
import java.sql.SQLException;

public class RefCampoController {

    public static Admin getAdminFromRef(UserBean user) throws IOException {
        Admin a = null;
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.getConn();
            a = dao.getAdminFromRef(user.getUsername());
        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    public static void closeConn() throws IOException {
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
