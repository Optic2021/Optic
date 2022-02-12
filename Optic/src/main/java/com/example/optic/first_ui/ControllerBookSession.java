package com.example.optic.first_ui;

import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Admin;
import com.example.optic.utilities.InvalidSelectedPG;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;

public class ControllerBookSession extends GraphicController {
    @FXML private Label user;
    @FXML private TableView table;
    @FXML private TableColumn nomeC;
    @FXML private TableColumn provincia;

    @Override
    public void setUserVariables(String user){
        this.user.setText(user);
        populateCampiTable(user);
    }
    public void toProfile() throws IOException {
        this.toView("views/userProfile.fxml",user.getText());
    }
    public void toHome() throws IOException {
        this.toView("views/userHomeMap.fxml");
    }
    public void toLogin() throws IOException {
        BookSessionAppController.closeConn();
        this.toView("views/login.fxml");
    }

    public void tableview(){
        try {
            Admin campo=(Admin) table.getSelectionModel().getSelectedItem();
            AdminBean bean = new AdminBean();
            if(campo==null){
                throw new InvalidSelectedPG();
            }
            bean.setNomeCampo(campo.getNomeC());
            if (!bean.getNomeCampo().isEmpty()){
                toView("views/userPgPage.fxml",user.getText(),bean);
            }
        } catch(InvalidSelectedPG z){
            Alert warn = new Alert(AlertType.WARNING);
            warn.setContentText("Seleziona un campo valido");
            warn.show();
            //z.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateCampiTable(String user) {
        //da finire
        PlayerBean player = new PlayerBean();
        player.setUsername(user);

        try {
            List<Admin> lista = BookSessionAppController.getCampi();
            nomeC.setCellValueFactory(new PropertyValueFactory<>("nomeC"));
            provincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
            int k=lista.size();
            int i=0;
            Admin elem;
            while(i<k){
                elem=lista.get(i);
                table.getItems().add(elem);
                i++;
            }
        }catch(Exception e){
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setContentText("Errore acquisizione campi");
            err.show();
        }
    }
}