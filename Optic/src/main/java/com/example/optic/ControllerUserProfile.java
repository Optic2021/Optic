package com.example.optic;

import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ControllerUserProfile extends GraphicController{
    @FXML
    private Label star1;
    @FXML
    private Label star2;
    @FXML
    private Label star3;
    @FXML
    private Label star4;
    @FXML
    private Label star5;
    @FXML
    private Label title;
    @FXML
    private TextArea description;
    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;
    @FXML
    private GridPane modifica;
    @FXML
    private Button salva;
    @FXML
    private Label user;

    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
        Player p = null;
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            p = player.getPlayer(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(p != null) {
            //descrizione
            this.description.setText(p.getDescrizione());
            //coloro le stele in base alla valutazione
            this.setStars(p.getValutazione());
            //se il giocatore è valutato positivamente, nome giallo
            if(p.getStato().equals("positivo")){
                this.title.setVisible(true);
            }
            this.urlInstagram.setText(p.getIg());;
            this.urlFacebook.setText(p.getFb());;
        }
    }

    public void facebook() throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome "+this.urlFacebook.getText()});
    }

    public void instagram() throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome "+this.urlInstagram.getText()});
    }

    private void setStars(int stars){
        switch (stars){
            case 1: star1.setVisible(true);
                    break;
            case 2: star1.setVisible(true);
                    star2.setVisible(true);
                    break;
            case 3: star1.setVisible(true);
                    star2.setVisible(true);
                    star3.setVisible(true);
                    break;
            case 4: star1.setVisible(true);
                    star2.setVisible(true);
                    star3.setVisible(true);
                    star4.setVisible(true);
                    break;
            case 5: star1.setVisible(true);
                    star2.setVisible(true);
                    star3.setVisible(true);
                    star4.setVisible(true);
                    star5.setVisible(true);
                    break;
            default:
        }
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

    public void toHome(ActionEvent e) throws Exception {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
}
