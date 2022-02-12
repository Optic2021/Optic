package com.example.optic.bean;

public class Factory {
    public static UserBean createUser(int prof){
        UserBean user;
        switch(prof){
            case 1: user= new PlayerBean();
            break;
            case 2: user= new AdminBean();
                break;
            default: user= new RefereeBean();
                break;
        }
        return user;
    }
}
