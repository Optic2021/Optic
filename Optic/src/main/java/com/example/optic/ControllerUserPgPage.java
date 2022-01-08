package com.example.optic;

import com.example.optic.AppControllers.UserPgPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Campo;
import com.example.optic.entities.Valutazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.lang.Object;
import java.util.ArrayList;
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
    private TextArea testoRecensione;

    @FXML
    private TableView table;
    @FXML
    private TableColumn nome;
    @FXML
    private TableColumn recensione;


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

    public void toHome(ActionEvent e) throws Exception {
        this.toView("views/userHomeMap.fxml",user.getText());
    }
    public void starEnter(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 2: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 3: star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
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
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
        }
    }
    public void starExit(MouseEvent e){
        Label l = (Label)e.getSource();
        String id = l.getId();
        int starN = Integer.parseInt(id.substring(id.length() - 1));
        switch(starN){
            case 1: star1.setTextFill(Color.rgb(229,190,51));
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
    public void setUserVariables(String string) {
        String [] result=string.split(" ");
        String username=result[0];
        String campo=result[1];
        user.setText(username);

        AdminBean admin1 = null;
        AdminBean admin = null;
        try {
            admin = new AdminBean();
            admin.setNomeCampo(campo);
            admin1 = UserPgPageAppController.getCampoInfo(admin);
            populateTable(admin);
        }catch(Exception a){
            a.printStackTrace();
        }
        try {
            setCampo(admin1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateTable(AdminBean admin){
        ArrayList<Valutazione> list = UserPgPageAppController.reviewList(admin);
        nome.setCellValueFactory(new PropertyValueFactory<>("fk_UsernameP1"));
        recensione.setCellValueFactory(new PropertyValueFactory<>("Descrizione"));
        int k = list.size();
        System.out.println("Valore di k: "+k);
        int i = 0;
        Valutazione val;

        while (i < k) {
            System.out.println("Valore di i: "+i);
            val = list.get(i);
            table.getItems().add(val);
            i++;
        }
    }

    public void setCampo(AdminBean admin){
        campo.setText(admin.getNomeCampo());
        desc.setText(admin.getDescrizione());
        //Una volta sistemato db aggiungere anche il referee e controllare su tutti gli altri metodi
        //ref.setText(admin.getReferee());
    }

    public void review() throws IOException {
        String t;
        int starN=0;

        if(star1.getTextFill().equals(Color.rgb(229,190,51)) && star2.getTextFill().equals(Color.rgb(28,28,28))){
            starN=1;
        }else if (star2.getTextFill().equals(Color.rgb(229,190,51)) && star3.getTextFill().equals(Color.rgb(28,28,28))){
            starN=2;
        } else if (star3.getTextFill().equals(Color.rgb(229,190,51)) && star4.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=3;
        } else if (star4.getTextFill().equals(Color.rgb(229,190,51)) && star5.getTextFill().equals(Color.rgb(28,28,28))) {
            starN=4;
        }else{
            starN=5;
        }

        ValutazioneBean valutazione = new ValutazioneBean();

        valutazione.setRecensione(testoRecensione.getText());
        valutazione.setCampo(campo.getText());
        valutazione.setStelle(starN);
        valutazione.setUsernameP1(user.getText());

        UserPgPageAppController.saveReview(valutazione);
        AdminBean admin=new AdminBean();
        admin.setNomeCampo(campo.getText());
        table.getItems().clear();
        populateTable(admin);
    }

    public void paginaProfilo(ActionEvent e){

    }
}