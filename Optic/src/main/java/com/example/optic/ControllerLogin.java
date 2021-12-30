package com.example.optic;

import com.example.optic.AppControllers.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class ControllerLogin extends GraphicController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label err1;
    @FXML
    private Label err2;
    @FXML
    private ToggleGroup profileL;
    @FXML
    private RadioButton userRB;
    @FXML
    private RadioButton refereeRB;
    @FXML
    private RadioButton adminRB;

    public void login(ActionEvent e) throws Exception {
        boolean res = false;
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            err1.setVisible(true);
            return;
        }
        String name = username.getText();
        String pw = password.getText();

        userRB.setUserData(1);
        adminRB.setUserData(2);
        refereeRB.setUserData(3);
        int prof = (int)profileL.getSelectedToggle().getUserData();
        String view;
        switch(prof){
            case 2 -> view = "views/modPgPage.fxml";
            case 3 -> view = "views/refCampo.fxml";
            default -> {
                /*PlayerBean player = new PlayerBean();
                   player.setUsername(name);
                   player.setPassword(pw);*/

                res = LoginController.playerLogin(name,pw);
                view = "views/userHomeMap.fxml";
            }

        }
        if(res){
            this.toView(view,name); //da usare effettivamente
            //this.toView(view);
        }else{
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Credenziali errate");
            err.show();
            err2.setVisible(true);
        }
    }

    public void toRegister(ActionEvent e) throws IOException {
        this.toView("views/register.fxml");
    }
}
