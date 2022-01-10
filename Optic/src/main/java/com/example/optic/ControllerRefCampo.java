package com.example.optic;

import com.example.optic.AppControllers.RefCampoController;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class ControllerRefCampo extends GraphicController{
    @FXML
    private Pane id;
    @FXML
    private Label user;
    @FXML
    private Label userType;
    @FXML
    private TextArea desc;
    @FXML
    private Label address;
    @FXML
    private Label prov;
    @FXML
    private Label admin;
    @FXML
    private Label title;


    @Override
    public void setUserVariables(String user) throws Exception {
        this.user.setText(user);
        Admin a = null;
        UserBean bean = new UserBean();
        bean.setUsername(user);
        a = RefCampoController.getAdminFromRef(bean);
        if(a == null){
            Alert err = new Alert(Alert.AlertType.WARNING);
            err.setContentText("Non sei collegato a nessun campo!");
            err.show();
        }else{
            this.title.setText(a.getNomeC());
            this.desc.setText(a.getDescrizioneC());
            this.address.setText(a.getVia());
            this.prov.setText(a.getProvincia());
            this.admin.setText(a.getUsername());
        }
    }

    public void eventList(ActionEvent e) throws Exception {
        try {
            Stage list = new Stage();
            Stage obj = (Stage) id.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/eventList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 580);
            GraphicController controller = fxmlLoader.getController();
            controller.setUserVariables(userType.getText());
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
    public void toLogin(ActionEvent e) throws IOException {
        RefCampoController.closeConn();
        this.toView("views/login.fxml");
    }

    public void toInstagram(){
       // Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlInstagram.getText()});
    }

    public void toFacebook(){
       // Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+urlFacebook.getText()});
    }

    public void toWhatsapp(){

    }
}
