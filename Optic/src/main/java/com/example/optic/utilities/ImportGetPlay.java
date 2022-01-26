package com.example.optic.utilities;

import com.example.optic.app_controllers.RefCampoController;
import com.example.optic.app_controllers.UserPgPageAppController;
import com.example.optic.bean.GiornataBean;
import com.example.optic.entities.Giornata;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ImportGetPlay {

    private ImportGetPlay(){
        //does nothing
    }

    public static void getPlay(Label idPlay, Label date, Label adminName, Label activity, int type, int userType) throws ParseException{
        GiornataBean playBean = new GiornataBean();
        Giornata play = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(!(idPlay.getText().isEmpty())) {
            Date data = dateFormat.parse(date.getText());
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);
            playBean.setData(cal);
            playBean.setAdmin(adminName.getText());
            if (userType == 0) {//referee
                if(type == 0){
                    play = RefCampoController.getNextPlay(playBean);
                }else{
                    play = RefCampoController.getLastPlay(playBean);
                }
            }else{//player
                if(type == 0){
                    play = UserPgPageAppController.getNextPlay(playBean);
                }else{
                    play = UserPgPageAppController.getLastPlay(playBean);
                }
            }
            if (play != null) {
                idPlay.setText(Integer.toString(play.getIdGiornata()));
                date.setText(dateFormat.format(play.getData().getTime()));//converto il calendar in un formato di data
                activity.setText(play.getFkNome());
            }
        }
    }
}
