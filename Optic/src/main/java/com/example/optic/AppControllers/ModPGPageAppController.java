package com.example.optic.AppControllers;

import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import java.io.IOException;

public class ModPGPageAppController {
    public static void closeConn() throws IOException {
        try {
            AdminDAO dao = AdminDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
