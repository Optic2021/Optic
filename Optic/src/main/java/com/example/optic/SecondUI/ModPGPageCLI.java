package com.example.optic.SecondUI;

import com.example.optic.app_controllers.ModPGPageAppController;
import com.example.optic.app_controllers.ReviewAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.*;
import com.example.optic.utilities.ImportCheckInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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
                    System.out.println("|Profilo campo|\n1)Info campo | 2)Lista giornate  | 3)Aggiungi Giornata | 4)Modifica info | 5)Vedi recensioni | 6)Indietro");
                    input = br.readLine();
                    res = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!res) {
                        System.out.println("Comando non valido!");
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
                            System.out.println("Non ci sono giornate da mostrare.");
                        }
                    }
                    case 3 -> addGiornata(user);
                    case 4 -> modInfo(a);
                    case 5 -> showReviews(a);
                    case 6 -> {
                        System.out.println("Indietro");
                        return;
                    }
                    default -> showInfo(a);
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
            System.out.println("Il campo non ha recensioni.");
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
                    System.out.println("1)Vedi Giocatori | 2)Giornata precedente | 3)Prossima Giornata | 4)Indietro");
                    String input;
                    input = br.readLine();
                    res = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!res) {
                        System.out.println("Comando non valido!");
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
                        } else {
                            showPlayList(admin, play2);
                        }
                    }
                    case 3 -> {
                        Giornata play2 = ModPGPageAppController.getNextPlay(playBean);
                        if (play2 == null) {
                            showPlayList(admin, play);
                        } else {
                            showPlayList(admin, play2);
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
        boolean res;
        System.out.println("Selezionare la modifica da effettuare: \n1)Descrizione | 2)Arbitro | 3)Facebook | 4)Instagram | 5)Whatsapp");

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
                        System.out.println("Input non valido!");
                    }
                } while (!res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showInfo(Admin admin) {
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
                    System.out.println("Comando non valido!");
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
            }else{
                System.out.println(cosa[i]+" non valido");
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
        }while(flag=false);
        ModPGPageAppController.insertPlay(bean);
    }

    /*public static void setYMD(Calendar data){
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
            }else{
                System.out.println(cosa[i]+" non valido");
                i--;
            }
        }
        data.set(ymd[3],ymd[2],ymd[1]);
    }*/
}
