package com.example.optic.second_ui;

import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.app_controllers.ReviewAppController;
import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;
import com.example.optic.utilities.ImportCheckInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.List;

public class UserPGPageCLI {
    private UserPGPageCLI(){/*does np*/}
    private static String err = "ATTENZIONE : Comando non valido!";
    public static void main(String user,String nomeC){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean res;
        int command = 0;
        AdminBean campo = new AdminBean();
        campo.setNomeCampo(nomeC);
        AdminBean a = BookSessionAppController.getCampoInfo(campo);
        try{
            do {
                System.out.println("|Profilo campo|\n1)Info campo | 2)Lista giornate | 3)Inserisci recensione | 4)Vedi recensioni | 5)Indietro");
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println(err);
                }else{
                    command = Integer.parseInt(input);
                }
            }while (!res);
            switch (command) {
                case 1 -> showInfo(user,a);
                case 2 -> {
                    UserBean bean = new UserBean();
                    bean.setUsername(a.getUsername());
                    Giornata play = BookSessionAppController.getFirstPlay(bean);
                    if(play == null){
                        play = BookSessionAppController.getRecentPlay(bean);
                    }
                    if(play != null){
                        showPlayList(user,a.getUsername(),play);
                    }else{
                        System.out.println("Non ci sono giornate da mostrare.");
                        UserPGPageCLI.main(user,nomeC);
                    }
                }
                case 3 -> review(user,a);
                case 4 -> showReviews(user,a);
                default -> System.out.println("Indietro");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void showInfo(String user,AdminBean admin){
        System.out.println(admin.getNomeCampo()+"\nDescrizione: "+admin.getDescrizione()+"\nAdmin: "+admin.getUsername()+"\nArbitro: "+admin.getReferee()+"\nVia: "+admin.getVia()+"\nProvincia: "+admin.getProvincia());
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
                    System.out.println(err);
                }
            }while (!res);
            UserPGPageCLI.main(user,admin.getNomeCampo());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void showPlayList(String user,String admin,Giornata play){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        String input;
        int command = 0;
        GiornataBean playBean = new GiornataBean();
        playBean.setAdmin(admin);
        playBean.setData(play.getData());
        try {
            do {
                do {
                    System.out.println("Data: "+play.getDataString()+"\nAttività: "+play.getFkNome());
                    System.out.println("1)Prenota \n2)Vedi Giocatori \n5)Indietro");
                    System.out.println("3)Precedente-----------------Successiva(4");
                    System.out.println("Inserire un comando valido");
                    input = br.readLine();
                    //controllo se l'input è corretto
                    res = ImportCheckInput.checkInput(input);
                } while (!res);
                command = Integer.parseInt(input);
                switch (command) {
                    case 1 -> {
                        UserBean userBean = new UserBean();
                        userBean.setUsername(user);
                        playBean.setIdPlay(play.getIdGiornata());
                        bookPlay(userBean,playBean);
                    }
                    case 2 -> showPlayers(user, play);
                    case 3 -> {
                        Giornata play2 = BookSessionAppController.getLastPlay(playBean);
                        if (play2 == null) {
                            showPlayList(user, admin, play);
                            return;
                        } else {
                            showPlayList(user, admin, play2);
                            return;
                        }
                    }
                    case 4 -> {
                        Giornata play2 = BookSessionAppController.getNextPlay(playBean);
                        if (play2 == null) {
                            showPlayList(user, admin, play);
                            return;
                        } else {
                            showPlayList(user, admin, play2);
                            return;
                        }
                    }
                    default -> {
                        return;
                    }
                }
            }while(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void bookPlay(UserBean userBean, GiornataBean playBean) {
        if(playBean.getData().toInstant().isBefore(Instant.now())){
            System.out.println("Non è possibile prenotarsi per una giornata passata!");
        }else if (BookSessionAppController.isPlayerBooked(userBean, playBean)) {
            System.out.println("Sei già prenotato!");
        } else {
            BookSessionAppController.bookPlay(userBean, playBean);
            System.out.println("Prenotazione avvenuta con successo.");
        }
    }

    public static void showPlayers(String user, Giornata play){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        boolean res2;
        String input;
        int command = 0;
        GiornataBean bean = new GiornataBean();
        bean.setIdPlay(play.getIdGiornata());
        List<Player> list = BookSessionAppController.getPlayersList(bean);
        if(!list.isEmpty()){
            try {
                do {
                    int num = list.size();
                    System.out.println("Numero Giocatori: " + num);
                    for (int i = 1; i < num+1; i++) {
                        System.out.println(i + ")" + list.get(i - 1).getUsername());
                    }
                    System.out.println("Inserire il numero di un giocatore per visitare il suo profilo, oppure digitare 0 per tornare indietro");
                    input = br.readLine();
                    //controllo se l'input è corretto
                    res = ImportCheckInput.isNumber(input);
                    res2 = isPlayerValid(input,num);
                }while(!res || !res2);
                command = Integer.parseInt(input);
                if(command != 0) {
                    if (list.get(command - 1).getUsername().equals(user)) {
                        PlayerProfileCLI.main(user);
                    } else {
                        PlayerProfileViewCLI.main(list.get(command - 1).getUsername(), user);
                    }
                }
                }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static boolean isPlayerValid(String input, int numGiocatori) {
        boolean res = true;
        if (Integer.parseInt(input) > numGiocatori) {
            System.out.println("Numero inserito non valido!");
            res = false;
        }
        return res;
    }

    public static void showReviews(String user, AdminBean admin){
        try {
            List<Valutazione> list = ReviewAppController.reviewList(admin);
            int stelleTot = 0;
            int media = 0;
            int val = 0;
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getFkUsernameP1() + ": " + list.get(i).getDescrizione() + " val. :" + list.get(i).getStelle());
                stelleTot += list.get(i).getStelle();
                val++;
            }
            if (val != 0) {
                media = stelleTot / val;
            }
            System.out.println("Media stelle: " + media);
            UserPGPageCLI.main(user, admin.getNomeCampo());
        }catch (ReviewEmpty c){
            System.out.println("Lista vuota");
        }
    }

    public static void review(String user, AdminBean campo){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean res;
        try{
            do {
                System.out.println("Inserisci il numero di stelle (1-5): ");
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println(err);
                } else if (Integer.parseInt(input) > 5 || Integer.parseInt(input) < 1) {
                    System.out.println("Numero inserito non valido!");
                    res = false;
                } else {
                    int stelle = Integer.parseInt(input);
                    System.out.println("Inserire una descrizione: ");
                    input = br.readLine();
                    ValutazioneBean valutazione = new ValutazioneBean();
                    valutazione.setRecensione(input);
                    valutazione.setStelle(stelle);
                    valutazione.setUsernameP1(user);
                    valutazione.setRiceve(campo.getNomeCampo());
                    ReviewAppController.saveReview(valutazione);
                    System.out.println("Valutazione inserita.");
                    UserPGPageCLI.main(user, campo.getNomeCampo());
                }
            }while(!res);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
