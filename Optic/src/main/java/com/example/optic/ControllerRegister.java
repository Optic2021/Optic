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

import java.io.IOException;

public class ControllerRegister extends GraphicController {
    @FXML
    private Pane id;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confPassword;
    @FXML
    private Label err2;
    @FXML
    private Label err3;
    @FXML
    private Label err4;
    @FXML
    private ToggleGroup profile;
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
    public void toLogin(ActionEvent e) throws IOException {
        Stage obj = (Stage) id.getScene().getWindow();
        this.toView("login.fxml",obj);
    }

    public void register(ActionEvent e) throws IOException{
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            err4.setVisible(true);
            return;
        }
        System.out.println(password.getText() + " " + confPassword.getText());
        if(password.getText().equals(confPassword.getText()) != true){
            err3.setVisible(true);
            return;
        }
        String name = username.getText();
        String pw = password.getText();
        String confPw = confPassword.getText();

        /*controllo se lo username è già usato comunicando con il DB (tramite Dao)
        String query = "";//cerca nel db il nome e restituisce
        if(username già usato){
            err2.setVisible(true);
            return;
        }*/

        //inserisco i dati nel database

        //controllo credenziali sul DB tramite la Dao
        /*JdbcDao jdbcDao = new JdbcDao();
        boolean flag = jdbcDao.validate(name, pw);*/ //funziona sicuramente dato che si tratta della registrazione

        userRB.setUserData(1);
        adminRB.setUserData(2);
        refereeRB.setUserData(3);
        int prof = (int)profile.getSelectedToggle().getUserData();
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
}
