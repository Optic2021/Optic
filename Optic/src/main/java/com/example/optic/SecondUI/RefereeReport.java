package com.example.optic.SecondUI;

import com.example.optic.app_controllers.RefReportPlayer;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.bean.UserBean;

import java.util.Calendar;
import java.util.Date;

public class RefereeReport {
    private static RefereeBean ref= new RefereeBean();
    public static void main(String user) {
        ref.setUsername(user);
        UserBean u1 = new UserBean();
        u1.setUsername(user);
        //set proprietario campo
        ref.setFkUsernameA1(RefReportPlayer.getAdminFromRef(u1).getUsername());
        System.out.println("Arbitro "+user);
        //print campo in cui lavora
        System.out.println("Assegnato al campo "+RefReportPlayer.getAdminFromRef(u1).getNomeC());
        u1.setUsername(ref.getFkUsernameA1());
        RefReportPlayer.getFirstPlay(u1);

    }
}
