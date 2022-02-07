package com.example.optic.first_ui;

import com.example.optic.Optic;
import com.example.optic.app_controllers.ModPGPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.*;
import com.example.optic.utilities.ImportList;
import com.example.optic.utilities.ImportStar;
import com.example.optic.utilities.NotARefereeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ControllerModPGPage extends GraphicController {
    @FXML private Label user;
    @FXML private Label userType;
    @FXML private Label title;
    @FXML private Label starMPG1;
    @FXML private Label starMPG2;
    @FXML private Label starMPG3;
    @FXML private Label starMPG4;
    @FXML private Label starMPG5;
    @FXML private TextArea description;
    //settata non editable
    //settata non visible
    @FXML private Button aggiorna;
    //settata non visible
    @FXML private Button addPlay;
    //settata non visible
    @FXML private Label urlFacebook;
    //settata non editable
    @FXML private Label urlInstagram;
    //settata non editable
    @FXML private Label numWhatsapp;
    @FXML private Label refName;
    @FXML private Label prov;
    @FXML private Label address;
    //settata non editable
    @FXML private Pane id;
    @FXML private TextField ref;
    @FXML private ListView reviews;
    @FXML private ComboBox eventBox;
    @FXML private DatePicker datePicker;
    @FXML private Label nPlayers;
    @FXML private Label activity;
    @FXML private Label date;
    @FXML private Label idPlay;
    @FXML private TableView players;
    @FXML private TableColumn playerName;
    @FXML private TableColumn playerVal;

    private String format="yyyy-MM-dd";

    public void toLogin() throws IOException {
        ModPGPageAppController.closeConn();
        this.toView("views/login.fxml");
    }

    @Override
    public void setUserVariables(String user) {
        this.user.setText(user);
        Admin a = new Admin();
        try {
            AdminBean admin = new AdminBean();
            admin.setUsername(user);
            this.populateReviewList(user);
            this.playgroundReferee(user);
            a = ModPGPageAppController.getAdmin(admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(a != null) {
            this.title.setText(a.getNomeC());
            this.address.setText(a.getVia());
            this.prov.setText(a.getProvincia());
            this.description.setText(a.getDescrizioneC());
            this.description.setWrapText(true);
            this.urlInstagram.setText(a.getIg());
            this.urlFacebook.setText(a.getFb());
            this.numWhatsapp.setText(a.getWa());
            try {
                this.setFirstPlay(a.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //setto la prima giornata di gioco disponibile
    public void setFirstPlay(String user) throws IOException {
        Giornata play = null;
        UserBean bean = new UserBean();
        bean.setUsername(user);
        try {
            play = ModPGPageAppController.getFirstPlay(bean);
            //controllo se esiste una giornata da poter mostrare
            if (play != null) {
                //mostro informazioni della giornata
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFkNome());
                this.populatePlayersTable();
            }else{

                this.populateEventBox();
                eventBox.setVisible(true);
                datePicker.setVisible(true);
                addPlay.setVisible(true);
                idPlay.setVisible(false);
                date.setVisible(false);
                activity.setVisible(false);
                nPlayers.setVisible(false);
                players.setVisible(false);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //recupero la prossima giornata di gioco disponibile
    //le generalizzo ma non sono sicuro sia corretto
    public void getNextPlay() throws ParseException {
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if(!(idPlay.getText().isEmpty())) {
            try {
                //crea nuova giornata
                Date data = dateFormat.parse(date.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                playBean.setData(cal);
                playBean.setAdmin(user.getText());
                play = ModPGPageAppController.getNextPlay(playBean);
                if (play != null) {
                    idPlay.setText(Integer.toString(play.getIdGiornata()));
                    date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                    activity.setText(play.getFkNome());
                    this.populatePlayersTable();
                } else {
                    eventBox.setVisible(true);
                    this.populateEventBox();
                    datePicker.setVisible(true);
                    addPlay.setVisible(true);
                    idPlay.setVisible(false);
                    date.setVisible(false);
                    activity.setVisible(false);
                    nPlayers.setVisible(false);
                    players.setVisible(false);
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }else{
            eventBox.setVisible(true);
            try {
                this.populateEventBox();
            } catch (IOException e) {
                e.printStackTrace();
            }
            datePicker.setVisible(true);
            addPlay.setVisible(true);
            idPlay.setVisible(false);
            date.setVisible(false);
            activity.setVisible(false);
            nPlayers.setVisible(false);
            players.setVisible(false);
        }
    }

    //recupero la giornata di gioco precedente a quella mostrata
    public void getLastPlay(){
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if(!(idPlay.getText().isEmpty())) {
            try {
                if (!(activity.getText().isEmpty()) && eventBox.isVisible()) {
                    eventBox.setVisible(false);
                    datePicker.setVisible(false);
                    addPlay.setVisible(false);
                    idPlay.setVisible(true);
                    date.setVisible(true);
                    activity.setVisible(true);
                    nPlayers.setVisible(true);
                    players.setVisible(true);
                } else {
                    Date data = dateFormat.parse(date.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data);
                    playBean.setData(cal);
                    playBean.setAdmin(user.getText());
                    play = ModPGPageAppController.getLastPlay(playBean);
                    if (play != null) {
                        idPlay.setText(Integer.toString(play.getIdGiornata()));
                        date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                        activity.setText(play.getFkNome());
                        this.populatePlayersTable();
                    }
                    eventBox.setVisible(false);
                    datePicker.setVisible(false);
                    addPlay.setVisible(false);
                    idPlay.setVisible(true);
                    date.setVisible(true);
                    activity.setVisible(true);
                    nPlayers.setVisible(true);
                    players.setVisible(true);
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void populateReviewList(String user) throws IOException {
        AdminBean admin = new AdminBean();
        int numVal = 0;
        int mediaVal = 0;
        int stars = 0;
        admin.setUsername(user);
        List<Valutazione> list = ModPGPageAppController.getReviewList(admin);
        for(int i = 0; i < list.size(); i++) {
            numVal++;
            mediaVal += list.get(i).getStelle();
            reviews.getItems().add(list.get(i).getFormattedText());
        }
        if(numVal > 0) {
            stars = mediaVal / numVal;
            if (stars > 0) {
                //coloro le stelle in base alla valutazione
                this.setStars(stars);
            }
        }
    }

    public void populatePlayersTable() throws IOException {
        GiornataBean playBean = new GiornataBean();
        players.getItems().clear();
        playerName.setCellValueFactory(new PropertyValueFactory<>("username"));
        playerVal.setCellValueFactory(new PropertyValueFactory<>("stelle"));
        Player p;
        playBean.setIdPlay(Integer.parseInt(idPlay.getText()));
        List<Player> list = ModPGPageAppController.getPlayersList(playBean);
        for(int i = 0; i < list.size(); i++) {
            p = list.get(i);
            players.getItems().add(p);
        }
        nPlayers.setText(Integer.toString(list.size()));
    }

    public void populateEventBox() throws IOException {
        eventBox.getItems().clear();
        List<Event> list = ModPGPageAppController.getEventList();
        for(int i = 0; i < list.size();i++){
            eventBox.getItems().add(list.get(i).getNome());
        }
    }

    public void insertPlay() throws ParseException, IOException {
        //controllo se sono stati inseriti i dati della giornata da inserire
        Alert err = new Alert(Alert.AlertType.ERROR);
        if(eventBox.getSelectionModel().isEmpty() || datePicker.getValue() == null){
            err.setContentText("Inserire data e tipo di attività.");
            err.show();
        }else if(datePicker.getValue().isBefore(LocalDate.now())) {
            err.setContentText("Impossibile inserire una data già passata.");
            err.show();
        }else{
            GiornataBean playBean = new GiornataBean();
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date dataPlay = dateFormat.parse(datePicker.getValue().toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataPlay);
            playBean.setEvento(eventBox.getSelectionModel().getSelectedItem().toString());
            playBean.setData(cal);
            playBean.setAdmin(user.getText());
            boolean res = ModPGPageAppController.isDateValid(playBean);
            if(!res) {
                ModPGPageAppController.insertPlay(playBean);
                this.setFirstPlay(user.getText());
                Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
                conf.setContentText("Giornata inserita con successo!");
                conf.show();
            }else{
                err.setContentText("Esiste già una giornata con la stessa data.");
                err.show();
            }
        }
    }

    //prendo il valore dentro il database dell'arbitro
    public void playgroundReferee(String user){
        UserBean bean2 = new UserBean();
        bean2.setUsername(user);
        //prendo l'arbitro collegato all'admin
        Referee ref2=ModPGPageAppController.getRefereeFromAdmin(bean2);
        if(ref2 == null){
            ref.setText("");
        }else{
            ref.setText(ref2.getUsername());
        }
    }

    //cambio l'arbitro del campo
    public void setReferee(String refereeUsername){
        UserBean adminBean = new UserBean();
        UserBean refBean = new UserBean();
        refBean.setUsername(refereeUsername);
        adminBean.setUsername(user.getText());
        //controllo se il referee esiste
        try {
            Referee referee = ModPGPageAppController.getReferee(refBean);
            if (referee.getAdminCampo() != null) {//controllo che l'arbitro non sia collegato
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setContentText("Arbitro già di un altro campo!");
                ref.setText("");
                err.show();
            } else {
                ModPGPageAppController.setReferee(adminBean, refBean);
            }
        }catch (NotARefereeException e){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Arbitro inesistente!");
            this.playgroundReferee(user.getText());
            err.show();
        }
    }

    public void freeReferee(){
        UserBean u = new UserBean();
        u.setUsername(ref.getText());
        try {
            ModPGPageAppController.getReferee(u);
            ModPGPageAppController.freeReferee(u);
            ref.setText("");
        }catch (NotARefereeException e){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Impossibile scollegare un arbitro inesistente!");
            err.show();
        }
    }

    private void setStars(int stars){
        ImportStar.setStars(stars,starMPG1,starMPG2,starMPG3,starMPG4,starMPG5);
    }

    public void eventList(){
        ImportList.eventList(userType,id);
    }

    public void socialModify() throws IOException{
        Stage social = new Stage();
        Stage obj = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/SocialModPG.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);
        GraphicController controller = fxmlLoader.getController();
        try {
            controller.setUserVariables(user.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        scene.setFill(Color.TRANSPARENT);
        social.setResizable(false);
        social.initOwner(obj);
        social.initModality(Modality.APPLICATION_MODAL);
        social.initStyle(StageStyle.TRANSPARENT);
        social.setScene(scene);
        social.show();
    }

    public void facebook() throws java.io.IOException {
        if (!urlFacebook.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + urlFacebook.getText()});
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo facebook inserito");
        }
    }
    public void instagram() throws IOException {
        if(!urlInstagram.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + urlInstagram.getText()});
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nessun profilo instagram inserito");
            alert.show();
        }
    }
    public void whatsapp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(numWhatsapp.getText().isEmpty()||numWhatsapp.getText()==null){
            alert.setContentText("Numero whatsapp non presente");
        }else {
            alert.setContentText("Numero whatsapp: " + numWhatsapp.getText());
        }
        alert.show();
    }


    public void modify(){
        //abilita la modalita modifica
        description.setEditable(true);
        description.setStyle("-fx-border-color: rgb(229,190,51)");
        aggiorna.setVisible(true);
        refName.setText(ref.getText());//salvo il nome attuale dell'arbitro per controllare successivamente se sono state apportate modifiche
        ref.setEditable(true);
        ref.setStyle("-fx-border-color: rgb(229,190,51)");
    }

    public void save(){
        AdminBean a = new AdminBean();
        //passare a classe di scrittura in database
        String desc = description.getText();
        a.setUsername(user.getText());
        a.setDescrizione(desc);
        ModPGPageAppController.setDescription(a);
        description.setEditable(false);
        description.setStyle("");
        ref.setEditable(false);
        ref.setStyle("");
        //setto il nuovo arbitro se il nome non è vuoto e se è diverso da quello precedente
        if(!(ref.getText().isEmpty()) && !(ref.getText().equals(refName.getText()))) {
            this.setReferee(ref.getText());
        }
        aggiorna.setVisible(false);
    }
}