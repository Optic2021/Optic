package com.example.optic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerSocialModPG extends GraphicController{
    @FXML
    private Pane idS;
    private double xOffset;
    private double yOffset;

    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;
    @FXML
    private TextField numWhatsapp;

    public void exitSocial(ActionEvent e) {
        Stage social = (Stage)this.idS.getScene().getWindow();
        social.close();
    }

    //CONVENZIONE per quanto riguarda i social con exit esco senza salvare con conferma esco e salvo
    public  void conferma(){
        String text1= urlFacebook.getText();
        String text2= urlFacebook.getText();
        String text3= urlFacebook.getText();
        ActionEvent event=new ActionEvent();
        exitSocial(event);
    }

}
