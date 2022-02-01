package com.example.optic.SecondUI;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportCheckInput;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserProfileCLI extends BaseCommandCLI{
    private static PlayerBean utente=new PlayerBean();

    public static boolean back(String command) {
        boolean flag=false;
        if (command.equals("Back") || command.equals("back") || command.equals("Indietro") || command.equals("indietro")) {
            UserHomeCLI.main(utente.getBUsername());
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
        do {
            System.out.println("Exit|Esci-------Home-------Back|Indietro");
            System.out.println("User: " + user + "\nDescription :" + utente.getBDescrizione());
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
                } else if (!back(input)) {
                  if(input.equals("save")){
                      save();
                  }
                }
            } else {
                int command = Integer.parseInt(input);
                System.out.println("Inserire nuovi parametri");
                try {
                    input = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (command) {
                    case 1 : utente.setBDescrizione(input);
                            System.out.println("New description: "+utente.getBDescrizione());
                    break;
                    case 2 : utente.setBFb(input);
                            System.out.println("New facebook: "+utente.getBFb());
                    break;
                    case 3 : utente.setBIg(input);
                            System.out.println("New instagram: "+utente.getBIg());
                    break;
                }
                save();
            }
        }while(true);
    }

    public static void save(){
        UserProfileAppController.setInfo(utente);
    }



}
