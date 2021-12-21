package com.example.optic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.IOException;

public class ControllerUserPgPage extends GraphicController {
    @FXML
    private Label star1;
    @FXML
    private Label star2;
    @FXML
    private Label star3;
    @FXML
    private Label star4;
    @FXML
    private Label star5;

    public void toHome(ActionEvent e) throws IOException {
        this.toView("views/userHomeMap.fxml");
    }
    public void starEnter(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 2: star1.setTextFill(Color.rgb(229,190,51));
                    star2.setTextFill(Color.rgb(229,190,51));
                    star3.setTextFill(Color.rgb(28,28,28));
                    break;
            case 3: star1.setTextFill(Color.rgb(229,190,51));
                    star2.setTextFill(Color.rgb(229,190,51));
                    star3.setTextFill(Color.rgb(229,190,51));
                    star4.setTextFill(Color.rgb(28,28,28));
                    break;
            case 4: star1.setTextFill(Color.rgb(229,190,51));
                    star2.setTextFill(Color.rgb(229,190,51));
                    star3.setTextFill(Color.rgb(229,190,51));
                    star4.setTextFill(Color.rgb(229,190,51));
                    star5.setTextFill(Color.rgb(28,28,28));
                    break;
            case 5: star1.setTextFill(Color.rgb(229,190,51));
                    star2.setTextFill(Color.rgb(229,190,51));
                    star3.setTextFill(Color.rgb(229,190,51));
                    star4.setTextFill(Color.rgb(229,190,51));
                    star5.setTextFill(Color.rgb(229,190,51));
                    break;
            default:star1.setTextFill(Color.rgb(229,190,51));
                    star2.setTextFill(Color.rgb(28,28,28));
        }
    }
    public void starExit(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 1: star1.setTextFill(Color.rgb(28,28,28));
                    star2.setTextFill(Color.rgb(28,28,28));
                    star3.setTextFill(Color.rgb(28,28,28));
                    star4.setTextFill(Color.rgb(28,28,28));
                    star5.setTextFill(Color.rgb(28,28,28));
                    break;
            case 2: star3.setTextFill(Color.rgb(28,28,28));
                    star4.setTextFill(Color.rgb(28,28,28));
                    star5.setTextFill(Color.rgb(28,28,28));
                    break;
            case 3: star4.setTextFill(Color.rgb(28,28,28));
                    star5.setTextFill(Color.rgb(28,28,28));
                    break;
            case 4: star5.setTextFill(Color.rgb(28,28,28));
                    break;
            default:;
        }
    }
    public void paginaProfilo(ActionEvent e){

    }
}
