package com.example.optic.second_ui;

import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.entities.Admin;
import com.example.optic.utilities.ImportCheckInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PlayerHomeCLI {
    private static BookSessionAppController bookSessionAppController = new BookSessionAppController();

    private PlayerHomeCLI(){
        //does nothing
    }

    public static void main(String user){

        int command;
        do {
            command=acquisisci();
            switch (command) {
                case 2 -> {
                    showPlaygroundList(user);
                }
                case 3 -> {
                    return;
                }
                default -> PlayerProfileCLI.main(user);
            }
        }while (true);
    }
    public static int acquisisci(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        String input;
        int command = 1;
        try {
            do {
                System.out.println("|Home|\n1)Profilo | 2)Lista Campi | 3)Logout");

                    input = br.readLine();

                    res = ImportCheckInput.isNumber(input);

                //controllo se l'input è corretto
                if (!res) {
                    if (!BaseCommandCLI.exit(input)) {
                        res = ImportCheckInput.checkInput(input);
                        if (!res) {
                            System.out.println("\nComando non valido!");
                        }
                    }

                } else {
                    command = Integer.parseInt(input);
                }
            } while (!res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return command;
    }

    private static void showPlaygroundList(String user) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Admin> list = bookSessionAppController.getCampi();
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
                }else{
                    System.out.println("Comando non valido!");
                }
            } while (!res2);
            if(campo != -1){
                UserPGPageCLI.main(user, list.get(campo).getNomeC());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
