package com.example.optic.SecondUI;

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
import java.util.List;

public class UserPGPageCLI {

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
                    System.out.println("\nComando non valido!");
                }else{
                    command = Integer.parseInt(input);
                }
            }while (!res);
            switch (command) {
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
                case 5 -> PlayerHomeCLI.main(user);
                default -> showInfo(user,a);
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
                    System.out.println("Comando non valido!");
                }
            }while (!res);
            UserPGPageCLI.main(user,admin.getNomeCampo());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void showPlayList(String user,String admin,Giornata play){
        System.out.println("Data: "+play.getDataString()+"\nAttività: "+play.getFkNome());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
        int command = 0;
        GiornataBean playBean = new GiornataBean();
        playBean.setAdmin(admin);
        playBean.setData(play.getData());
        try {
            do {
                System.out.println("1)Prenota | 2)Vedi Giocatori | 3)Giornata precedente | 4)Prossima Giornata | 5)Indietro");
                String input;
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println("Comando non valido!");
                }else{
                    command = Integer.parseInt(input);
                }
            } while (!res);
            switch (command){
                case 1 -> {
                    UserBean userBean = new UserBean();
                    userBean.setUsername(user);
                    playBean.setIdPlay(play.getIdGiornata());
                    if(BookSessionAppController.isPlayerBooked(userBean,playBean)){
                        System.out.println("Sei già prenotato!");
                    }else{
                        BookSessionAppController.bookPlay(userBean,playBean);
                        System.out.println("Prenotazione avvenuta con successo.");
                    }
                    showPlayList(user,admin,play);
                }
                case 2 -> showPlayers(user,admin,play);
                case 3 -> {
                    Giornata play2 = BookSessionAppController.getLastPlay(playBean);
                    if(play2 == null){
                        showPlayList(user,admin,play);
                    }else{
                        showPlayList(user,admin,play2);
                    }
                }
                case 4 -> {
                    Giornata play2 = BookSessionAppController.getNextPlay(playBean);
                    if(play2 == null){
                        showPlayList(user,admin,play);
                    }else{
                        showPlayList(user,admin,play2);
                    }
                }
                case 5 -> PlayerHomeCLI.main(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void showPlayers(String user, String admin, Giornata play){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res;
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
                    res = ImportCheckInput.isNumber(input);
                    //controllo se l'input è corretto
                    if (!res) {
                        System.out.println("Comando non valido!");
                    } else if (Integer.parseInt(input) > num) {
                        System.out.println("Numero inserito non valido!");
                        res = false;
                    } else {
                        command = Integer.parseInt(input);
                        if(command == 0){
                            showPlayList(user,admin,play);
                        }else{
                            PlayerProfileCLI.main(list.get(command-1).getUsername());
                        }
                    }
                }while(!res);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void showReviews(String user, AdminBean admin){
        List<Valutazione> list = ReviewAppController.reviewList(admin);
        int stelleTot = 0;
        int media = 0;
        int val = 0;
        if(!list.isEmpty()){
            for(int i = 0; i < list.size(); i++){
                System.out.println(list.get(i).getFkUsernameP1()+": "+"\n"+list.get(i).getDescrizione());
                stelleTot += list.get(i).getStelle();
                val++;
            }
            media = stelleTot/val;
            System.out.println("Media stelle: "+media);
        }else{
            System.out.println("Il campo non ha recensioni.");
        }
        UserPGPageCLI.main(user, admin.getNomeCampo());
    }

    public static void review(String user, AdminBean campo){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean res;
        int command = 0;
        try{
            do {
                System.out.println("Inserisci il numero di stelle (1-5): ");
                input = br.readLine();
                res = ImportCheckInput.checkInput(input);
                //controllo se l'input è corretto
                if (!res) {
                    System.out.println("Comando non valido!");
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
