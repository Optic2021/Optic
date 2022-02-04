package com.example.optic;

import com.example.optic.second_ui.*;
import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.utilities.ImportCheckInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Optic2UI {

    private Optic2UI(){/*does np*/}

    public static void main2(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            int type = 0;
            boolean typeRes;
            boolean res = false;
            do {
                    System.out.println("|Login|\n1)Player | 2)Admin | 3)Referee | 4)Registrati | 5)Esci");
                    input = br.readLine();
                    typeRes = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!typeRes) {
                        System.out.println("Tipo di user non valido!");
                        return;
                    }
                    type = Integer.parseInt(input);
                    String []uspass=new String[2];
                    switch (type) {
                        case 2 -> {
                            uspass=getUsPass();
                            AdminBean a = new AdminBean();
                            a.setUsername(uspass[0]);
                            a.setPassword(uspass[1]);
                            res = LoginController.adminLogin(a);
                        }
                        case 3 -> {
                            uspass=getUsPass();
                            RefereeBean r = new RefereeBean();
                            r.setUsername(uspass[0]);
                            r.setPassword(uspass[1]);
                            res = LoginController.refereeLogin(r);
                        }
                        case 4 -> {
                            RegisterCLI.main();
                            res = true;
                        }
                        case 1 -> {
                            uspass=getUsPass();
                            PlayerBean p = new PlayerBean();
                            p.setBUsername(uspass[0]);
                            p.setBPassword(uspass[1]);
                            res = LoginController.playerLogin(p);
                        }
                        default -> {
                            System.exit(0);
                        }
                    }
                    if (!res) {
                        System.out.println("ATTENZIONE : Valori inseriti non validi");
                    }else {
                        switch (type) {
                            case 1 -> PlayerHomeCLI.main(uspass[0]);
                            case 2 -> ModPGPageCLI.main(uspass[0]);
                            case 3 -> RefereeReportCLI.main(uspass[0]);
                            default -> System.exit(0);
                        }
                    }
            }while(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String[] getUsPass(){
        String []ret=new String[2];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Inserisci Username : ");
        try {
            ret[0] = br.readLine();
            System.out.println("Inserisci Password : ");
            ret[1] = br.readLine();
        }catch (IOException e) {
        e.printStackTrace();
        }

        return ret;
    }
}
