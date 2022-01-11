package com.example.optic;

import com.example.optic.AppControllers.ModPGPageAppController;
import com.example.optic.AppControllers.UserPgPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.lang.Object;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControllerUserPgPage extends GraphicController {
    @FXML
    private Label user;
    @FXML
    private Label campo;
    @FXML
    private Label desc;
    @FXML
    private Label ref;
    @FXML
    private Label address;
    @FXML
    private Label prov;
    @FXML
    private Label adminName;
    @FXML
    private TextArea testoRecensione;

    @FXML
    private TableView table;
    @FXML
    private TableColumn nome;
    @FXML
    private TableColumn recensione;

    @FXML
    private TableView players;
    @FXML
    private TableColumn playerName;
    @FXML
    private TableColumn playerVal;

    @FXML
    private Label idPlay;
    @FXML
    private Label activity;
    @FXML
    private Label date;
    @FXML
    private Label numGiocatori;

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

    @Override
    public void setUserVariables(String string) {
        String [] result = string.split("/");
        String username = result[0];
        String campo = result[1];
        user.setText(username);

        AdminBean admin = null;
        AdminBean playground = null;
        try {
            playground = new AdminBean();
            playground.setNomeCampo(campo);
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
    public void setFirstPlay(String user) throws Exception {
        Giornata play = null;
        UserBean bean = new UserBean();
        //setto la bean con info dell'admin del campo attualmente visualizzato
        bean.setUsername(user);
        try {
            play = UserPgPageAppController.getFirstPlay(bean);
            //controllo se esiste una giornata da poter mostrare
            if (play != null) {
                //mostro informazioni della giornata
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFk_Nome());
                this.populatePlayersTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //recupero la prossima giornata di gioco disponibile
    public void getNextPlay() throws ParseException {
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = date_format.parse(date.getText());
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            playBean.setData(cal);
            playBean.setAdmin(adminName.getText());
            play = UserPgPageAppController.getNextPlay(playBean);
            if (play != null) {
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFk_Nome());
                this.populatePlayersTable();
            }
        }catch (ParseException | IOException e){
            e.printStackTrace();
        }
    }

    //recupero la giornata di gioco precedente a quella mostrata
    public void getLastPlay(){
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = date_format.parse(date.getText());
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            playBean.setData(cal);
            playBean.setAdmin(adminName.getText());
            play = UserPgPageAppController.getLastPlay(playBean);
            if (play != null) {
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFk_Nome());
                this.populatePlayersTable();
            }
        }catch (ParseException | IOException e){
            e.printStackTrace();
        }
    }

    public void populatePlayersTable() throws IOException {
        GiornataBean playBean = new GiornataBean();
        players.getItems().clear();
        playerName.setCellValueFactory(new PropertyValueFactory<>("username"));
        playerVal.setCellValueFactory(new PropertyValueFactory<>("stelle"));
        Player p = new Player();
        playBean.setIdPlay(Integer.parseInt(idPlay.getText()));
        ArrayList<Player> list = UserPgPageAppController.getPlayersList(playBean);
        for(int i = 0; i < list.size(); i++) {
            p = list.get(i);
            players.getItems().add(p);
        }
        numGiocatori.setText(Integer.toString(list.size()));
    }

    public void toHome(ActionEvent e) throws Exception {
        this.toView("views/userHomeMap.fxml",user.getText());
    }

    public void starEnter(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 2: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 3: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 4: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(229,190,51));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 5: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(229,190,51));
                star5.setTextFill(Color.rgb(229,190,51));
                break;
            default:star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(28,28,28));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
        }
    }
    public void starExit(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 1: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(28,28,28));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 2: star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 3: star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 4: star5.setTextFill(Color.rgb(28,28,28));
                break;
            default:;
        }
    }

    public void populateReviewTable(AdminBean admin){

        ArrayList<Valutazione> list = UserPgPageAppController.reviewList(admin);
        nome.setCellValueFactory(new PropertyValueFactory<>("fk_UsernameP1"));
        recensione.setCellValueFactory(new PropertyValueFactory<>("Descrizione"));
        int k = list.size();
        int i = 0;
        Valutazione val;

        while (i < k) {
            val = list.get(i);
            table.getItems().add(val);
            i++;
        }
    }

    public void setCampo(AdminBean admin){
        campo.setText(admin.getNomeCampo());
        desc.setText(admin.getDescrizione());
        adminName.setText(admin.getUsername());
        ref.setText(admin.getReferee());
        address.setText(admin.getVia());
        prov.setText(admin.getProvincia());
    }

    public void review() throws IOException {
        String t;
        int starN=0;

        if(star1.getTextFill().equals(Color.rgb(229,190,51)) && star2.getTextFill().equals(Color.rgb(28,28,28))){
            starN=1;
        }else if (star2.getTextFill().equals(Color.rgb(229,190,51)) && star3.getTextFill().equals(Color.rgb(28,28,28))){
            starN=2;
        } else if (star3.getTextFill().equals(Color.rgb(229,190,51)) && star4.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=3;
        } else if (star4.getTextFill().equals(Color.rgb(229,190,51)) && star5.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=4;
        }else{
            starN=5;
        }

        ValutazioneBean valutazione = new ValutazioneBean();

        valutazione.setRecensione(testoRecensione.getText());
        valutazione.setCampo(campo.getText());
        valutazione.setStelle(starN);
        valutazione.setUsernameP1(user.getText());

        UserPgPageAppController.saveReview(valutazione);
        AdminBean admin=new AdminBean();
        admin.setNomeCampo(campo.getText());
        table.getItems().clear();
        populateReviewTable(admin);
    }

    public void paginaProfilo(/*MouseEvent e*/){
        try {
            //this.toView("views/userProfile.fxml",user.getText());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void tableview(MouseEvent event) throws IOException {
        Node node=((Node)event.getTarget()).getParent();
        Valutazione val = (Valutazione) table.getSelectionModel().getSelectedItem();
        try {
            if (user.getText().equals(val.getFk_UsernameP1())){
                toView("views/userProfile.fxml", user.getText());
            }else{
                System.out.println("Entro qui");
                toView("views/userViewProfile.fxml",val.getFk_UsernameP1(), user.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}