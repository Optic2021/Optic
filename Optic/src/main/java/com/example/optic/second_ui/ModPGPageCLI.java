package com.example.optic.second_ui;

import com.example.optic.app_controllers.ModPGPageAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.entities.*;
import com.example.optic.utilities.ImportCheckInput;
import com.example.optic.utilities.ImportUrl;
import com.example.optic.utilities.NotARefereeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ModPGPageCLI {

    private ModPGPageCLI(){/*does np*/}

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
                    System.out.println("|Profilo campo|\n1)Info campo \n2)Lista giornate  \n3)Aggiungi Giornata \n4)Modifica info \n5)Vedi recensioni \n6)Lista Eventi \n7)Indietro");
                    input = br.readLine();
                    res = ImportCheckInput.checkInput(input);
                    //controllo se l'input è corretto
                    if (!res) {
                        if (!BaseCommandCLI.exit(input)){
                            System.out.println("ATTENZIONE: Comando non valido!");
                        }
                    } else {
                        command = Integer.parseInt(input);
                    }
                } while (!res);
                switch (command) {
                    case 2 -> {
                        UserBean bean = new UserBean();
                        bean.setUsername(a.getUsername());
                        Giornata play = ModPGPageAppController.getFirstPlay(bean);
                        case2(play, bean);
                    }
                    case 3 -> addGiornata(user);
                    case 4 -> modInfo(a);
                    case 5 -> showReviews(a);
                    case 6 -> showEventList();
                    case 7 -> {
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

    public static void showEventList(){
        List<Event> list = ModPGPageAppController.getEventList();
        for(int i = 0;i<list.size();i++) {
            System.out.println(i + ") " + list.get(i).getNome());
        }
    }

    public static void case2(Giornata play, UserBean bean){
        if (play == null) {
            play = ModPGPageAppController.getRecentPlay(bean);
        }
        if (play != null) {
            showPlayList(bean.getUsername(), play);
        } else {
            System.out.println("ATTENZIONE: Non ci sono giornate da mostrare.");
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
            if (val!=0) {
                media = stelleTot / val;
                System.out.println("Media stelle: " + media);
            }
        } else {
            System.out.println("ATTENZIONE: Il campo non ha recensioni.");
        }
    }

    public static void showPlayList(String admin, Giornata play) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        int command = 0;
        String input2;
        GiornataBean playBean = new GiornataBean();
        playBean.setAdmin(admin);
        playBean.setData(play.getData());
        try {
            do {
                do {
                    System.out.println("Data: " + play.getDataString() + "\nAttività: " + play.getFkNome());
                    System.out.println("1)Vedi Giocatori\n4)Indietro");
                    System.out.println("2)Precedente                   Successiva(3");
                    System.out.println("Inserire un comando valido");
                    input2 = br.readLine();
                    res = ImportCheckInput.checkInput(input2);
                } while (!res);
                command = Integer.parseInt(input2);
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
                case 2 ->{
                    UserBean u=new UserBean();
                    u.setUsername(admin.getUsername());
                    modReferee(admin,ModPGPageAppController.getRefereeFromAdmin(u));
                }
                case 3 -> modSocial(admin);
                default -> modDesc(a);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void modReferee(Admin a, Referee ref){
        System.out.println("1)Cancellare\n2)Modificare");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String input2 = null;
        try {
            input2=br2.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!ImportCheckInput.isNumber(input2)) {
            System.out.println("ATTENZIONE: Immettere un numero");
            return;
        }
        int inp=Integer.parseInt(input2);
        //cancellare
        if (inp == 1) {
            if (ref != null) {
                UserBean u = new UserBean();
                u.setUsername(ref.getUsername());
                freeReferee(u);
            } else {
                System.out.println("ATTENZIONE: Nessuno arbitro collegato!");
            }
        }else {
            try {
                System.out.println("Immettere lo username dell'arbitro da collegare: ");
                input2 = br2.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                UserBean adminBean = new UserBean();
                UserBean refBean = new UserBean();
                refBean.setUsername(input2);
                adminBean.setUsername(a.getUsername());
                //controllo se il referee esiste
                Referee referee = ModPGPageAppController.getReferee(refBean);
                if (referee.getAdminCampo() != null) {//controllo che l'arbitro non sia collegato
                    System.out.println("ATTENZIONE: Arbitro già di un altro campo!");
                } else {
                    System.out.println("Arbitro correttamente collegato.");
                    ModPGPageAppController.setReferee(adminBean, refBean);
                }
            }catch (NotARefereeException e){
                System.out.println("ATTENZIONE: Arbitro inesistente!");
            }
        }
    }

    public static void freeReferee(UserBean u){
        try {
            ModPGPageAppController.getReferee(u);
            System.out.println("Arbitro scollegato correttamente.");
            ModPGPageAppController.freeReferee(u);
        }catch (NotARefereeException e){
            System.out.println("ATTENZIONE: Impossibile scollegare un arbitro inesistente!");
        }
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
        String inp;
        try{
            do{
                System.out.println("Social da modificare: \n1)Facebook \n2)Instagram \n3)Whatsapp");
                inp = br.readLine();
                res = ImportCheckInput.checkInput(inp);
                if(!res){
                    System.out.println("Comando non valido!");
                }
            }while(!res);
            command = Integer.parseInt(inp);
            System.out.println("Inserire nuovo parametro: ");
            inp = br.readLine();
            switch(command){
                case 2 :
                    res = ImportUrl.controlliUrl(inp,a.getFb(),a.getWa(),2);
                    bean.setInsta(inp);
                    break;
                case 1 :
                    res = ImportUrl.controlliUrl(a.getIg(),inp,a.getWa(),1);
                    bean.setFaceb(inp);
                    break;
                default :
                    res = ImportUrl.controlliUrl(a.getIg(),a.getFb(),inp,3);
                    bean.setWhats(inp);
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
        UserBean admbean = new UserBean();
        admbean.setUsername(user);
        Referee appoggio = ModPGPageAppController.getRefereeFromAdmin(admbean);
        String param = "Nessuno ";
        if (appoggio != null) {
            param = appoggio.getUsername();
        }
        System.out.println(admin.getNomeC() + "\nDescrizione: " + admin.getDescrizioneC() + "\nAdmin: " + admin.getUsername() + "\nVia: " + admin.getVia() + "\nProvincia: " + admin.getProvincia() + "\nArbitro : " + param);
    }

    public static void addGiornata(String user) {
        int inp;
        Boolean flag = true;
        Calendar data=Calendar.getInstance();
        String inputt = null;
        BufferedReader brt = new BufferedReader(new InputStreamReader(System.in));
        GiornataBean bean = new GiornataBean();

        try {
            String input1 = null;
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            int[] ymd = new int[4];
            String[] cosa = {" ", "Giorno", "Mese", "Anno"};
            for (int i = 1; i < 4; i++) {
                System.out.println("Inserisci " + cosa[i]);
                input1 = br1.readLine();
                if (ImportCheckInput.isNumber(input1)) {
                    ymd[i] = Integer.parseInt(input1);
                } else {
                    System.out.println("ATTENZIONE: 1" + cosa[i] + " non valido");
                    return;
                }
            }
            //Manca il controllo sulla validita
            data.set(ymd[3], ymd[2], ymd[1]);
            bean.setData(data);
            bean.setAdmin(user);
            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setLenient(false);
            df.parse(Integer.toString(ymd[1]) + "-" + Integer.toString(ymd[2]) + "-" + Integer.toString(ymd[3]));

            System.out.println("Valida " + Integer.toString(ymd[1]) + "-" + Integer.toString(ymd[2]) + "-" + Integer.toString(ymd[3]));
            ModPGPageAppController.isDateValid(bean);
            System.out.println("Seleziona evento ");
            List<Event> list = ModPGPageAppController.getEventList();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ") " + list.get(i).getNome());
            }
            flag = false;
            do {
                inputt = brt.readLine();
                if (ImportCheckInput.isNumber(inputt)) {
                    inp = Integer.parseInt(inputt);
                    bean.setEvento(list.get(inp).getNome());
                    flag = true;
                } else if (inputt.equalsIgnoreCase("indietro")) {
                    return;
                }
            } while (Boolean.FALSE.equals(flag));
            ModPGPageAppController.insertPlay(bean);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            System.out.println("ATTENZIONE: formato data non valido");
        }
    }

    //CONTROLLARE IL FORMATO DELLA DATA

}
