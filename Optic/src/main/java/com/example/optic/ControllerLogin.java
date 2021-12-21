package com.example.optic;

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

    public void login(ActionEvent e) throws IOException{
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            err1.setVisible(true);
            return;
        }
        String name = username.getText();
        String pw = password.getText();
        //controllo credenziali sul DB tramite la Dao
        /*JdbcDao jdbcDao = new JdbcDao();
        boolean flag = jdbcDao.validate(name, pw);*/

       // if (!flag) {
       //     err2.setVisible(true);
       // } else {
            userRB.setUserData(1);
            adminRB.setUserData(2);
            refereeRB.setUserData(3);
            int prof = (int)profileL.getSelectedToggle().getUserData();
            String view;
            switch(prof){
                case 2 -> view = "views/modPgPage.fxml";
                case 3 -> view = "views/refCampo.fxml";
                default -> view = "views/userHomeMap.fxml";
            }
            this.toView(view);
       // }

    }

    public void toRegister(ActionEvent e) throws IOException {
        this.toView("views/register.fxml");
    }
}
