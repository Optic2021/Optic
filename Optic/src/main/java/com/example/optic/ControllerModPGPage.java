package com.example.optic;

import com.example.optic.AppControllers.LoginController;
import com.example.optic.AppControllers.ModPGPageAppController;
import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Referee;
import com.example.optic.entities.Valutazione;
import com.example.optic.entities.ValutazionePlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

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
    private Button addEvent;
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
            this.populateGameTable(user);
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

    public void populateGameTable(String user){

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
        addEvent.setVisible(true);
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
        addEvent.setVisible(false);

        /*urlFacebook.setEditable(false);
        urlInstagram.setEditable(false);
        numWhatsapp.setEditable(false);
        grid.setVisible(false);*/
    }
}