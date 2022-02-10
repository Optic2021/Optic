package com.example.optic.first_ui;

import com.example.optic.Optic;
import com.example.optic.bean.AdminBean;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class GraphicController {
    @FXML
    private Pane id;
    @FXML
    private Label user;

    private double xOffset = 0;
    private double yOffset = 0;

    public void exitButton(){
        Platform.exit();
    }
    public void reduceButton(){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setIconified(true);
    }
    public void drag(MouseEvent e){
        Stage obj = (Stage) id.getScene().getWindow();
        obj.setX(e.getScreenX() + xOffset);
        obj.setY(e.getScreenY() + yOffset);
    }

    public abstract void setUserVariables(String username);
        //ogni controller grafico implementerà la sua versione in base alle necessità

    public void toView(String view) throws IOException {
        Stage obj = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));
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

    public void toView(String view,String user, String viewer) throws IOException {
        Stage obj1 = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));
        Scene scene1 = new Scene(fxmlLoader.load(), 1200, 720);
        GraphicController controller = fxmlLoader.getController();
        String usr=user+" "+viewer;
        controller.setUserVariables(usr);
        scene1.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene1.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj1.setX(event.getScreenX() - xOffset);
                obj1.setY(event.getScreenY() - yOffset);
            }
        });
        scene1.setFill(Color.TRANSPARENT);
        obj1.setScene(scene1);
        obj1.show();
    }

    public void toView(String view, String usr) throws IOException {
        Stage obj2 = (Stage) id.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));
        Scene scene2 = new Scene(fxmlLoader.load(), 1200, 720);
        GraphicController controller = fxmlLoader.getController();
        controller.setUserVariables(usr);
        scene2.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene2.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj2.setX(event.getScreenX() - xOffset);
                obj2.setY(event.getScreenY() - yOffset);
            }
        });
        scene2.setFill(Color.TRANSPARENT);
        obj2.setScene(scene2);
        obj2.show();
    }

    public void toView(String view, String usr, AdminBean campo)throws IOException{
        Stage obj3 = (Stage) id.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(Optic.class.getResource(view));

        Scene scene3 = new Scene(fxmlLoader.load(), 1200, 720);
        GraphicController controller = fxmlLoader.getController();

        usr = usr+"/"+campo.getNomeCampo();
        controller.setUserVariables(usr);

        scene3.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene3.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                obj3.setX(event.getScreenX() - xOffset);
                obj3.setY(event.getScreenY() - yOffset);
            }
        });
        scene3.setFill(Color.TRANSPARENT);
        obj3.setScene(scene3);
        obj3.show();
    }
}
