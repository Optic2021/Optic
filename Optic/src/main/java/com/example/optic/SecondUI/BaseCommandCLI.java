package com.example.optic.SecondUI;

import javafx.application.Platform;

public abstract class BaseCommandCLI {
    public static boolean exit(String command){
        boolean flag=false;
        if(command.equals("Esci")||command.equals("Exit")||command.equals("esci")||command.equals("exit")) {
            System.exit(0);
        }
        return flag;
    }

    //public abstract void back(String command);
}
