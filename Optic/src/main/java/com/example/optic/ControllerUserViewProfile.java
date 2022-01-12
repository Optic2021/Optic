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
import java.util.StringTokenizer;

public class ControllerUserViewProfile extends GraphicController{
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
    private Label user;
    @FXML
    private  Label profile;
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
        String [] app =user.split(" ");
        String profile=app[0];
        String viewer=app[1];
        System.out.println("Sono l utente "+viewer+" Sto guardando "+profile);

        this.profile.setText("Profile: "+profile);
        this.user.setText(viewer);
        Player p = null;
        try {
            PlayerBean player = new PlayerBean();
            player.setUsername(profile);
            this.populateReviewList(profile);
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

    public void toHome(ActionEvent e) throws Exception {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
}
