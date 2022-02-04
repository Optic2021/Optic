package com.example.optic.second_ui;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Event;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.utilities.ImportCheckInput;
import com.example.optic.utilities.ImportUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class PlayerProfileCLI {
    private static PlayerBean utente=new PlayerBean();

    private PlayerProfileCLI(){/*does np*/}

    public static boolean back(String command) {
        boolean flag=false;
        if (command.equalsIgnoreCase("back") || command.equalsIgnoreCase("indietro")) {
            flag=true;
        }
        return flag;
    }

    public static void main(String user){
        utente.setBUsername(user);
        Player player;
        player = UserProfileAppController.getPlayer(utente);
        utente.setBIg(player.getIg());
        utente.setBFb(player.getFb());
        utente.setBDescrizione(player.getDescrizione());
        int command;
        do {
            System.out.println("Exit-------Home-------Back");
            System.out.println("User: " + user + "\nDescription :" + utente.getBDescrizione());
            System.out.println("Modifica--------------------------------");
            System.out.println("1 Facebook------------------------------");
            System.out.println("2 Instagram-----------------------------");
            System.out.println("3 Vedi report---------------------------");
            System.out.println("4 Lista Eventi--------------------------");
            System.out.println("5 Vedi Storico Partite------------------");
            String input = null;
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = bufferReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean res = ImportCheckInput.isNumber(input);
            //controllo se l'input è numero
            if (!res) {
                if (BaseCommandCLI.exit(input)) {
                    return;
                } else if (back(input)) {
                    return;
                }else if(input.equals("Modifica")){
                    System.out.println("1)Descrizione \n2)Profilo Facebook \n3)Profilo Instagram");
                    try {
                        input = bufferReader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    command = Integer.parseInt(input);
                    System.out.println("Inserire nuovi parametri");
                    try {
                        input = bufferReader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    boolean flag=true;
                    if(command > 1){
                        flag=ImportUrl.controlliUrl(input,input,"",command-1);
                    }
                    if(flag) {
                        switch (command) {
                            case 1:
                                utente.setBDescrizione(input);
                                System.out.println("New description: " + utente.getBDescrizione() + "\n");
                                break;
                            case 2:
                                utente.setBFb(input);
                                System.out.println("New facebook: " + utente.getBFb() + "\n");
                                break;
                            default:
                                utente.setBIg(input);
                                System.out.println("New instagram: " + utente.getBIg() + "\n");
                                break;
                        }
                        save();
                    }
                }
            } else {
                command=Integer.parseInt(input);
                switch (command) {
                    //apri facebook
                    case 1 :
                        try {
                            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + utente.getBFb()});
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                        //apri insta
                    case 2 :
                        try {
                            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + utente.getBIg()});
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 3 : showReport();break;
                    case 4 : showEventList();break;
                    default : showRecentPlays();

                }
            }
        }while(true);
    }

    public static void save(){
        UserProfileAppController.setInfo(utente);
    }

    public static void showRecentPlays(){
        UserBean userBean = new UserBean();
        userBean.setUsername(utente.getBUsername());
        List<Giornata> list = UserProfileAppController.getRecentPlayList(userBean);
        if(list.isEmpty()){
            System.out.println("Non ci sono partite recenti da mostrare.");
        }else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("Data: "+list.get(i).getDataString()+" | Campo: "+list.get(i).getNomeC()+"\n");
            }
        }
    }

    public static void showEventList(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean ris;
        List<Event> list;
        list = UserProfileAppController.getEventList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getFormattedText()+"\n");
        }
        try {
            do {
                System.out.println("Inserire un numero per tornare indietro");
                String input;
                input = br.readLine();
                ris = ImportCheckInput.checkInput(input);
                //controllo se l'input è corretto
                if (!ris) {
                    System.out.println("Comando non valido!");
                }
            } while (!ris);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void showReport(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        List<ReportBean> list;
        list = UserProfileAppController.getReportList(utente.getBUsername());
        if(list.isEmpty()){
            System.out.println("Non ci sono report da mostrare.");
        }else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFormattedText()+"\n");
            }
        }
        try {
            do {
                System.out.println("Inserire un numero per tornare indietro");
                String input2;
                input2 = br.readLine();
                res = ImportCheckInput.checkInput(input2);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println("Comando non valido!");
                }
            } while (!res);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
