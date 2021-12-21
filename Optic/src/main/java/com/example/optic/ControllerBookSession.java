package com.example.optic;

import javafx.event.ActionEvent;
import java.io.IOException;

public class ControllerBookSession extends GraphicController {

    public void toProfile(ActionEvent e) throws IOException {
        this.toView("views/userProfile.fxml");
    }
    public void toHome(ActionEvent e) throws IOException {
        this.toView("views/userHomeMap.fxml");
    }
    public void toLogin(ActionEvent e) throws IOException {
        this.toView("views/login.fxml");
    }
}
