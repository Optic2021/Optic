package com.example.optic.FirstUI;

import com.example.optic.app_controllers.ModPGPageAppController;
import com.example.optic.utilities.ImportUrl;
import com.example.optic.bean.AdminBean;
import com.example.optic.entities.Admin;
import javafx.fxml.FXML;
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

    public void exitSocial() {
        Stage social = (Stage)this.idS.getScene().getWindow();
        social.close();
    }

    //CONVENZIONE per quanto riguarda i social con exit esco senza salvare con conferma esco e salvo
    public  void conferma(){
        boolean res = true;
        AdminBean bean = new AdminBean();
        bean.setUsername(user.getText());
        bean.setFaceb(urlFacebook.getText());
        bean.setInsta(urlInstagram.getText());
        bean.setWhats(numWhatsapp.getText());
        //controllo se gli url sono validi
        res= ImportUrl.controlliUrl(urlInstagram,urlFacebook,numWhatsapp,false);
        if(res){
            ModPGPageAppController.setAdminSocial(bean);
            exitSocial();
        }
    }

}
