package com.example.optic.SecondUI;

import com.example.optic.Optic2UI;
import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.entities.Admin;
import com.example.optic.utilities.ImportCheckInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PlayerHomeCLI {
    public static void main(String user){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        int command = 1;
        String input;
        try{
            do {
                System.out.println("|Home|\n1)Profilo | 2)Lista Campi | 3)Logout");
                input = br.readLine();
                res = ImportCheckInput.isNumber(input);
                //controllo se l'input è corretto
                if (!res) {
                    if(!BaseCommandCLI.exit(input)){
                        res = ImportCheckInput.checkInput(input);
                        if(!res){
                            System.out.println("\nComando non valido!");
                        }
                    }

                }else{
                    command = Integer.parseInt(input);
                }
            }while (!res);
            switch (command) {
                case 2 -> showPlaygroundList(user);
                case 3 -> logout();
                default -> PlayerProfileCLI.main(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void showPlaygroundList(String user) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Admin> list = BookSessionAppController.getCampi();
            String input2;
            int campo = 0;
            boolean res2 = false;
            System.out.println("Nome Campo  /  Provincia");
            for (int i = 1; i < list.size()+1; i++) {
                System.out.println(i + ") " + list.get(i-1).getNomeC() + "   |   " + list.get(i-1).getProvincia());
            }
            do {
                System.out.println("Inserire il numero del campo da visitare oppure 0 per tornare indietro");
                input2 = br.readLine();
                res2 = ImportCheckInput.isNumber(input2);
                if (res2) {
                    campo = Integer.parseInt(input2)-1;
                    if (campo >= list.size()) {
                        res2 = false;
                    }
                }
                if (!res2) {
                    System.out.println("Comando non valido!");
                }
            } while (!res2);
            if(campo == -1){
                PlayerHomeCLI.main(user);
            }else {
                UserPGPageCLI.main(user, list.get(campo).getNomeC());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void logout() {
        Optic2UI.main2();
    }
}
