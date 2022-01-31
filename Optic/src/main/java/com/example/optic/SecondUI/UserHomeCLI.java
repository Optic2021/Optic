package com.example.optic.SecondUI;

import com.example.optic.utilities.ImportCheckInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserHomeCLI {
    public static void main(String user){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        int command = 0;
        String input;
        try{
            do {
                System.out.println("|Optic|\n1)Profilo | 2)Lista Campi | 3)Logout");
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println("\nComando non valido!");
                }else{
                    command = Integer.parseInt(input);
                }
            }while (!res);
            switch (command){

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
