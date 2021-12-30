package com.example.optic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class ControllerBookSession extends GraphicController {
    @FXML
    private Label user;

    @Override
    public void setUserVariables(String user){
        this.user.setText(user);
    }
    public void toProfile(ActionEvent e) throws Exception {
        this.toView("views/userProfile.fxml",user.getText());
    }
    public void toHome(ActionEvent e) throws IOException {
        this.toView("views/userHomeMap.fxml");
    }
    public void toLogin(ActionEvent e) throws IOException {
        this.toView("views/login.fxml");
    }
}
