package com.example.optic;

import com.example.optic.app_controllers.UserPgPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;

import com.example.optic.utilities.ImportStar;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ControllerUserPgPage extends GraphicController {
    @FXML private Label user;
    @FXML private Label campo;
    @FXML private Label desc;
    @FXML private Label ref;
    @FXML private Label address;
    @FXML private Label prov;
    @FXML private Label adminName;
    @FXML private TextArea testoRecensione;
    @FXML private TableView table;
    @FXML private TableColumn nome;
    @FXML private TableColumn recensione;
    @FXML private TableView players;
    @FXML private TableColumn playerName;
    @FXML private TableColumn playerVal;
    @FXML private Label idPlay;
    @FXML private Label activity;
    @FXML private Label date;
    @FXML private Label numGiocatori;
    //stelle della recensione
    @FXML private Label star1;
    @FXML private Label star2;
    @FXML private Label star3;
    @FXML private Label star4;
    @FXML private Label star5;
    //stelle valutazione del campo
    @FXML private Label star11;
    @FXML private Label star22;
    @FXML private Label star33;
    @FXML private Label star44;
    @FXML private Label star55;
    @FXML private Label fb;
    @FXML private Label ig;
    @FXML private Label wa;
    @FXML private Button book;

    String format="yyyy-MM-dd";

    @Override
    public void setUserVariables(String string) {
        String [] result = string.split("/");
        String username = result[0];
        String field = result[1];
        user.setText(username);

        AdminBean admin = null;
        AdminBean playground = null;
        try {
            playground = new AdminBean();
            playground.setNomeCampo(field);
            admin = UserPgPageAppController.getCampoInfo(playground);
            populateReviewTable(playground);
            this.setFirstPlay(admin.getUsername());
        }catch(Exception a){
            a.printStackTrace();
        }
        try {
            setCampo(admin);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //setto la prima giornata di gioco disponibile
    public void setFirstPlay(String user) {
        Giornata play = null;
        UserBean bean = new UserBean();
        //setto la bean con info dell'admin del campo attualmente visualizzato
        bean.setUsername(user);
        try {
            play = UserPgPageAppController.getFirstPlay(bean);
            //controllo se esiste una giornata da poter mostrare
            if (play != null) {
                //mostro informazioni della giornata
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFk_Nome());
                //controllo se la data è disponibile per la prenotazione
                this.isDateValid();
                this.populatePlayersTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //recupero la prossima giornata di gioco disponibile
    public void getNextPlay() {
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if(!(idPlay.getText().isEmpty())) {
            try {
                Date data = dateFormat.parse(date.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                playBean.setData(cal);
                playBean.setAdmin(adminName.getText());
                play = UserPgPageAppController.getNextPlay(playBean);
                if (play != null) {
                    idPlay.setText(Integer.toString(play.getIdGiornata()));
                    date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                    activity.setText(play.getFk_Nome());
                    //controllo se la data è disponibile per la prenotazione
                    this.isDateValid();
                    this.populatePlayersTable();
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    //recupero la giornata di gioco precedente a quella mostrata
    public void getLastPlay(){
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if(!(idPlay.getText().isEmpty())) {
            try {
                Date data = dateFormat.parse(date.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                playBean.setData(cal);
                playBean.setAdmin(adminName.getText());
                play = UserPgPageAppController.getLastPlay(playBean);
                if (play != null) {
                    idPlay.setText(Integer.toString(play.getIdGiornata()));
                    date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                    activity.setText(play.getFk_Nome());
                    //controllo se la data è disponibile per la prenotazione
                    this.isDateValid();
                    this.populatePlayersTable();
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void isDateValid() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date datePlay = dateFormat.parse(date.getText());
        boolean bool = datePlay.toInstant().isBefore(Instant.now());
        book.setVisible(bool);
    }
    public void bookPlay() throws IOException {
        UserBean bean = new UserBean();
        GiornataBean playBean = new GiornataBean();
        bean.setUsername(user.getText());
        playBean.setIdPlay(Integer.parseInt(idPlay.getText()));
        if(UserPgPageAppController.isPlayerBooked(bean,playBean)){
            Alert conf = new Alert(Alert.AlertType.ERROR);
            conf.setContentText("Sei già prenotato!");
            conf.show();
        }else{
            UserPgPageAppController.bookPlay(bean,playBean);
            this.populatePlayersTable();
        }
    }
    public void populatePlayersTable() throws IOException {
        GiornataBean playBean = new GiornataBean();
        players.getItems().clear();
        playerName.setCellValueFactory(new PropertyValueFactory<>("username"));
        playerVal.setCellValueFactory(new PropertyValueFactory<>("stelle"));
        Player p;
        playBean.setIdPlay(Integer.parseInt(idPlay.getText()));
        List<Player> list = UserPgPageAppController.getPlayersList(playBean);
        for(int i = 0; i < list.size(); i++) {
            p = list.get(i);
            players.getItems().add(p);
        }
        numGiocatori.setText(Integer.toString(list.size()));
    }
    public void toHome() throws Exception {
        this.toView("views/userHomeMap.fxml", user.getText());
    }

    public void starEnter(MouseEvent e){
        ImportStar.starEnter(e,star1,star2,star3,star4,star5);
    }
    public void starExit(MouseEvent e){
        ImportStar.starEnter(e,star1,star2,star3,star4,star5);
    }
    private void setStars(int stars){
        ImportStar.setStars(stars,star11,star22,star33,star44,star55);
    }

    public void populateReviewTable(AdminBean admin){
        int numVal = 0;
        int mediaVal = 0;
        int stars = 0;
        List<Valutazione> list = UserPgPageAppController.reviewList(admin);
        nome.setCellValueFactory(new PropertyValueFactory<>("fk_UsernameP1"));
        recensione.setCellValueFactory(new PropertyValueFactory<>("Descrizione"));
        int k = list.size();
        int i = 0;
        Valutazione val;
        while (i < k) {
            val = list.get(i);
            numVal++;
            mediaVal += list.get(i).getStelle();
            table.getItems().add(val);
            i++;
        }
        if(numVal > 0) {
            stars = mediaVal / numVal;
            if (stars > 0) {
                //coloro le stelle in base alla valutazione
                this.setStars(stars);
            }
        }
    }

    public void setCampo(AdminBean admin){
        campo.setText(admin.getNomeCampo());
        desc.setText(admin.getDescrizione());
        adminName.setText(admin.getUsername());
        ref.setText(admin.getReferee());
        address.setText(admin.getVia());
        prov.setText(admin.getProvincia());
        fb.setText(admin.getFb());
        ig.setText(admin.getIg());
        wa.setText(admin.getWa());
    }

    public void review() throws IOException {
        int starN = 0;
        starN= ImportStar.getStarN(star1,star2,star3,star4,star5);
        ValutazioneBean valutazione = new ValutazioneBean();
        valutazione.setRecensione(testoRecensione.getText());
        valutazione.setRiceve(campo.getText());
        valutazione.setStelle(starN);
        valutazione.setUsernameP1(user.getText());
        UserPgPageAppController.saveReview(valutazione);
        AdminBean admin=new AdminBean();
        admin.setNomeCampo(campo.getText());
        table.getItems().clear();
        populateReviewTable(admin);
    }

    public void tableview() throws IOException {
        Valutazione val = (Valutazione) table.getSelectionModel().getSelectedItem();
        try {
            if (user.getText().equals(val.getFk_UsernameP1())){
                toView("views/userProfile.fxml", user.getText());
            }else{
                toView("views/userViewProfile.fxml",val.getFk_UsernameP1(), user.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tableview2() {
        Player player = (Player) players.getSelectionModel().getSelectedItem();
        try {
            if (user.getText().equals(player.getUsername())){
                toView("views/userProfile.fxml", user.getText());
            }else{
                toView("views/userViewProfile.fxml",player.getUsername(), user.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toFacebook() {
        if (fb.getText() != null) {
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + fb.getText()});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo facebook inserito");
        }
    }
    public void toInstagram() {
        if (ig.getText() != null) {
            try {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + ig.getText()});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo instagram inserito");
        }
    }

    public void toWhatsapp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(wa.getText()==null){
            alert.setContentText("Numero whatsapp non presente");
        }else {
            alert.setContentText("Numero whatsapp: " + wa.getText());
        }
        alert.show();
    }
}