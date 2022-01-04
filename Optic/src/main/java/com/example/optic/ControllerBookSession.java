package com.example.optic;

import com.example.optic.AppControllers.BookSessionAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Campo;
import com.example.optic.entities.ValutazionePlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ControllerBookSession extends GraphicController {
    @FXML
    private Label user;
    @FXML
    private TableView table;
    @FXML
    private TableColumn NomeC;
    @FXML
    private TableColumn Provincia;

    @Override
    public void setUserVariables(String user){
        this.user.setText(user);
        populateReviewTable(user);
    }
    public void toProfile(ActionEvent e) throws Exception {
        this.toView("views/userProfile.fxml",user.getText());
    }
    public void toHome(ActionEvent e) throws IOException {
        this.toView("views/userHomeMap.fxml");
    }
    public void toLogin(ActionEvent e) throws IOException {
        BookSessionAppController.closeConn();
        this.toView("views/login.fxml");
    }

    public void tableview(ActionEvent e){
        System.out.println("dragdragdragdragdragdragdrag");
    }

    //Debug
    //Visualizzare provincia e non password
    //Controllare che effettivamente populate review non buggi niente
    public void populateReviewTable(String user) {
        System.out.println("CIAOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        PlayerBean player = new PlayerBean();
        player.setUsername(user);
        NomeC.setCellValueFactory(new PropertyValueFactory<>("Nomec"));
        Provincia.setCellValueFactory(new PropertyValueFactory<>("Provincia"));
        boolean list = true;
        if (list) {
            Campo campo = new Campo("Softair river 2.0", "giocatore scorretto");
            table.getItems().add(campo);
            Campo campo1 = new Campo("Thule softair", "Somebody once told me");
            table.getItems().add(campo1);
        }
    }

}
