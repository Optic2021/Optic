package com.example.optic;

import com.example.optic.AppControllers.ModPGPageAppController;
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

public class ControllerModPGPage extends GraphicController {

    //sistema gli id

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
    /*
    @FXML
    private GridPane grid;

    @FXML
    private TextField urlFacebook;
    //settata non editable
    @FXML
    private TextField urlInstagram;
    //settata non editable
    @FXML
    private TextField numWhatsapp;
    //settata non editable*/

    @FXML
    private Pane id;
    @FXML
    private TextField ref;

    public void toLogin(ActionEvent e) throws IOException {
        ModPGPageAppController.closeConn();
        this.toView("views/login.fxml");
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
        //query db
        //Desktop.getDesktop().browse(new URI("http://www.example.com"));
        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome https://www.facebook.com/giuseppe.biasiniasr"});
    }
    public void instagram() throws IOException {
        //query db

        Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome https://www.instagram.com/giuseppe.biasini/"});
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
        addPhoto.setVisible(false);
        aggiorna.setVisible(false);
        addEvent.setVisible(false);

        /*urlFacebook.setEditable(false);
        urlInstagram.setEditable(false);
        numWhatsapp.setEditable(false);
        grid.setVisible(false);*/

    }

}
