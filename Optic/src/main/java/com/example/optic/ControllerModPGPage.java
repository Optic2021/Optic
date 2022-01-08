package com.example.optic;

import com.example.optic.AppControllers.ModPGPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ControllerModPGPage extends GraphicController {
    @FXML
    private Label user;
    @FXML
    private Label title;
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
    private TextArea description;
    //settata non editable
    @FXML
    private Button addPhoto;
    //settata non visible
    @FXML
    private Button aggiorna;
    //settata non visible
    @FXML
    private Button addPlay;
    //settata non visible
    @FXML
    private Label urlFacebook;
    //settata non editable
    @FXML
    private Label urlInstagram;
    //settata non editable
    @FXML
    private Label numWhatsapp;
    @FXML
    private Label refName;
    @FXML
    private Label prov;
    @FXML
    private Label address;
    //settata non editable
    @FXML
    private Pane id;
    @FXML
    private TextField ref;
    @FXML
    private ListView reviews;
    @FXML
    private ComboBox activityBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label nPlayers;
    @FXML
    private Label activity;
    @FXML
    private Label date;
    @FXML
    private Label idPlay;
    @FXML
    private TableView players;
    @FXML
    private TableColumn playerName;
    @FXML
    private TableColumn playerVal;

    public void toLogin(ActionEvent e) throws IOException {
        ModPGPageAppController.closeConn();
        this.toView("views/login.fxml");
    }

    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
        Admin a = null;
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
            //aggiungere provincia
            this.description.setText(a.getDescrizioneC());
            this.description.setWrapText(true);
            this.urlInstagram.setText(a.getIg());;
            this.urlFacebook.setText(a.getFb());;
            this.numWhatsapp.setText(a.getWa());;
            this.setFirstPlay(a.getUsername());
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
                //activity.impostaAttivita;
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFk_Nome());
                this.populatePlayersTable();
            }
        }catch (IOException e){
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
            playBean.setAdmin(user.getText());
            play = ModPGPageAppController.getNextPlay(playBean);
            if (play != null) {
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFk_Nome());
                this.populatePlayersTable();
            } else {
                activityBox.setVisible(true);
                this.populateActivityBox();
                datePicker.setVisible(true);
                addPlay.setVisible(true);
                idPlay.setVisible(false);
                date.setVisible(false);
                activity.setVisible(false);
                nPlayers.setVisible(false);
                players.setVisible(false);
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
            if(!(activity.getText().isEmpty()) && activityBox.isVisible()) {
                activityBox.setVisible(false);
                datePicker.setVisible(false);
                addPlay.setVisible(false);
                idPlay.setVisible(true);
                date.setVisible(true);
                activity.setVisible(true);
                nPlayers.setVisible(true);
                players.setVisible(true);
            }else{
                Date data = date_format.parse(date.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                playBean.setData(cal);
                playBean.setAdmin(user.getText());
                play = ModPGPageAppController.getLastPlay(playBean);
                if (play != null) {
                    idPlay.setText(Integer.toString(play.getIdGiornata()));
                    date.setText(date_format.format(play.getData().getTime()));//converto il calendar in un formato di data
                    activity.setText(play.getFk_Nome());
                    this.populatePlayersTable();
                }
                activityBox.setVisible(false);
                datePicker.setVisible(false);
                addPlay.setVisible(false);
                idPlay.setVisible(true);
                date.setVisible(true);
                activity.setVisible(true);
                nPlayers.setVisible(true);
                players.setVisible(true);
            }
        }catch (ParseException | IOException e){
            e.printStackTrace();
        }
    }

    public void populateReviewList(String user) throws IOException {
        AdminBean admin = new AdminBean();
        int numVal = 0;
        int mediaVal = 0;
        int stars = 0;
        admin.setUsername(user);
        ArrayList<Valutazione> list = ModPGPageAppController.getReviewList(admin);
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
    }

    public void populatePlayersTable() throws IOException {
        GiornataBean playBean = new GiornataBean();
        players.getItems().clear();
        playerName.setCellValueFactory(new PropertyValueFactory<>("username"));
        playerVal.setCellValueFactory(new PropertyValueFactory<>("stelle"));
        Player p = new Player();
        playBean.setIdPlay(Integer.parseInt(idPlay.getText()));
        ArrayList<Player> list = ModPGPageAppController.getPlayersList(playBean);
        for(int i = 0; i < list.size(); i++) {
            p = list.get(i);
            players.getItems().add(p);
        }
        nPlayers.setText(Integer.toString(list.size()));
    }

    public void populateActivityBox() throws IOException {
        activityBox.getItems().clear();
        ArrayList<Event> list = ModPGPageAppController.getEventList();
        for(int i = 0; i < list.size();i++){
            activityBox.getItems().add(list.get(i).getNome());
        }
    }

    //prendo il valore dentro il database dell'arbitro
    public void playgroundReferee(String user){
        UserBean bean2 = new UserBean();
        bean2.setUsername(user);
        //prendo l'arbitro collegato all'admin
        Referee ref2 = ModPGPageAppController.getRefereeFromAdmin(bean2);
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
        Referee referee = ModPGPageAppController.getReferee(refBean);
        if(referee == null){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Arbitro inesistente!");
            this.playgroundReferee(user.getText());
            err.show();
        }else if(referee.getAdminCampo() != null){//controllo che l'arbitro non sia collegato
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Arbitro già di un altro campo!");
            ref.setText("");
            err.show();
        }else{
            ModPGPageAppController.setReferee(adminBean,refBean);
        }
    }

    public void freeReferee(){
        UserBean u = new UserBean();
        u.setUsername(ref.getText());
        Referee referee = ModPGPageAppController.getReferee(u);
        if(referee == null){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Impossibile scollegare un arbitro inseistente!");
            err.show();
        }else{
            ModPGPageAppController.freeReferee(u);
            ref.setText("");
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

    public void eventList(ActionEvent e) throws IOException {
        Stage list = new Stage();
        Stage obj = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/eventList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 350);
        scene.setFill(Color.TRANSPARENT);
        list.setResizable(false);
        list.initOwner(obj);
        list.initModality(Modality.APPLICATION_MODAL);
        list.initStyle(StageStyle.TRANSPARENT);
        list.setScene(scene);
        list.show();
    }

    public void socialModify() throws IOException{
        Stage social = new Stage();
        Stage obj = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/SocialModPG.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 250);
        scene.setFill(Color.TRANSPARENT);
        social.setResizable(false);
        social.initOwner(obj);
        social.initModality(Modality.APPLICATION_MODAL);
        social.initStyle(StageStyle.TRANSPARENT);
        social.setScene(scene);
        social.show();
    }

    public void facebook() throws java.io.IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlFacebook.getText()});
    }
    public void instagram() throws IOException {
        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlInstagram.getText()});
    }
    public void whatsapp(){
        //query db
        /*@Override public void start(Stage stage) throws Exception {
            WebView web = new WebView();
            web.getEngine().load("http://www.google.com/");
            Scene scene = new Scene(web);
            stage.setScene(scene);
            stage.show();
        }*/
    }


    public void modify(ActionEvent e){
        //abilita la modalita modifica
        description.setEditable(true);
        addPhoto.setVisible(true);
        aggiorna.setVisible(true);
        refName.setText(ref.getText());//salvo il nome attuale dell'arbitro per controllare successivamente se sono state apportate modifiche
        ref.setEditable(true);

        /*grid.setVisible(true);
        urlFacebook.setEditable(true);
        urlInstagram.setEditable(true);
        numWhatsapp.setEditable(true);*/

    }

    public void save(ActionEvent e){
        //passare a classe di scrittura in database
        String text1 =description.getText();
        /*String text2 =urlFacebook.getText();
        String text3 =urlInstagram.getText();
        String text4 =numWhatsapp.getText();*/

        description.setEditable(false);
        ref.setEditable(false);
        //setto il nuovo arbitro se il nome non è vuoto e se è diverso da quello precedente
        if(!(ref.getText().isEmpty()) && !(ref.getText().equals(refName.getText()))) {
            this.setReferee(ref.getText());
        }
        addPhoto.setVisible(false);
        aggiorna.setVisible(false);

        /*urlFacebook.setEditable(false);
        urlInstagram.setEditable(false);
        numWhatsapp.setEditable(false);
        grid.setVisible(false);*/
    }
}