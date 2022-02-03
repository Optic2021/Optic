package com.example.optic;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

public class Optic extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/login.fxml"));
        Scene scena = new Scene(fxmlLoader.load(), 1200, 720);
        scena.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scena.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        scena.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scena);
        stage.show();
    }

    public static void main(String[] args) {
        int i =2;
        if(i==1) {
            launch();
        }else {
            Optic2UI.main2();
            System.exit(0);
        }
    }
}

