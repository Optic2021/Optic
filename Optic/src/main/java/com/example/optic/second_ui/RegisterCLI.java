package com.example.optic.second_ui;

import com.example.optic.Optic2UI;
import com.example.optic.app_controllers.RegisterController;
import com.example.optic.bean.Factory;
import com.example.optic.bean.UserBean;
import com.example.optic.utilities.ImportCheckInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class RegisterCLI {
    private static RegisterController registerController;

    private RegisterCLI(){/*does np*/}

    public static void main(){
        try {
            registerController = new RegisterController();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            UserBean bean= null;
            String input;
            String user = null;
            String password;
            int type = 0;
            boolean res2;
            boolean res = true;
            do {
                do {
                    System.out.println("|Registrazione|\n1)Player | 2)Admin | 3)Referee | 4)Login | 5)Esci");
                    input = br.readLine();
                    res2 = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!res2) {
                        System.out.println("Input non valido!");
                    }
                } while (!res2);
                type = Integer.parseInt(input);
                if(type == 1 || type == 2 || type == 3) {
                    bean = Factory.createUser(type);
                    System.out.println("Inserisci Username : ");
                    user = br.readLine();
                    System.out.println("Inserisci Password : ");
                    password = br.readLine();
                    bean.setUsername(user);
                    bean.setPassword(password);
                }

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
                        res = registerController.isUsernameUsed(user,2);
                    }
                    //registrazione arbitro
                    case 3 -> {
                        res = registerController.isUsernameUsed(user,3);
                    }
                    case 4 -> Optic2UI.main2();
                    case 5 -> System.exit(0);
                    //registrazione player
                    default -> {
                        res = registerController.isUsernameUsed(user,1);
                    }
                }
                if (res) {
                    System.out.println("Username già in utilizzo!");
                }
            } while (res);
            registerController.userRegister(bean, type);
            System.out.println("Registrazione avvenuta con successo.");
            switch (type) {
                case 2 -> ModPGPageCLI.main(user);
                case 3 ->  RefereeReportCLI.main(user);
                default -> PlayerHomeCLI.main(user);
            }
        }catch (IOException|SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
