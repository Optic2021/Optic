package com.example.optic.second_ui;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportCheckInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PlayerProfileViewCLI extends BaseCommandCLI{
    private static PlayerBean utente=new PlayerBean();

    public static boolean back(String command) {
        boolean flag=false;
        if (command.equals("Back") || command.equals("back") || command.equals("Indietro") || command.equals("indietro")) {
            PlayerHomeCLI.main(utente.getBUsername());
            flag=true;
        }
        return flag;
    }

    public static void main(String user,String viewerUsername){
        Boolean flag=true;
        String viewer = viewerUsername;
        utente.setBUsername(user);
        Player p;
        p = UserProfileAppController.getPlayer(utente);
        utente.setBIg(p.getIg());
        utente.setBFb(p.getFb());
        utente.setBDescrizione(p.getDescrizione());
        int command;
        do {
            System.out.println("Exit|Esci-------Home-------Back|Indietro");
            System.out.println("User: " + user + "\nDescription :" + utente.getBDescrizione());
            System.out.println("1 Facebook------------------------------");
            System.out.println("2 Instagram-----------------------------");
            System.out.println("3 Vedi report---------------------------");
            System.out.println("4 Vedi Storico Partite------------------");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = null;
            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean res = ImportCheckInput.isNumber(input);
            //controllo se l'input è numero
            if (!res) {
                if (BaseCommandCLI.exit(input)) {
                } else if (back(input)) {
                }
            } else {
                command=Integer.parseInt(input);
                switch (command) {
                    //apri facebook
                    case 1 :
                        try {
                            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + utente.getBFb()});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    //apri insta
                    case 2 :
                        try {
                            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + utente.getBIg()});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3 : showReport();break;
                    case 4 : showRecentPlays();break;
                    default:
                        ValutazioneBean val= new ValutazioneBean();
                        do {
                            System.out.println("Inserisci stelle valutazione");
                            try {
                                input = br.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(!ImportCheckInput.isNumber(input)){
                                flag=false;
                            }else{
                                val.setStelle(Integer.parseInt(input));
                            }
                            System.out.println("Inserisci descrizione valutazione");
                            try {
                                input = br.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (input.length()<0 || input.length()>200){
                                flag=false;
                            }else{
                                val.setRecensione(input);
                            }
                            val.setRiceve(utente.getBUsername());
                            val.setUsernameP1(viewer);
                            UserProfileAppController.saveReview(val);
                        }while (Boolean.FALSE.equals(flag));
                        break;
                }
            }
        }while(true);
    }

    public static void save(){
        UserProfileAppController.setInfo(utente);
    }

    public static void showRecentPlays(){
        UserBean bean = new UserBean();
        bean.setUsername(utente.getBUsername());
        List<Giornata> list = UserProfileAppController.getRecentPlayList(bean);
        if(list.isEmpty()){
            System.out.println("Non ci sono partite recenti da mostrare.");
        }else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("Data: "+list.get(i).getDataString()+" | Campo: "+list.get(i).getNomeC()+"\n");
            }
        }
    }

    public static void showReport(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        List<ReportBean> list;
        list = UserProfileAppController.getReportList(utente.getBUsername());
        if(list.isEmpty()){
            System.out.println("Non ci sono report da mostrare.");
        }else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFormattedText());
            }
        }
        try {
            do {
                System.out.println("Inserire un numero per tornare indietro");
                String inp;
                inp = br.readLine();
                res = ImportCheckInput.checkInput(inp);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println("Comando non valido!");
                }
            } while (!res);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
