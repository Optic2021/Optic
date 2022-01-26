package com.example.optic.utilities;

import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.SQLException;

public class ImportCloseConn {
    private ImportCloseConn(){
        //does nothing
    }

    public static void closeConn(int user) {
        try {
            switch (user) {
                case 1 -> {
                    PlayerDAO player = PlayerDAO.getInstance();
                    player.closeConn();
                }
                case 2 -> {
                    AdminDAO admin = AdminDAO.getInstance();
                    admin.closeConn();
                }
                default -> {
                    RefereeDAO referee = RefereeDAO.getInstance();
                    referee.closeConn();
                }
            }
        }catch (SQLException e){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Errore chiusura connessione con il database");
            err.show();
        }
    }
}
