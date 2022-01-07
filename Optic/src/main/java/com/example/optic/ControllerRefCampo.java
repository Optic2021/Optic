package com.example.optic;

import com.example.optic.AppControllers.RefCampoController;
import com.example.optic.AppControllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class ControllerRefCampo extends GraphicController{
    @FXML
    private Pane id;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Label user;

    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
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
    public void toLogin(ActionEvent e) throws IOException {
        RefCampoController.closeConn();
        this.toView("views/login.fxml");
    }
}
