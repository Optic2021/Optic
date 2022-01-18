package com.example.optic.utilities;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ImportStar {

    private ImportStar(){/*Does nothing*/}

    public static void starEnter(MouseEvent e, Label star1, Label star2, Label star3, Label star4, Label star5){
        Label l = (Label)e.getSource();
        String idnt = l.getId();
        int starN = Integer.parseInt(idnt.substring(idnt.length() - 1));
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
            default:
                star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(28,28,28));
                star3.setTextFill(Color.rgb(28,28,28));
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
        }
    }
    //Colora le stelle quando il mouse esce dalla label
    public static void starExit(MouseEvent e,Label star1,Label star2,Label star3,Label star4,Label star5){
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
            case 3:
                star4.setTextFill(Color.rgb(28,28,28));
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            case 4:
                star5.setTextFill(Color.rgb(28,28,28));
                break;
            default:star1.setTextFill(Color.rgb(229,190,51));
                star2.setTextFill(Color.rgb(229,190,51));
                star3.setTextFill(Color.rgb(229,190,51));
                star4.setTextFill(Color.rgb(229,190,51));
                star5.setTextFill(Color.rgb(229,190,51));
        }
    }

    //Set delle stelle di valutazione media del profilo
    public static void setStars(int stars, Label star11, Label star22, Label star33, Label star44, Label star55){
        switch (stars){
            case 1:
                star11.setVisible(true);
                break;
            case 2:
                star11.setVisible(true);
                star22.setVisible(true);
                break;
            case 3:
                star11.setVisible(true);
                star22.setVisible(true);
                star33.setVisible(true);
                break;
            case 4:
                star11.setVisible(true);
                star22.setVisible(true);
                star33.setVisible(true);
                star44.setVisible(true);
                break;
            default:
                star11.setVisible(true);
                star22.setVisible(true);
                star33.setVisible(true);
                star44.setVisible(true);
                star55.setVisible(true);
        }
    }

    public static int getStarN(Label star1, Label star2, Label star3, Label star4, Label star5){
        int starN;
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
        return starN;
    }
}
