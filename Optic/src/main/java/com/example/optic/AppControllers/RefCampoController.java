package com.example.optic.AppControllers;

import com.example.optic.bean.UserBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.EventDAO;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Event;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RefCampoController {

    public static Admin getAdminFromRef(UserBean user) throws IOException {
        Admin a = null;
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.getConn();
            a = dao.getAdminFromRef(user.getUsername());
        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    public static ArrayList<Event> getEventList() throws IOException{
        ArrayList<Event> list = new ArrayList<Event>();
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getRefereeEventList();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void closeConn() throws IOException {
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
