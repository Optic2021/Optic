package com.example.optic.FirstUI;

import com.example.optic.Optic;
import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;

import java.sql.SQLException;
import java.util.List;

import com.example.optic.utilities.ImportList;
import com.example.optic.utilities.ImportStar;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class ControllerUserViewProfile extends GraphicController{
    //stelle recensione
    @FXML private Pane id;
    @FXML private Label starUW1;
    @FXML private Label starUW2;
    @FXML private Label starUW3;
    @FXML private Label starUW4;
    @FXML private Label starUW5;
    //stelle valutazione profilo
    @FXML private Label star11;
    @FXML private Label star22;
    @FXML private Label star33;
    @FXML private Label star44;
    @FXML private Label star55;
    @FXML private Label nVal;
    @FXML private TextArea description;
    @FXML private TextField urlFacebook;
    @FXML private TextField urlInstagram;
    @FXML private Label user;
    @FXML private  Label profile;
    @FXML private ListView reviews;
    @FXML private TableView partite;
    @FXML private TableColumn date;
    @FXML private TableColumn playground;
    //Label per acquisizione della recensione
    @FXML private  TextArea desc;

    @Override
    public void setUserVariables(String user) {
        String [] app = user.split(" ");
        String prof=app[0];
        String viewer=app[1];

        this.profile.setText("Profile: "+prof);
        this.user.setText(viewer);
        Player p = null;
        try {
            PlayerBean player = new PlayerBean();
            player.setBUsername(prof);
            this.populateReviewList(prof);
            this.populateGamesTable();
            p = UserProfileAppController.getPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(p != null) {
            //descrizione
            this.description.setText(p.getDescrizione());
            //se il giocatore è valutato positivamente, nome giallo
            if(p.getStato().equals("positivo")){
                //giocatore positivo
            }
            this.urlInstagram.setText(p.getIg());
            this.urlFacebook.setText(p.getFb());
        }
    }

    //popolo la lista di review e utilizzo i dati delle valutazioni per il contatore
    public void populateReviewList(String user){
        PlayerBean player = new PlayerBean();
        player.setBUsername(user);
        List<Valutazione> list = null;
        list = UserProfileAppController.getReviewList(player);
        int stars= ImportList.populateReviewList(list,reviews,nVal);
        if(stars>0){
            setStars(stars);
        }
    }

    //popolo la tabella con lo storico delle partite del player
    public void populateGamesTable() throws SQLException {
        UserBean player = new UserBean();
        String[] app = profile.getText().split(" ");
        String username=app[1];
        player.setUsername(username);
        date.setCellValueFactory(new PropertyValueFactory<>("dataString"));
        playground.setCellValueFactory(new PropertyValueFactory<>("nomeC"));
        List<Giornata> pList = UserProfileAppController.getRecentPlayList(player);
        Giornata g = null;
        for(int i = 0;i < pList.size(); i++){
            g = pList.get(i);
            partite.getItems().add(g);
        }
    }

    public void instagram() throws IOException {
        if(!urlInstagram.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlInstagram.getText()});
        }
    }

    public void facebook() throws IOException {
        if(!urlFacebook.getText().isEmpty()) {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + this.urlFacebook.getText()});
        }
    }

    //Set della visibilita delle stelle in base al valore
    public void reportList(){
        try {
            String[] app = profile.getText().split(" ");
            String username=app[1];
            Stage list = new Stage();
            Stage obj = (Stage) id.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/reportList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 580);
            GraphicController controller = fxmlLoader.getController();
            controller.setUserVariables(username);
            scene.setFill(Color.TRANSPARENT);
            list.setResizable(false);
            list.initOwner(obj);
            list.initModality(Modality.APPLICATION_MODAL);
            list.initStyle(StageStyle.TRANSPARENT);
            list.setScene(scene);
            list.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void review(){

        String descript=desc.getText();
        int starN;
        String givento=profile.getText();
        String giver=user.getText();

        if(starUW1.getTextFill().equals(Color.rgb(229,190,51)) && starUW2.getTextFill().equals(Color.rgb(28,28,28))){
            starN=1;
        }else if (starUW2.getTextFill().equals(Color.rgb(229,190,51)) && starUW3.getTextFill().equals(Color.rgb(28,28,28))){
            starN=2;
        } else if (starUW3.getTextFill().equals(Color.rgb(229,190,51)) && starUW4.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=3;
        } else if (starUW4.getTextFill().equals(Color.rgb(229,190,51)) && starUW5.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=4;
        }else{
            starN=5;
        }
        String []array;
        array=givento.split(" ");

        ValutazioneBean val= new ValutazioneBean();
        val.setUsernameP1(giver);
        val.setRecensione(descript);
        val.setStelle(starN);
        val.setRiceve(array[1]);

        UserProfileAppController.saveReview(val);
        reviews.getItems().clear();
        String []ar;
        ar=profile.getText().split(" ");
        populateReviewList(ar[1]);
    }

    //forse si puo mettere su graphic controller
    public void starEnter(MouseEvent e){
        ImportStar.starEnter(e, starUW1,starUW2, starUW3, starUW4, starUW5);
    }
    public void starExit(MouseEvent e){
        ImportStar.starEnter(e, starUW1,starUW2, starUW3, starUW4, starUW5);
    }
    private void setStars(int stars){
        ImportStar.setStars(stars, star11,star22, star33, star44, star55);
    }


    public void toHome() throws IOException {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
}
