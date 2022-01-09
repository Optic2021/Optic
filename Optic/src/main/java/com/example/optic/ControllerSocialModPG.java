package com.example.optic;

import com.example.optic.AppControllers.ModPGPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.entities.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerSocialModPG extends GraphicController{
    @FXML
    private Pane idS;
    @FXML
    private Label user;
    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;
    @FXML
    private TextField numWhatsapp;

    @Override
    public void setUserVariables(String user){
        this.user.setText(user);
        Admin a = null;
        AdminBean bean = new AdminBean();
        bean.setUsername(user);
        a = ModPGPageAppController.getAdmin(bean);
        if(a != null){
            urlFacebook.setText(a.getFb());
            urlInstagram.setText(a.getIg());
            numWhatsapp.setText(a.getWa());
        }
    }

    public void exitSocial(ActionEvent e) {
        Stage social = (Stage)this.idS.getScene().getWindow();
        social.close();
    }

    //CONVENZIONE per quanto riguarda i social con exit esco senza salvare con conferma esco e salvo
    public  void conferma(){
        Alert err = new Alert(Alert.AlertType.ERROR);
        AdminBean bean = new AdminBean();
        bean.setUsername(user.getText());
        bean.setFb(urlFacebook.getText());
        bean.setIg(urlInstagram.getText());
        bean.setWa(numWhatsapp.getText());
        //controllo se gli url sono validi
        if(!(urlInstagram.getText().contains("instagram"))){
            err.setContentText("Url instagram non valido.");
            err.show();
        } else if(!(urlFacebook.getText().contains("facebook"))){
            err.setContentText("Url facebook non valido.");
            err.show();
        } else if(numWhatsapp.getText().length() != 10){
            err.setContentText("Numero di telefono non valido");
            err.show();
        } else if(urlInstagram.getText().length() > 200 || urlInstagram.getText().length() > 200) {
            err.setContentText("Link dei social troppo lunghi");
            err.show();
        }else{
            ModPGPageAppController.setAdminSocial(bean);
            ActionEvent event = new ActionEvent();
            exitSocial(event);
        }
    }

}
