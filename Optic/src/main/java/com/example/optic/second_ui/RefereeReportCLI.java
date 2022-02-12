package com.example.optic.second_ui;

import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.app_controllers.RefReportPlayer;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportCheckInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;

public class RefereeReportCLI {
    private static RefereeBean ref= new RefereeBean();
    private static GiornataBean play= new GiornataBean();

    private RefereeReportCLI(){/*does np*/}

    public static void main(String user) {
        Giornata data;
        String input = null;
        Boolean res;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ref.setUsername(user);
        UserBean u1 = new RefereeBean();
        u1.setUsername(user);
        //set proprietario campo
        ref.setFkUsernameA1(RefReportPlayer.getAdminFromRef(u1).getUsername());
        u1.setUsername(ref.getFkUsernameA1());

        //Prendo la prossima giornata da giocare
        data = RefReportPlayer.getFirstPlay(u1);
        if (data == null) {
            data = BookSessionAppController.getRecentPlay(u1);
        }
        if (data == null) {
            System.out.println("ATTENZIONE: Non ci sono giornate da mostrare.\n");
        }else {
            play.setData(data.getData());
            play.setIdPlay(data.getIdGiornata());
            play.setAdmin(ref.getFkUsernameA1());
        }

        int j = 0;
        int i = 0;
        try {
            do {
                System.out.println("Arbitro " + ref.getUsername());
                //print campo in cui lavora
                System.out.println("Assegnato al campo : " + ref.getFkUsernameA1());
                System.out.println("Giornata :" + dateFormat.format(play.getData().getTime()));
                System.out.println("Num.-|-Username-|-Valutazione");
                List<Player> list = RefReportPlayer.getPlayersList(play);
                i = showPlayers(list);
                System.out.println("i)Precedente                   Successiva(a");
                input = br.readLine();
                res = ImportCheckInput.isNumber(input);
                if (Boolean.TRUE.equals(res)) {
                    j = Integer.parseInt(input);
                    //seleziona giocatore
                    if (i > j) {
                        //salva
                        report(list.get(j).getUsername());

                    } else {
                        System.out.println("ATTENZIONE: Numero non presente in lista\n");
                    }
                } else {
                    if (BaseCommandCLI.exit(input)) {
                        System.out.println("Chiusura app");
                    }
                    checkCommand(input);
                }
            } while (true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void checkCommand(String input) {
        Giornata data = null;
        if (input.equalsIgnoreCase("a")) {
            //vai avanti
            data = RefReportPlayer.getNextPlay(play);
        } else if (input.equalsIgnoreCase("i")) {
            //vai indietro
            data = RefReportPlayer.getLastPlay(play);
        } else {
            if (input.equalsIgnoreCase("indietro") || input.equalsIgnoreCase("back")) {
                return;
            }
            System.out.println("ATTENZIONE: Input non valido\n");
        }
        if (data != null) {
            play.setData(data.getData());
            play.setIdPlay(data.getIdGiornata());
        }
    }

    private static int showPlayers(List<Player> list) {
        int i = 0;
        if(list.isEmpty()){
            System.out.println("Nessun giocatore prenotato");
        }else {
            for (i = 0; i < list.size(); i++) {
                System.out.println(i + "---|---" + list.get(i).getUsername() + "---|---" + list.get(i).getValutazione());
            }
        }
        return i;
    }

    public static void report(String username){
        System.out.println("Stai immettendo un nuovo report al player : "+username+"\nImmetti 'Annulla' per annullare l'azione");
        ReportBean rep= new ReportBean();
        BufferedReader brtemp = new BufferedReader(new InputStreamReader(System.in));
        String inputemp;
        try {
            inputemp = brtemp.readLine();
            if (!inputemp.equalsIgnoreCase("annulla")) {
                rep.setMotivazione(inputemp);
                rep.setPlayer(username);
                rep.setReferee(ref.getUsername());
                RefReportPlayer.saveReport(rep);
                System.out.println("ATTENZIONE : Report salvato correttamente \n");
            } else {
                System.out.println("ATTENZIONE : Report annullato correttamente \n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
