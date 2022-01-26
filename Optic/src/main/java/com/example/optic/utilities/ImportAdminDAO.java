package com.example.optic.utilities;

import com.example.optic.entities.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportAdminDAO {

    private ImportAdminDAO(){/*Doesnp*/}

    public static Admin setAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        if (!rs.first()){ // rs empty
            admin = null;
        }else {
            rs.first();
            admin.setUsername(rs.getString("Username"));
            admin.setPassword(rs.getString("Password"));
            admin.setIg(rs.getString("Instagram"));
            admin.setFb(rs.getString("Facebook"));
            admin.setWa(rs.getString("Whatsapp"));
            admin.setDescrizioneC(rs.getString("DescrizioneC"));
            admin.setNomeC(rs.getString("NomeC"));
            admin.setVia(rs.getString("Via"));
            admin.setProvincia(rs.getString("Provincia"));

            //chiudo result set
            rs.close();
        }
        return admin;
    }

}
