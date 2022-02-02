package com.example.optic.SecondUI;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportCheckInput;
import com.example.optic.utilities.ImportUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayerProfileCLI extends BaseCommandCLI{
    private static PlayerBean utente=new PlayerBean();

    public static boolean back(String command) {
        boolean flag=false;
        if (command.equals("Back") || command.equals("back") || command.equals("Indietro") || command.equals("indietro")) {
            PlayerHomeCLI.main(utente.getBUsername());
            flag=true;
        }
        return flag;
    }

    public static void main(String user){
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
            System.out.println("Modifica--------------------------------");
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
                  //if(input.equals("save")){
                  //    save();
                  //}
                }else if(input.equals("Modifica")){
                    System.out.println("1)Descrizione | 2)Profilo Facebook | 3)Profilo Instagram");
                    try {
                        input = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    command = Integer.parseInt(input);
                    System.out.println("Inserire nuovi parametri");
                    try {
                        input = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    boolean flag=true;
                    if(command > 1){
                        flag=ImportUrl.controlliUrl(input,input,"",command-1);
                    }
                    if(flag) {
                        switch (command) {
                            case 1:
                                utente.setBDescrizione(input);
                                System.out.println("New description: " + utente.getBDescrizione() + "\n");
                                break;
                            case 2:
                                utente.setBFb(input);
                                System.out.println("New facebook: " + utente.getBFb() + "\n");
                                break;
                            default:
                                utente.setBIg(input);
                                System.out.println("New instagram: " + utente.getBIg() + "\n");
                                break;
                        }
                        save();
                    }
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
                    default :
                        try {
                            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + utente.getBIg()});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }while(true);
    }

    public static void save(){
        UserProfileAppController.setInfo(utente);
    }



}
