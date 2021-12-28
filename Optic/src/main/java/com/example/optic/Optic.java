package com.example.optic;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.sql.*;

public class Optic extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    private static String USER = "root";
    private static String PASS = "17moneC*";
    //private static String PASS = "Luca_2001";
    private static String DB_URL = "jdbc:mysql://localhost:3306/optic";
    private static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    @Override
    public void start(Stage stage) throws IOException, SQLException, Exception{
        Statement stmt = null;
        Connection conn = null;
        try{
            // STEP 2: loading dinamico del driver mysql
            Class.forName(DRIVER_CLASS_NAME);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

           ResultSet rs = stmt.executeQuery("select * from referee");

           while(rs.next()){
                System.out.println(rs.getString("Username")+ " "+rs.getString("Password"));
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource("views/login.fxml"));
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
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        scene.setFill(Color.TRANSPARENT);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

