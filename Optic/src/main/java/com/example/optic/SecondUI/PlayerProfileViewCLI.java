package com.example.optic.SecondUI;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportCheckInput;
import com.example.optic.utilities.ImportUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayerProfileViewCLI extends BaseCommandCLI{
    private static PlayerBean utente=new PlayerBean();
    private static String viewer;

    public static boolean back(String command) {
        boolean flag=false;
        if (command.equals("Back") || command.equals("back") || command.equals("Indietro") || command.equals("indietro")) {
            PlayerHomeCLI.main(utente.getBUsername());
            flag=true;
        }
        return flag;
    }

    public static void main(String user,String Viewer){
        Boolean flag=false;
        viewer=Viewer;
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
                        }while (flag==false);
                        break;
                }
            }
        }while(true);
    }

    public static void save(){
        UserProfileAppController.setInfo(utente);
    }



}
