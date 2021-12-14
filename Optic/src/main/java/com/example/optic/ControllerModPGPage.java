package com.example.optic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerModPGPage extends GraphicController {

    //sistema gli id

    @FXML
    private TextArea description;
    //settata non editable
    @FXML
    private Button addPhoto;
    //settata non visible
    @FXML
    private Button aggiorna;
    //settata non visible
    @FXML
    private Button addEvent;
    //settata non visible
    /*
    @FXML
    private GridPane grid;

    @FXML
    private TextField urlFacebook;
    //settata non editable
    @FXML
    private TextField urlInstagram;
    //settata non editable
    @FXML
    private TextField numWhatsapp;
    //settata non editable*/


    @FXML
    private Pane id;
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

    //aggiungi grid con i social come userprofile

    public void modify(ActionEvent e){
        //abilita la modalita modifica
        description.setEditable(true);
        addPhoto.setVisible(true);
        aggiorna.setVisible(true);
        addEvent.setVisible(true);

        /*grid.setVisible(true);
        urlFacebook.setEditable(true);
        urlInstagram.setEditable(true);
        numWhatsapp.setEditable(true);*/

    }

    public void save(ActionEvent e){
        //passare a classe di scrittura in database
        String text1 =description.getText();
        /*String text2 =urlFacebook.getText();
        String text3 =urlInstagram.getText();
        String text4 =numWhatsapp.getText();*/

        description.setEditable(false);
        addPhoto.setVisible(false);
        aggiorna.setVisible(false);
        addEvent.setVisible(false);

        /*urlFacebook.setEditable(false);
        urlInstagram.setEditable(false);
        numWhatsapp.setEditable(false);
        grid.setVisible(false);*/

    }

}
