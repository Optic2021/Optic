package com.example.optic.SecondUI;

import com.example.optic.Optic2UI;
import com.example.optic.app_controllers.RegisterController;
import com.example.optic.bean.UserBean;
import com.example.optic.utilities.ImportCheckInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class RegisterCLI {

    public static void main(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            UserBean bean = new UserBean();
            String input;
            String user;
            String password = new String();
            int type = 0;
            boolean typeRes;
            boolean res;
            do {
                do {
                    System.out.println("|Registrazione|\n1)Player | 2)Admin | 3)Referee | 4)Login | 5)Esci");
                    input = br.readLine();
                    typeRes = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!typeRes) {
                        System.out.println("Input non valido!");
                    } else {
                        type = Integer.parseInt(input);
                        if(type == 4){
                            Optic2UI.main2();
                        }
                        if(type == 5){
                            System.exit(0);
                        }
                    }
                } while (!typeRes);
                System.out.println("Inserisci Username : ");
                user = br.readLine();
                System.out.println("Inserisci Password : ");
                password = br.readLine();
                bean.setUsername(user);
                bean.setPassword(password);
                switch (type) {
                    //registrazione admin
                    case 2 -> {
                        String via;
                        String nomeC;
                        String prov;
                        System.out.println("Inserisci Via : ");
                        via = br.readLine();
                        System.out.println("Inserisci Nome Campo : ");
                        nomeC = br.readLine();
                        System.out.println("Inserisci Provincia : ");
                        prov = br.readLine();
                        bean.setVia(via);
                        bean.setNomeC(nomeC);
                        bean.setProv(prov);
                        res = RegisterController.isUsernameUsed(bean,2);
                    }
                    //registrazione arbitro
                    case 3 -> {
                        res = RegisterController.isUsernameUsed(bean,3);
                    }
                    //registrazione player
                    default -> {
                        res = RegisterController.isUsernameUsed(bean,1);
                    }
                }
                if (res) {
                    System.out.println("Username già in utilizzo!");
                }
            } while (res);
            RegisterController.userRegister(bean, type);
            System.out.println("Registrazione avvenuta con successo.");
            switch (type) {
                case 2 -> ModPGPageCLI.main(user);
                case 3 ->  RefereeReport.main(user);
                default -> PlayerHomeCLI.main(user);
            }
        }catch (IOException|SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
