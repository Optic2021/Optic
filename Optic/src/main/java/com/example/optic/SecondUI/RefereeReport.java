package com.example.optic.SecondUI;

import com.example.optic.app_controllers.RefReportPlayer;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Giornata;
import com.example.optic.utilities.ImportCheckInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RefereeReport {
    private static RefereeBean ref= new RefereeBean();

    public static void main(String user) {
        Giornata Data;
        String input = null;
        Boolean res;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ref.setUsername(user);
        UserBean u1 = new UserBean();
        u1.setUsername(user);
        //set proprietario campo
        ref.setFkUsernameA1(RefReportPlayer.getAdminFromRef(u1).getUsername());
        u1.setUsername(ref.getFkUsernameA1());

        //Prendo la prossima giornata da giocare
        Giornata giornata=RefReportPlayer.getFirstPlay(u1);
        GiornataBean bean=new GiornataBean();
        bean.setData(giornata.getData());
        bean.setAdmin(ref.getFkUsernameA1());

        //attivita=giornata.getFkNome();
        do {
            System.out.println("Arbitro "+ref.getUsername());
            //print campo in cui lavora
            System.out.println("Assegnato al campo : "+ref.getFkUsernameA1());
            System.out.println("Giornata :" + dateFormat.format(bean.getData().getTime()));
            System.out.println("Lista giocatori bla bla bla");
            System.out.println("<-Indietro                   Avanti->");

            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = ImportCheckInput.isNumber(input);
            if (res) {
                //seleziona giocatore

            } else {
                Data=null;
                if (input.toLowerCase().equals("a")) {
                    //vai avanti
                    System.out.println("Avanti "+dateFormat.format(giornata.getData().getTime()));
                    Data=RefReportPlayer.getNextPlay(bean);

                } else if (input.toLowerCase().equals("i")) {
                    //vai indietro
                    System.out.println("Indietro "+dateFormat.format(giornata.getData().getTime()));
                    Data=RefReportPlayer.getLastPlay(bean);
                } else {
                    System.out.println("Input non valido");
                }
                if(Data!=null){
                    bean.setData(Data.getData());
                }
            }
        }while(true);
    }
}
