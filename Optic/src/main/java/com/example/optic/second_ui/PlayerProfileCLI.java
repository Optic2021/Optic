package com.example.optic.second_ui;

import com.example.optic.app_controllers.UserProfileAppController;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.Event;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.utilities.EmptyReportListException;
import com.example.optic.utilities.ImportCheckInput;
import com.example.optic.utilities.ImportUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class PlayerProfileCLI {
    private static PlayerBean userBean=new PlayerBean();
    private static UserProfileAppController userProfileAppController = new UserProfileAppController();

    private PlayerProfileCLI(){/*does np*/}

    public static boolean back(String command) {
        boolean flag=false;
        if (command.equalsIgnoreCase("back") || command.equalsIgnoreCase("indietro")) {
            flag=true;
        }
        return flag;
    }

    public static void main(String user){
        userBean.setUsername(user);
        Player player;
        player = userProfileAppController.getPlayer(userBean);
        userBean.setBIg(player.getIg());
        userBean.setBFb(player.getFb());
        userBean.setBDescrizione(player.getDescrizione());
        try {
            do {
                System.out.println("Exit-------Home-------Back");
                System.out.println("User: " + user + "\nDescription :" + userBean.getBDescrizione());
                System.out.println("Modifica--------------------------------");
                System.out.println("1 Facebook------------------------------");
                System.out.println("2 Instagram-----------------------------");
                System.out.println("3 Vedi report---------------------------");
                System.out.println("4 Lista Eventi--------------------------");
                System.out.println("5 Vedi Storico Partite------------------");
                String input = null;
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
                input = bufferReader.readLine();
                boolean res = ImportCheckInput.isNumber(input);
                //controllo se l'input è numero
                if (!res) {
                    if (BaseCommandCLI.exit(input)) {
                        System.out.println("Chiusura app");
                    }
                    inputIsNotNumber(input);
                } else {
                    inputIsNumber(input);
                }
            } while (true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void inputIsNotNumber(String input) {
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (back(input)) {
                System.out.println("Indietro");
            } else if (input.equals("Modifica")) {
                System.out.println("1)Descrizione \n2)Profilo Facebook \n3)Profilo Instagram");
                input = bufferReader.readLine();
                int command = Integer.parseInt(input);
                System.out.println("Inserire nuovi parametri");
                input = bufferReader.readLine();
                boolean flag = true;
                if (command > 1) {
                    flag = ImportUrl.controlliUrl(input, input, "", command - 1);
                }
                if (flag) {
                    modInfoPlayer(command, input);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void modInfoPlayer(int command, String input) {
        switch (command) {
            case 1:
                userBean.setBDescrizione(input);
                System.out.println("New description: " + userBean.getBDescrizione() + "\n");
                break;
            case 2:
                userBean.setBFb(input);
                System.out.println("New facebook: " + userBean.getBFb() + "\n");
                break;
            default:
                userBean.setBIg(input);
                System.out.println("New instagram: " + userBean.getBIg() + "\n");
                break;
        }
        save();
    }

    private static void inputIsNumber(String input) {
        int command = Integer.parseInt(input);
        try {
            switch (command) {
                //apri facebook
                case 1:
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + userBean.getBFb()});
                    break;
                //apri insta
                case 2:
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + userBean.getBIg()});
                    break;
                case 3:
                    showReport();
                    break;
                case 4:
                    showEventList();
                    break;
                default:
                    showRecentPlays();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void save(){
        userProfileAppController.setInfo(userBean);
    }

    public static void showRecentPlays(){
        UserBean bean = new PlayerBean();
        bean.setUsername(userBean.getUsername());
        List<Giornata> list = userProfileAppController.getRecentPlayList(bean);
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
        list = userProfileAppController.getEventList();
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
        try{
            list = userProfileAppController.getReportList(userBean.getUsername());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFormattedText()+"\n");
            }

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
        }catch (EmptyReportListException e){
            System.out.println("Non ci sono report da mostrare.\n");
        }
    }

}
