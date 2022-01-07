package com.example.optic;

import com.example.optic.AppControllers.UserPgPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Campo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.lang.Object;
import java.util.StringTokenizer;

public class ControllerUserPgPage extends GraphicController {
    @FXML
    private Label user;
    @FXML
    private Label campo;
    @FXML
    private Label desc;
    @FXML
    private Label ref;


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

    @Override
    public void setUserVariables(String string) throws Exception {
        String [] result=string.split(" ");
        System.out.println("1: "+result[0]+"2: "+result[1]);
        String username=result[0];
        String campo=result[1];
        user.setText(username);

        AdminBean admin =new AdminBean();
        admin.setNomeCampo(campo);
        AdminBean admin1=UserPgPageAppController.getCampoInfo(admin);

        setCampo(admin1);
    }

    public void setCampo(AdminBean admin){
        campo.setText(admin.getNomeCampo());
        desc.setText(admin.getDescrizione());
        //Una volta sistemato db aggiungere anche il referee e controllare su tutti gli altri metodi
        //ref.setText(admin.getReferee());
    }

    public void paginaProfilo(ActionEvent e){

    }
}
