package com.example.optic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class ControllerUserProfile extends GraphicController{
    @FXML
    private TextArea description;
    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;
   /* @FXML
    private TextField numWhatsapp;*/
    @FXML
    private GridPane modifica;
    @FXML
    private Button salva;
    @FXML
    private Label user;

    @Override
    public void setUserLabel(String user){
        this.user.setText(user);
    }

    public void modify(){
        //Rendo visibile il tasto aggiorna e modificabile la grid di connessione ai social e la description
        description.setEditable(true);
        urlFacebook.setEditable(true);
        urlInstagram.setEditable(true);
       // numWhatsapp.setEditable(true);
        modifica.setVisible(true);
        salva.setVisible(true);

    }

    public void save(){
        //Informazione da salvare nel dao

        String Text1=description.getText();
        String Text2=urlFacebook.getText();
        //String Text3=numWhatsapp.getText();
        String Text4=urlInstagram.getText();

        description.setEditable(false);
        urlFacebook.setEditable(false);
        urlInstagram.setEditable(false);
        //numWhatsapp.setEditable(false);
        modifica.setVisible(false);
        salva.setVisible(false);

    }

    public void toHome(ActionEvent e) throws IOException {
        this.toView("views/userHomeMap.fxml");
    }
}
