package com.example.optic.second_ui;

public abstract class BaseCommandCLI {

    public static boolean exit(String command){
        boolean flag=false;
        command=command.toLowerCase();
        if(command.equals("esci")||command.equals("exit")) {
            System.exit(0);
        }
        return flag;
    }

}
