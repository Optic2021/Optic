package com.example.optic.first_ui;

import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
import com.mysql.cj.log.Log;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class ControllerLogin extends GraphicController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ToggleGroup profileL;
    @FXML
    private RadioButton userRB;
    @FXML
    private RadioButton refereeRB;
    @FXML
    private RadioButton adminRB;

    private LoginController loginController;
    @Override
    public void setUserVariables(String username){
        //does np
    }

    public void login() throws IOException {
        loginController = new LoginController();
        boolean res = false;
        Alert err = new Alert(Alert.AlertType.ERROR);
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            err.setContentText("Inserire i dati");
            err.show();
        }else {
            String name = username.getText();
            String pw = password.getText();

            userRB.setUserData(1);
            adminRB.setUserData(2);
            refereeRB.setUserData(3);

            int prof = (int) profileL.getSelectedToggle().getUserData();
            String view;
            switch (prof) {
                case 2 -> {
                    AdminBean a = new AdminBean();
                    a.setUsername(name);
                    a.setPassword(pw);
                    res = loginController.adminLogin(a);
                    view = "views/modPgPage.fxml";
                }
                case 3 ->{
                    RefereeBean r= new RefereeBean();
                    r.setUsername(name);
                    r.setPassword(pw);
                    res = loginController.refereeLogin(r);
                    view = "views/refCampo.fxml";
                }
                default -> {
                    PlayerBean p = new PlayerBean();
                    p.setUsername(name);
                    p.setPassword(pw);
                    res = loginController.playerLogin(p);
                    view = "views/userHomeMap.fxml";
                }

            }
            if (res) {
                this.toView(view, name);
            } else {
                loginController.closeConn(prof);
                err.setContentText("Credenziali errate");
                err.show();
            }
        }
    }

    public void toRegister() throws IOException {
        this.toView("views/register.fxml");
    }
}
