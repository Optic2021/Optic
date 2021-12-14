package com.example.optic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerRefCampo extends GraphicController{
    @FXML
    private Pane id;
    private double xOffset = 0;
    private double yOffset = 0;

    public void exitButton(ActionEvent e){
        Platform.exit();
    }
    public void reduceButton(ActionEvent e){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setIconified(true);
    }
    public void drag(MouseEvent e){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setX(e.getScreenX() + xOffset);
        obj.setY(e.getScreenY() + yOffset);
    }
    public void toLogin(ActionEvent e) throws IOException {
        Stage obj = (Stage) id.getScene().getWindow();
        this.toView("login.fxml",obj);
    }
}
