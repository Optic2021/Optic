package com.example.optic.first_ui;

import com.example.optic.app_controllers.RegisterController;
import com.example.optic.bean.Factory;
import com.example.optic.bean.UserBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;

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
    @FXML
    private Label addressLabel;
    @FXML
    private TextField addressField;
    @FXML
    private Label pgNameLabel;
    @FXML
    private TextField pgNameField;
    @FXML
    private Label pgProv;
    @FXML
    private TextField pgProvField;

    private RegisterController registerController;

    public void toLogin() throws IOException {
        this.toView("views/login.fxml");
    }

    @Override
    public void setUserVariables(String username){
        //does np
    }

    public void register() throws SQLException, IOException, ClassNotFoundException {
        registerController = new RegisterController();
        boolean res = false;
        userRB.setUserData(1);
        adminRB.setUserData(2);
        refereeRB.setUserData(3);
        Alert err = new Alert(Alert.AlertType.ERROR);
        int prof = (int) profile.getSelectedToggle().getUserData();
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            err.setContentText("Inserire i dati");
            err.show();
        }else if(password.getText().length() < 4){
            err.setContentText("La password deve contenere almeno 4 caratteri");
            err.show();
        }else if(!(password.getText().equals(confPassword.getText()))){
            err.setContentText("Le password non combaciano");
            err.show();
        }else if(prof == 2 && (addressField.getText().isEmpty() || pgNameField.getText().isEmpty() || pgProv.getText().isEmpty())) {
            err.setContentText("Inserire informazioni campo!");
            err.show();
        } else{
            String name = username.getText();

            res = registerController.isUsernameUsed(username.getText(), prof);
            if(!res) {
                UserBean user = Factory.createUser(prof);
                user.setUsername(username.getText());
                user.setPassword(password.getText());
                user.setVia(addressField.getText());
                user.setNomeC(pgNameField.getText());
                user.setProv(pgProvField.getText());
                String[] arr = {"views/userHomeMap.fxml","views/modPgPage.fxml","views/refCampo.fxml"};
                registerController.userRegister(user, prof);
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setContentText("Registrazione avvenuta con successo!");
                conf.show();
                this.toView(arr[prof-1], name);
            } else {
                registerController.closeConn(prof);
                err.setContentText("Username già utilizzato");
                err.show();
            }
        }
    }

    public void showAddress(){
        addressField.setVisible(true);
        addressLabel.setVisible(true);
        pgNameLabel.setVisible(true);
        pgNameField.setVisible(true);
        pgProv.setVisible(true);
        pgProvField.setVisible(true);
    }

    public void hideAddress(){
        addressField.setVisible(false);
        addressLabel.setVisible(false);
        pgNameLabel.setVisible(false);
        pgNameField.setVisible(false);
        pgProv.setVisible(false);
        pgProvField.setVisible(false);
    }
}
