package com.example.optic.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ImportUrl {

    private ImportUrl(){/*Does nothing*/}

    public static boolean controlliUrl(TextField urlInstagram, TextField urlFacebook, TextField numWhatsapp,boolean exclW){
        Alert err = new Alert(Alert.AlertType.ERROR);
        boolean res=true;

        if( urlFacebook.getText()!= null && !(urlFacebook.getText().isEmpty()) && !(urlFacebook.getText().contains("https://www.facebook.com")) ) {
            res = false;
            err.setContentText("Url facebook non valido.");
            err.show();
        }
        if(urlInstagram.getText() != null && (!(urlInstagram.getText().isEmpty()) && !(urlInstagram.getText().contains("https://www.instagram.com")))) {
            res = false;
            err.setContentText("Url instagram non valido.");
            err.show();
        }
        if (!exclW && numWhatsapp.getText() != null && (numWhatsapp.getText().isEmpty()) && numWhatsapp.getText().length() != 10){
            res = false;
            err.setContentText("Numero di telefono non valido");
            err.show();
        }
        if(res && (urlInstagram.getText().length() > 200 || urlFacebook.getText().length() > 200)) {
                res = false;
                err.setContentText("Url facebook o instagram troppo lunghi.");
                err.show();
        }
        return res;
    }

    public static boolean controlliUrl(String urlInstagram, String urlFacebook, String numWhatsapp,int social){
        boolean res=true;
        switch(social){
            case 1 :
                if(urlFacebook.length() > 200){
                    res = false;
                    System.out.println("Url troppo lungo");
                }else if(!(urlFacebook.isEmpty()) && !(urlFacebook.contains("https://www.facebook.com"))) {//il controllo sul null serve per evitare il crush
                    res = false;
                    System.out.println("Url non valido");
                }
                break;
            case 2:
                if(urlInstagram.length() > 200 ){
                    res = false;
                    System.out.println("Url troppo lungo");
                }else if(!(urlInstagram.isEmpty()) && !(urlInstagram.contains("https://www.instagram.com"))) { //il controllo sul null serve per evitare il crush
                    res = false;
                    System.out.println("Url non valido");
                }
                break;
            default:
                if (numWhatsapp != null && !(numWhatsapp.isEmpty()) && numWhatsapp.length() != 10){
                    res = false;
                    System.out.println("Numero non valido");
                }

        }
        return res;
    }

}
