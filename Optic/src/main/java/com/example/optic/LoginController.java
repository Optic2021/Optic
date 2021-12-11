package com.example.optic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Pane id;
    Stage obj = (Stage) id.getScene().getWindow();

    private double xOffset = 0;
    private double yOffset = 0;


    public void exitButton(ActionEvent e){
        Platform.exit();
    }
    public void reduceButton(ActionEvent e){
        obj.setIconified(true);
    }
    public void drag(MouseEvent e){
        obj.setX(e.getScreenX() + xOffset);
        obj.setY(e.getScreenY() + yOffset);
    }

    public void toRegister(ActionEvent e){
        Parent pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("register.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Scene scene = new Scene( pane );
            obj.setScene(scene);
    }
    
}
