package com.example.optic;

import com.example.optic.AppControllers.RegisterController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class ControllerRegister extends GraphicController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confPassword;
    @FXML
    private ToggleGroup profile;
    @FXML
    private RadioButton userRB;
    @FXML
    private RadioButton refereeRB;
    @FXML
    private RadioButton adminRB;

    public void toLogin(ActionEvent e) throws IOException {
        this.toView("com/example/optic/views/login.fxml");
    }

    public void register(ActionEvent e) throws Exception {
        boolean res = false;
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Inserire i dati");
            err.show();
        }else if(password.getText().length() < 4){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("La password deve contenere almeno 4 caratteri");
            err.show();
        }else if(password.getText().equals(confPassword.getText()) != true){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Le password non combaciano");
            err.show();
        }else {
            String name = username.getText();
            String pw = password.getText();

            userRB.setUserData(1);
            adminRB.setUserData(2);
            refereeRB.setUserData(3);
            int prof = (int) profile.getSelectedToggle().getUserData();
            String view = "views/register.fxml";
            switch (prof) {
                case 2 -> view = "views/modPgPage.fxml";
                case 3 -> view = "views/refCampo.fxml";
                default -> {
                    PlayerBean p = new PlayerBean();
                    p.setUsername(name);
                    p.setPassword(pw);
                    res = RegisterController.isUsernameUsed(p, 1);
                    if (!res) {
                        RegisterController.playerRegister(p);
                        view = "views/userHomeMap.fxml";
                    }
                }

            }
            if (!res) {
                Alert err = new Alert(Alert.AlertType.CONFIRMATION);
                err.setContentText("Registrazione avvenuta con successo!");
                err.show();
                this.toView(view, name);
            } else {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setContentText("Username già utilizzato");
                err.show();
            }
        }
    }
}
