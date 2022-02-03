package com.example.optic.SecondUI;

import com.example.optic.app_controllers.ModPGPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.*;
import com.example.optic.utilities.ImportCheckInput;
import com.example.optic.utilities.ImportUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

public class ModPGPageCLI extends BaseCommandCLI {

    public static void main(String user) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean res;
        int command = 0;
        AdminBean admin = new AdminBean();
        admin.setUsername(user);
        Admin a = ModPGPageAppController.getAdmin(admin);
        try {
            do {
                do {
                    System.out.println("|Profilo campo|\n1)Info campo \n2)Lista giornate  \n3)Aggiungi Giornata \n4)Modifica info \n5)Vedi recensioni \n6)Indietro");
                    input = br.readLine();
                    res = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!res) {
                        System.out.println("ATTENZIONE: Comando non valido!");
                    } else {
                        command = Integer.parseInt(input);
                    }
                } while (!res);
                switch (command) {
                    case 2 -> {
                        UserBean bean = new UserBean();
                        bean.setUsername(a.getUsername());
                        Giornata play = ModPGPageAppController.getFirstPlay(bean);
                        if (play == null) {
                            play = ModPGPageAppController.getRecentPlay(bean);
                        }
                        if (play != null) {
                            showPlayList(a.getUsername(), play);
                        } else {
                            System.out.println("ATTENZIONE: Non ci sono giornate da mostrare.");
                        }
                    }
                    case 3 -> addGiornata(user);
                    case 4 -> modInfo(a);
                    case 5 -> showReviews(a);
                    case 6 -> {
                        System.out.println("Indietro");
                        return;
                    }
                    default -> showInfo(a.getUsername());
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showReviews(Admin admin) {
        AdminBean bean = new AdminBean();
        bean.setUsername(admin.getUsername());
        List<Valutazione> list = ModPGPageAppController.getReviewList(bean);
        int stelleTot = 0;
        int media;
        int val = 0;
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFkUsernameP1() + ": " + "\n" + list.get(i).getDescrizione());
                stelleTot += list.get(i).getStelle();
                val++;
            }
            media = stelleTot / val;
            System.out.println("Media stelle: " + media);
        } else {
            System.out.println("ATTENZIONE: Il campo non ha recensioni.");
        }
    }

    public static void showPlayList(String admin, Giornata play) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        int command = 0;
        GiornataBean playBean = new GiornataBean();
        playBean.setAdmin(admin);
        playBean.setData(play.getData());
        try {
            do {
                do {
                    System.out.println("Data: " + play.getDataString() + "\nAttività: " + play.getFkNome());
                    System.out.println("1)Vedi Giocatori\n4)Indietro");
                    System.out.println("2)Precedente                   Successiva(3");
                    String input;
                    input = br.readLine();
                    res = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!res) {
                        System.out.println("ATTENTIONE: Comando non valido!");
                    } else {
                        command = Integer.parseInt(input);
                    }
                } while (!res);
                switch (command) {
                    case 1 -> showPlayers(play);
                    case 2 -> {
                        Giornata play2 = ModPGPageAppController.getLastPlay(playBean);
                        if (play2 == null) {
                            showPlayList(admin, play);
                            return;
                        } else {
                            showPlayList(admin, play2);
                            return;
                        }
                    }
                    case 3 -> {
                        Giornata play2 = ModPGPageAppController.getNextPlay(playBean);
                        if (play2 == null) {
                            showPlayList(admin, play);
                            return;
                        } else {
                            showPlayList(admin, play2);
                            return;
                        }
                    }
                    default -> {
                        System.out.println("Indietro");
                        return;
                    }
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modInfo(Admin admin) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        AdminBean a = new AdminBean();
        a.setUsername(admin.getUsername());
        boolean res;
        int command;
        String input;
        try{
            do{
                System.out.println("Selezionare la modifica da effettuare: \n1)Descrizione \n2)Arbitro \n3)Social");
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                if(!res){
                    System.out.println("Comando non valido!");
                }
            }while(!res);
            command = Integer.parseInt(input);
            switch(command){
                case 2 -> modReferee(admin);
                case 3 -> modSocial(admin);
                default -> modDesc(a);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void modReferee(Admin a){

    }

    public static void modSocial(Admin a){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        AdminBean bean = new AdminBean();
        bean.setUsername(a.getUsername());
        bean.setFaceb(a.getFb());
        bean.setInsta(a.getIg());
        bean.setWhats(a.getWa());
        boolean res;
        int command;
        String input;
        try{
            do{
                System.out.println("Social da modificare: \n1)Facebook \n2)Instagram \n3)Whatsapp");
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                if(!res){
                    System.out.println("Comando non valido!");
                }
            }while(!res);
            command = Integer.parseInt(input);
            System.out.println("Inserire nuovo parametro: ");
            input = br.readLine();
            switch(command){
                case 2 :
                    res = ImportUrl.controlliUrl(input,a.getFb(),a.getWa(),2);
                    bean.setInsta(input);
                    break;
                case 1 :
                    res = ImportUrl.controlliUrl(a.getIg(),input,a.getWa(),1);
                    bean.setFaceb(input);
                    break;
                default :
                    res = ImportUrl.controlliUrl(a.getIg(),a.getFb(),input,3);
                    bean.setWhats(input);
            }
            if(res){
                ModPGPageAppController.setAdminSocial(bean);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void modDesc(AdminBean a){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println("Inserire una nuova descrizione: ");
        try {
            input = br.readLine();
            a.setDescrizione(input);
            ModPGPageAppController.setDescription(a);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void showPlayers(Giornata play) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        String input;
        int command = 0;
        GiornataBean bean = new GiornataBean();
        bean.setIdPlay(play.getIdGiornata());
        List<Player> list = ModPGPageAppController.getPlayersList(bean);
        if (!list.isEmpty()) {
            try {
                do {
                    int num = list.size();
                    System.out.println("Numero Giocatori: " + num);
                    for (int i = 1; i < num + 1; i++) {
                        System.out.println(i + ")" + list.get(i - 1).getUsername());
                    }
                    System.out.println("Digitare un numero per tornare indietro");
                    input = br.readLine();
                    res = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!res) {
                        System.out.println("ATTENZIONE:Input non valido!");
                    }
                } while (!res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showInfo(String user) {
        AdminBean bean = new AdminBean();
        bean.setUsername(user);
        Admin admin = ModPGPageAppController.getAdmin(bean);
        System.out.println(admin.getNomeC() + "\nDescrizione: " + admin.getDescrizioneC() + "\nAdmin: " + admin.getUsername() + "\nVia: " + admin.getVia() + "\nProvincia: " + admin.getProvincia());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        try {
            do {
                System.out.println("Inserire un numero per tornare indietro");
                String input;
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println("ATTENZIONE: Comando non valido!");
                }
            } while (!res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addGiornata(String user) {
        int inp;
        Boolean flag;
        Calendar data=Calendar.getInstance();
        //
        String inputt = new String();
        BufferedReader brt = new BufferedReader(new InputStreamReader(System.in));
        GiornataBean bean = new GiornataBean();

        String input1= new String();
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        int ymd[] = new int[4];
        String cosa[]={" ","Giorno","Mese","Anno"};
        for(int i=1;i<4;i++) {
            System.out.println("Inserisci "+cosa[i]);
            try {
                input1 = br1.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (ImportCheckInput.isNumber(input1)) {
                ymd[i]=Integer.parseInt(input1);
            }else /*if(!isDMYValid(ymd[i],i,ymd[2]))*/{
                System.out.println("ATTENZIONE: 1"+cosa[i]+" non valido");
                i--;
            }
        }
        //Manca il controllo sulla validita
        data.set(ymd[3],ymd[2],ymd[1]);
        bean.setData(data);
        bean.setAdmin(user);
        ModPGPageAppController.isDateValid(bean);
        System.out.println("Seleziona evento ");
        List<Event> list = ModPGPageAppController.getEventList();
        for(int i = 0;i<list.size();i++) {
            System.out.println(i + ") " + list.get(i).getNome());
        }
        flag=false;
        do {
            try {
                inputt = brt.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (ImportCheckInput.isNumber(inputt)) {
                inp = Integer.parseInt(inputt);
                bean.setEvento(list.get(inp).getNome());
                flag=true;
            }else if (inputt.toLowerCase().equals("indietro")){
                return;
            }
        }while(flag==false);
        ModPGPageAppController.insertPlay(bean);
    }

    //CONTROLLARE IL FORMATO DELLA DATA
    /*public static boolean isDMYValid(int dmy, int i,int febbraio){
        boolean flag= true;
        switch (i){
            case 1://giorno
                if(dmy<1){
                    flag=false;
                }
                if(febbraio==2){
                    if (dmy>28){
                        flag=false;
                    }
                }else if (dmy>31){
                    flag=false;
                }
            break;
            case 2://mese
                if (dmy<0 || dmy>12){
                    flag=false;
                }
            break;
            case 3://anno
                if (dmy<2022 && dmy>2100){
                    flag=false;
                }
            break;
        }
        return flag;
    }*/
}
