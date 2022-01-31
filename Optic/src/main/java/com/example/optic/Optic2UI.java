package com.example.optic;

/*System.out.println("Inizializzando ");
        Console console = System.console();
        String s= console.readLine();
        int i=Integer.parseInt(console.readLine());*/

import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Optic2UI {

    public static void main2(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String accStr;
        String user = new String();
        String password = new String();
        int type = 0;
        try {
            System.out.println("|Optic|\n1)Player | 2)Referee | 3)Admin");
            type = Integer.parseInt(br.readLine());
            System.out.println("Login \nInserisci Username : ");
            user = br.readLine();
            System.out.println("\nInserisci Password :");
            password = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean res=false;
        do {
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
        }while(!res);
        System.out.println("User "+user+" pass "+password);
    }

}
