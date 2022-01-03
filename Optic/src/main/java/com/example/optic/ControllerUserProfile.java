package com.example.optic;

import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
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
            PlayerBean player = new PlayerBean();
            player.setUsername(user);
            p = UserProfileAppController.getPlayer(player.getUsername());
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
        if(!urlFacebook.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlFacebook.getText()});
        }
    }

    public void instagram() throws IOException {
        if(!urlInstagram.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlInstagram.getText()});
        }
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

        String desc = description.getText();
        String fb = urlFacebook.getText();
        String ig = urlInstagram.getText();
        if(desc.length() > 200){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("La descrizione supera il limite massimo di 200 caratteri: "+desc.length());
            err.show();
        }else if(fb.length() > 200 || ig.length() > 200){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("L'url supera il limite massimo di 200 caratteri: fb -> "+fb.length()+", ig -> "+ig.length());
            err.show();
        }else {
            //
            //METTERE LA BEAN
            UserProfileAppController.setInfo(user.getText(), desc, fb, ig);

            description.setEditable(false);
            urlFacebook.setEditable(false);
            urlInstagram.setEditable(false);
            modifica.setVisible(false);
            salva.setVisible(false);
        }
    }

    public void toHome(ActionEvent e) throws Exception {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
}
