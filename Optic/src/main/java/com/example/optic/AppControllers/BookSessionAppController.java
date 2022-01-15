package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class BookSessionAppController {

    private BookSessionAppController(){
        //does np
    }

    public static List<AdminBean> getCampi() throws Exception {
        AdminDAO dao = AdminDAO.getInstance();
        ArrayList<AdminBean> lista = new ArrayList<>();
        try {
            lista=dao.getCampoList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }


    public static void closeConn() throws IOException {
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Errore chiusura connessione al db");
            err.show();
        }
    }
}
