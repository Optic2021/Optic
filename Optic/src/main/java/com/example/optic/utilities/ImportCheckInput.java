package com.example.optic.utilities;

public class ImportCheckInput {
    private ImportCheckInput(){
        //does nothing
    }
    //controllo se il tipo inserito è valido
    public static boolean checkInput(String str){
        String[] number = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        boolean res = false;
        if(str.length() == 1){
            for(int i = 0; i < 10; i++){
                if(str.equals(number[i])){
                    res = true;
                }
            }
        }
        return res;
    }

    public static boolean isNumber(String str){
        boolean res = true;
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i))){
                res = false;
            }
        }
        return res;
    }
}
