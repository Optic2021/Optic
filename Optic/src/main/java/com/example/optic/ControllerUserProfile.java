package com.example.optic;

import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;
import com.example.optic.entities.ValutazionePlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.util.ArrayList;

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
    @FXML
    private TableView reviews;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn desc;

    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
        Player p = null;
        try {
            PlayerBean player = new PlayerBean();
            player.setUsername(user);
            this.populateReviewTable(user);
            p = UserProfileAppController.getPlayer(player);
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

    //popolo la lista di review e utilizzo i dati delle valutazioni per il contatore
    public void populateReviewTable(String user){
        PlayerBean player = new PlayerBean();
        player.setUsername(user);
        name.setCellValueFactory(new PropertyValueFactory<>("user"));
        desc.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
       // ArrayList<Valutazione> list = UserProfileAppController.getReviewList(player);
        boolean list = true;
        if(list){
            //ValutazionePlayer val = new ValutazionePlayer(list.get(0).getFk_UsernameP1(),list.get(0).getDescrizione()); //passo chi fa la segnalazione e la descrizione
            ValutazionePlayer val = new ValutazionePlayer("pippo","giocatore scorretto");
            reviews.getItems().add(val);
            ValutazionePlayer val2 = new ValutazionePlayer("marco","Giocatore molto molto molto scorretto!");
            reviews.getItems().add(val2);
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
        modifica.setVisible(true);
        salva.setVisible(true);
    }

    public void save(){
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
            PlayerBean p = new PlayerBean();
            p.setUsername(user.getText());
            p.setDescrizione(desc);
            p.setFb(fb);
            p.setIg(ig);
            UserProfileAppController.setInfo(p);

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