package com.example.optic.first_ui;

import com.example.optic.Optic;
import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.utilities.ImportList;
import com.example.optic.utilities.ImportStar;
import com.example.optic.utilities.ImportUrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControllerUserProfile extends GraphicController{
    @FXML private Pane id;
    @FXML private Label starUP1;
    @FXML private Label starUP2;
    @FXML private Label starUP3;
    @FXML private Label starUP4;
    @FXML private Label starUP5;
    @FXML private Label nVal;
    @FXML private TextArea description;
    @FXML private TextField urlFacebook;
    @FXML private TextField urlInstagram;
    @FXML private GridPane modifica;
    @FXML private Button salva;
    @FXML private Label user;
    @FXML private ListView reviews;
    @FXML private TableView partite;
    @FXML private TableColumn date;
    @FXML private TableColumn playground;
    @FXML private Label userType;

    @Override
    public void setUserVariables(String user){
        this.user.setText(user);
        Player p = null;
        try {
            PlayerBean player = new PlayerBean();
            player.setBUsername(user);
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
                //profilo plus
            }
            this.urlInstagram.setText(p.getIg());
            this.urlFacebook.setText(p.getFb());
        }
    }

    public void eventList(){
        ImportList.eventList(userType,id);
    }

    public void reportList(){
        try {
            Stage list = new Stage();
            Stage obj = (Stage) id.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/reportList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 580);
            GraphicController controller = fxmlLoader.getController();
            controller.setUserVariables(user.getText());
            scene.setFill(Color.TRANSPARENT);
            list.setResizable(false);
            list.initOwner(obj);
            list.initModality(Modality.APPLICATION_MODAL);
            list.initStyle(StageStyle.TRANSPARENT);
            list.setScene(scene);
            list.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    //popolo la lista di review e utilizzo i dati delle valutazioni per il contatore
    public void populateReviewList(String user) {
        PlayerBean player = new PlayerBean();
        player.setBUsername(user);
        List<Valutazione> reviewList = null;
        reviewList = UserProfileAppController.getReviewList(player);
        int stars=ImportList.populateReviewList(reviewList,reviews,nVal);
        if(stars>0){
            setStars(stars);
        }
    }

    //popolo la tabella con lo storico delle partite del player
    public void populateGamesTable(String user) throws SQLException {
        UserBean player = new UserBean();
        player.setUsername(user);
        date.setCellValueFactory(new PropertyValueFactory<>("dataString"));
        playground.setCellValueFactory(new PropertyValueFactory<>("nomeC"));
        List<Giornata> list = UserProfileAppController.getRecentPlayList(player);
        Giornata g = null;
        for(int i = 0;i < list.size(); i++){
            g = list.get(i);
            partite.getItems().add(g);
        }
    }

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
        ImportStar.setStars(stars,starUP1,starUP2,starUP3,starUP4,starUP5);
    }

    public void modify(){
        //Rendo visibile il tasto aggiorna e modificabile la grid di connessione ai social e la description
        description.setEditable(true);
        description.setStyle("-fx-border-color: rgb(229,190,51)");
        urlFacebook.setEditable(true);
        urlInstagram.setEditable(true);
        modifica.setVisible(true);
        salva.setVisible(true);
    }

    public void save(){
        Alert err = new Alert(Alert.AlertType.ERROR);
        String desc = description.getText();
        String fb = urlFacebook.getText();
        String ig = urlInstagram.getText();
        boolean res = true;
        if(desc.length() > 200){
            res = false;
            err.setContentText("La descrizione supera il limite massimo di 200 caratteri: "+desc.length());
            err.show();
        }
        ImportUrl.controlliUrl(urlInstagram,urlFacebook,null,true);
        if(res){
            PlayerBean p = new PlayerBean();
            p.setBUsername(user.getText());
            p.setBDescrizione(desc);
            p.setBFb(fb);
            p.setBIg(ig);
            UserProfileAppController.setInfo(p);

            description.setEditable(false);
            description.setStyle("");
            urlFacebook.setEditable(false);
            urlInstagram.setEditable(false);
            modifica.setVisible(false);
            salva.setVisible(false);
        }
    }

    public void toHome() throws IOException {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
}