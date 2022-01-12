package com.example.optic;

import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Player;
import com.example.optic.entities.Storico;
import com.example.optic.entities.Valutazione;
import com.example.optic.entities.ValutazionePlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private Label nVal;
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
    private ListView reviews;
    @FXML
    private TableView partite;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn playground;

    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
        Player p = null;
        try {
            PlayerBean player = new PlayerBean();
            player.setUsername(user);
            this.populateReviewList(user);
            this.populateGamesTable(user);
            p = UserProfileAppController.getPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(p != null) {
            //descrizione
            this.description.setText(p.getDescrizione());
            //se il giocatore è valutato positivamente, nome giallo
            if(p.getStato().equals("positivo")){
                this.title.setVisible(true);
            }
            this.urlInstagram.setText(p.getIg());;
            this.urlFacebook.setText(p.getFb());;
        }
    }

    //popolo la lista di review e utilizzo i dati delle valutazioni per il contatore
    public void populateReviewList(String user) throws IOException {
        PlayerBean player = new PlayerBean();
        int numVal = 0;
        int mediaVal = 0;
        int stars = 0;
        player.setUsername(user);
        ArrayList<Valutazione> list = UserProfileAppController.getReviewList(player);
        for(int i = 0; i < list.size(); i++) {
            ValutazionePlayer val = new ValutazionePlayer(list.get(i).getFk_UsernameP1(), list.get(i).getDescrizione()); //passo chi fa la segnalazione e la descrizione
            numVal++;
            mediaVal += list.get(i).getStelle();
            reviews.getItems().add(val.getDescrizione());
        }
        if(numVal > 0) {
            stars = mediaVal / numVal;
            if (stars > 0) {
                //coloro le stelle in base alla valutazione
                this.setStars(stars);
            }
        }
        nVal.setText(Integer.toString(numVal));
    }

    //popolo la tabella con lo storico delle partite del player DA FINIRE!!!!!
    public void populateGamesTable(String user){
        PlayerBean player = new PlayerBean();
        player.setUsername(user);
        date.setCellValueFactory(new PropertyValueFactory<>("data"));
        playground.setCellValueFactory(new PropertyValueFactory<>("campo"));
        //ArrayList<Prenotazione> list = UserProfileAppController.getReviewList(player);
        boolean list = true;
        if(list){
            SimpleDateFormat dataGiornata = new SimpleDateFormat("dd-mm-yyyy");
            Date data = new Date(2022,1,10);
            //ValutazionePlayer val = new ValutazionePlayer(list.get(0).getFk_UsernameP1(),list.get(0).getDescrizione()); //passo chi fa la segnalazione e la descrizione
            Storico partita = new Storico("11-2-2022","SoftAir River");
            partite.getItems().add(partita);
        }
    }

    /*public  void conferma(){
        boolean res = true;
        Alert err = new Alert(Alert.AlertType.ERROR);
        AdminBean bean = new AdminBean();
        bean.setUsername(user.getText());
        bean.setFb(urlFacebook.getText());
        bean.setIg(urlInstagram.getText());
        bean.setWa(numWhatsapp.getText());
        //controllo se gli url sono validi
        if(urlFacebook.getText() != null) {
            if(!(urlFacebook.getText().isEmpty())) {
                if (!(urlFacebook.getText().contains("https://www.facebook.com"))) {
                    res = false;
                    err.setContentText("Url facebook non valido.");
                    err.show();
                } else if (urlFacebook.getText().length() > 200) {
                    res = false;
                    err.setContentText("Url facebook troppo lungo.");
                    err.show();
                }
            }
        }
        if(urlInstagram.getText() != null) {
            if(!(urlInstagram.getText().isEmpty())) {
                if (!(urlInstagram.getText().contains("https://www.instagram.com"))) {
                    res = false;
                    err.setContentText("Url instagram non valido.");
                    err.show();
                } else if (urlInstagram.getText().length() > 200) {
                    res = false;
                    err.setContentText("Url instagram troppo lungo.");
                    err.show();
                }
            }
        }
        if (numWhatsapp.getText() != null){
            if(!(numWhatsapp.getText().isEmpty())) {
                if (numWhatsapp.getText().length() != 10) {
                    res = false;
                    err.setContentText("Numero di telefono non valido");
                    err.show();
                }
            }
        }
        if(res){
            ModPGPageAppController.setAdminSocial(bean);
            ActionEvent event = new ActionEvent();
            exitSocial(event);
        }
    }*/

    public void facebook() throws IOException {
        if(!urlFacebook.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlFacebook.getText()});
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo facebook inserito");
        }
    }

    public void instagram() throws IOException {
        if(!urlInstagram.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlInstagram.getText()});
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo instagram inserito");
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

        /*if(fb.getText() != null) {
            if(!(fb.getText().isEmpty())) {
                if (!(fb.getText().contains("https://www.facebook.com"))) {
        */

        if(desc.length() > 200){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("La descrizione supera il limite massimo di 200 caratteri: "+desc.length());
            err.show();
        }else if(fb.length() > 200 || ig.length() > 200){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("L'url supera il limite massimo di 200 caratteri: fb -> "+fb.length()+", ig -> "+ig.length());
            err.show();
        }else if (!(fb.contains("https://www.facebook.com"))|| (!(ig.contains("https://www.instagram.com")))) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("L'url di uno dei due social non è valido");
            err.show();
        }else{
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