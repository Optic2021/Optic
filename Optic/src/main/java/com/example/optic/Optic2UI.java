package com.example.optic;

/*System.out.println("Inizializzando ");
        Console console = System.console();
        String s= console.readLine();
        int i=Integer.parseInt(console.readLine());*/

import com.example.optic.SecondUI.UserHomeCLI;
import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.utilities.ImportCheckInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Optic2UI {

    public static void main2(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String accStr;
            String input;
            String user = new String();
            String password = new String();
            int type = 0;
            boolean typeRes;
            boolean res;
            do {
                do {
                    System.out.println("|Login|\n1)Player | 2)Referee | 3)Admin");
                    input = br.readLine();
                    typeRes = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!typeRes) {
                        System.out.println("Tipo di user non valido!");
                    } else {
                        type = Integer.parseInt(input);
                    }
                } while (!typeRes);
                System.out.println("Inserisci Username : ");
                user = br.readLine();
                System.out.println("Inserisci Password :");
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
                    default -> {
                        PlayerBean p = new PlayerBean();
                        p.setBUsername(user);
                        p.setBPassword(password);
                        res = LoginController.playerLogin(p);
                    }
                }
                if (!res) {
                    System.out.println("Credenziali errate! ");
                }
            } while (!res);
            switch (type) {
                case 2 -> System.out.println("Pagina campo");
                case 3 -> System.out.println("Pagina arbitro");
                default -> UserHomeCLI.main(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
