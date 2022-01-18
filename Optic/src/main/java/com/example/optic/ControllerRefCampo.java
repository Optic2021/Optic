package com.example.optic;

import com.example.optic.app_controllers.RefCampoController;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportGetPlay;
import com.example.optic.utilities.ImportList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ControllerRefCampo extends GraphicController{
    @FXML private Pane id;
    @FXML private Label user;
    @FXML private Label userType;
    @FXML private TextArea desc;
    @FXML private Label address;
    @FXML private Label prov;
    @FXML private Label admin;
    @FXML private Label title;
    @FXML private Label activity;
    @FXML private Label idPlay;
    @FXML private Label date;
    @FXML private TableView players;
    @FXML private Label numGiocatori;
    @FXML private TableColumn playerName;
    @FXML private TableColumn playerVal;
    @FXML private Label selectedPlayer;
    @FXML private TextArea reportDesc;

    @FXML private Label numWhatsapp;
    @FXML private Label urlInstagram;
    @FXML private Label urlFacebook;

    private String format="yyyy-MM-dd";

    @Override
    public void setUserVariables(String user) {
        this.user.setText(user);
        Admin a = null;
        UserBean bean = new UserBean();
        bean.setUsername(user);
        a = RefCampoController.getAdminFromRef(bean);
        if(a == null){
            Alert err = new Alert(Alert.AlertType.WARNING);
            err.setContentText("Non sei collegato a nessun campo!");
            err.show();
        }else{
            this.title.setText(a.getNomeC());
            this.desc.setText(a.getDescrizioneC());
            this.address.setText(a.getVia());
            this.prov.setText(a.getProvincia());
            this.admin.setText(a.getUsername());
            this.setFirstPlay(admin.getText());
        }
    }

    //setto la prima giornata di gioco disponibile
    public void setFirstPlay(String user){
        Giornata play = null;
        UserBean bean = new UserBean();
        //setto la bean con info dell'admin del campo attualmente visualizzato
        bean.setUsername(user);
        try {
            play = RefCampoController.getFirstPlay(bean);
            //controllo se esiste una giornata da poter mostrare
            if (play != null) {
                //mostro informazioni della giornata
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFkNome());
                //controllo se la data è disponibile per la prenotazione
                this.populatePlayersTable();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Errore caricamento giocate");
            alert.show();
        }
    }

    //recupero la prossima giornata di gioco disponibile
    public void getNextPlay() throws IOException {
        ImportGetPlay.getPlay(idPlay, date, admin, activity,0);
        this.populatePlayersTable();
    }

    //recupero la giornata di gioco precedente a quella mostrata
    public void getLastPlay() throws IOException {
        ImportGetPlay.getPlay(idPlay, date, admin, activity,1);
        this.populatePlayersTable();
    }

    public void populatePlayersTable() throws IOException {
        GiornataBean playBean = new GiornataBean();
        players.getItems().clear();
        playerName.setCellValueFactory(new PropertyValueFactory<>("username"));
        playerVal.setCellValueFactory(new PropertyValueFactory<>("stelle"));
        Player p = null;
        playBean.setIdPlay(Integer.parseInt(idPlay.getText()));
        List<Player> list = RefCampoController.getPlayersList(playBean);
        for(int i = 0; i < list.size(); i++) {
            p = list.get(i);
            players.getItems().add(p);
        }
        numGiocatori.setText(Integer.toString(list.size()));
    }

    public void tableview2() throws IOException {
        Player player = (Player) players.getSelectionModel().getSelectedItem();
        //Setto username report
        String username=player.getUsername();
        selectedPlayer.setText("Giocatore : "+username);
    }

    public void report() {
        ReportBean rep=new ReportBean();
        rep.setMotivazione(reportDesc.getText());
        String[] giocatore = selectedPlayer.getText().split(" ");
        rep.setPlayer(giocatore[2]);
        rep.setReferee(user.getText());
        RefCampoController.saveReport(rep);
    }

    public void eventList() {
        ImportList.eventList(userType,id);
    }

    public void toLogin() throws IOException {
        RefCampoController.closeConn();
        this.toView("views/login.fxml");
    }

    /*DA AGGIUNGERE HBOX CON IG WA FB*/

    public void toInstagram(){
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlInstagram.getText()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toFacebook(){
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlFacebook.getText()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toWhatsapp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(numWhatsapp.getText().isEmpty()||numWhatsapp.getText()==null){
            alert.setContentText("Numero whatsapp non presente");
        }else {
            alert.setContentText("Numero whatsapp: " + numWhatsapp.getText());
        }
        alert.show();
    }
}
