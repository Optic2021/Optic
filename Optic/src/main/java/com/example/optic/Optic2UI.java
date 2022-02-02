package com.example.optic;
import com.example.optic.SecondUI.BaseCommandCLI;
import com.example.optic.SecondUI.PlayerHomeCLI;
import com.example.optic.SecondUI.RefereeReport;
import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.utilities.ImportCheckInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Optic2UI extends BaseCommandCLI {

    public static void main2(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String accStr;
            String input;
            String user = new String();
            String password = new String();
            int type = 0;
            boolean typeRes;
            boolean res = false;
            do {
                do {
                    System.out.println("|Login|\n1)Player | 2)Admin | 3)Referee | 4)Esci");
                    input = br.readLine();
                    typeRes = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!typeRes) {
                        System.out.println("Tipo di user non valido!");
                    } else {
                        type = Integer.parseInt(input);
                        if(type == 4){
                            System.exit(0);
                        }
                    }
                } while (!typeRes);
                System.out.println("Inserisci Username : ");
                user = br.readLine();
                System.out.println("Inserisci Password : ");
                password = br.readLine();
                switch (type) {
                    case 2 -> {
                        AdminBean a = new AdminBean();
                        a.setUsername(user);
                        a.setPassword(password);
                        res = LoginController.adminLogin(a);
                    }
                    case 3 -> {
                        RefereeBean r = new RefereeBean();
                        r.setUsername(user);
                        r.setPassword(password);
                        res = LoginController.refereeLogin(r);
                    }
                    case 1 -> {
                        PlayerBean p = new PlayerBean();
                        p.setBUsername(user);
                        p.setBPassword(password);
                        res = LoginController.playerLogin(p);
                    }
                    default -> {
                        System.exit(0);
                    }
                }
                if (!res) {
                    System.out.println("Credenziali errate! ");
                }
            } while (!res);
            switch (type) {
                case 2 -> System.out.println("Pagina campo");
                case 3 -> {
                    RefereeReport.main(user);
                    System.out.println("torno");
                }
                default -> PlayerHomeCLI.main(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return;
    }

}
