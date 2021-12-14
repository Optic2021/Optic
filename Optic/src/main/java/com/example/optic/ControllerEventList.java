package com.example.optic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerEventList {
    @FXML
    private Pane id2;

    public void exitListButton(ActionEvent e){
        Stage list = (Stage) id2.getScene().getWindow();
        list.close();
    }
}
