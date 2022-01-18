package com.example.optic.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ImportUrl {

    private ImportUrl(){/*Does nothing*/}

    public static boolean controlliUrl(TextField urlInstagram, TextField urlFacebook, TextField numWhatsapp,boolean excl_W){
        Alert err = new Alert(Alert.AlertType.ERROR);
        boolean res=true;
        if(urlInstagram.getText().length() > 200 || urlFacebook.getText().length() > 200){
            res = false;
            err.setContentText("Url facebook o instagram troppo lunghi.");
            err.show();
        }
        if(urlFacebook.getText() != null && !(urlFacebook.getText().isEmpty()) && !(urlFacebook.getText().contains("https://www.facebook.com"))) {
            res = false;
            err.setContentText("Url facebook non valido.");
            err.show();
        }
        if(urlInstagram.getText() != null && !(urlInstagram.getText().isEmpty()) && !(urlInstagram.getText().contains("https://www.instagram.com"))) {
            res = false;
            err.setContentText("Url instagram non valido.");
            err.show();
        }
        if(!excl_W){
            if (numWhatsapp.getText() != null && !(numWhatsapp.getText().isEmpty()) & numWhatsapp.getText().length() != 10){
                res = false;
                err.setContentText("Numero di telefono non valido");
                err.show();
            }
        }
        return res;
    }

}
