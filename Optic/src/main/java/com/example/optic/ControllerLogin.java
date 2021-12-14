package com.example.optic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ControllerLogin extends GraphicController {
    @FXML
    private Pane id;
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
                case 2 -> view = "modPgPage.fxml";
                case 3 -> view = "refCampo.fxml";
                default -> view = "userHomeMap.fxml";
            }
            Stage obj = (Stage) id.getScene().getWindow();
            this.toView(view,obj);
       // }

    }

    public void toRegister(ActionEvent e) throws IOException {
        Stage obj = (Stage) id.getScene().getWindow();
        this.toView("register.fxml", obj);
    }
}
