package com.example.optic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerUserProfile {
    @FXML
    private Pane id;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TextArea description;
    @FXML
    private TextField urlFacebook;
    @FXML
    private TextField urlInstagram;
    @FXML
    private TextField numWhatsapp;
    @FXML
    private GridPane modifica;
    @FXML
    private Button salva;

    public void modify(){
        //Rendo visibile il tasto aggiorna e modificabile la grid di connessione ai social e la description
        description.setEditable(true);
        urlFacebook.setEditable(true);
        urlInstagram.setEditable(true);
        numWhatsapp.setEditable(true);
        modifica.setVisible(true);
        salva.setVisible(true);

    }

    public void save(){
        //Informazione da salvare nel dao

        String Text1=description.getText();
        String Text2=urlFacebook.getText();
        String Text3=numWhatsapp.getText();
        String Text4=urlInstagram.getText();

        description.setEditable(false);
        urlFacebook.setEditable(false);
        urlInstagram.setEditable(false);
        numWhatsapp.setEditable(false);
        modifica.setVisible(false);
        salva.setVisible(false);

    }

    public void exitButton(ActionEvent e){
        Platform.exit();
    }
    public void reduceButton(ActionEvent e){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setIconified(true);
    }
    public void drag(MouseEvent e){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setX(e.getScreenX() + xOffset);
        obj.setY(e.getScreenY() + yOffset);
    }

    public void toHome(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("userHomeMap.fxml"));
        Stage obj = (Stage) id.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
        scene.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj.setX(event.getScreenX() - xOffset);
                obj.setY(event.getScreenY() - yOffset);
            }
        });
        scene.setFill(Color.TRANSPARENT);
        obj.setScene(scene);
        obj.show();
    }

}
