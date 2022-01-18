package com.example.optic.app_controllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.dao.PlayerDAO;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSessionAppController {

    private BookSessionAppController(){
        //does np
    }

    public static List<AdminBean> getCampi(){
        PlayerDAO dao = null;
        try {
            dao = PlayerDAO.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<AdminBean> lista = new ArrayList<>();
        try{
            lista=dao.getCampoList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
