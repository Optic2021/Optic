package com.example.optic;

import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
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

    public void login() throws Exception {
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
                    res = LoginController.adminLogin(a);
                    view = "views/modPgPage.fxml";
                }
                case 3 ->{
                    RefereeBean r= new RefereeBean();
                    r.setUsername(name);
                    r.setPassword(pw);
                    res = LoginController.refereeLogin(r);
                    view = "views/refCampo.fxml";
                }
                default -> {
                    PlayerBean p = new PlayerBean();
                    p.setUsername(name);
                    p.setPassword(pw);
                    res = LoginController.playerLogin(p);
                    view = "views/userHomeMap.fxml";
                }

            }
            if (res) {
                this.toView(view, name);
            } else {
                LoginController.closeConn(prof);
                err.setContentText("Credenziali errate");
                err.show();
            }
        }
    }

    public void toRegister() throws IOException {
        this.toView("views/register.fxml");
    }
}
