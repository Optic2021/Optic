package com.example.optic.AppControllers;

import com.example.optic.dao.PlayerDAO;

import java.io.IOException;

public class BookSessionAppController {

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
