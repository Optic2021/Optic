package com.example.optic.AppControllers;

import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;

import java.io.IOException;

public class RefCampoController {

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
