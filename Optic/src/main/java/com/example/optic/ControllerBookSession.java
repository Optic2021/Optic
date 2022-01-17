package com.example.optic;

import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public void toProfile() throws Exception {
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
            AdminBean campo=(AdminBean) table.getSelectionModel().getSelectedItem();
            toView("views/userPgPage.fxml",user.getText(),campo);
        }
        catch(Exception z){
            z.printStackTrace();
        }
    }

    public void populateCampiTable(String user) {
        //da finire
        PlayerBean player = new PlayerBean();
        player.setUsername(user);

        try {
            List<AdminBean> lista = BookSessionAppController.getCampi();
            nomeC.setCellValueFactory(new PropertyValueFactory<>("nomeCampo"));
            provincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
            int k=lista.size();
            int i=0;
            AdminBean elem;
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