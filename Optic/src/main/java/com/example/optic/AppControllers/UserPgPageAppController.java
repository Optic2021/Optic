package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Campo;
import com.example.optic.dao.AdminDAO;
import java.io.IOException;
public class UserPgPageAppController {

    //Da finire con adminDao
    /*public Admin getCampo(String nomeC) throws Exception {
        Statement stmt = null;
        Admin admin = new Admin("","");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM admin join referee on referee.fk_UsernameA1=admin.Username WHERE NomeC=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,admin.getNomeC());
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
                campo = null;
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
                admin.setReferee(rs.getString("referee.Username"));

                //chiudo result set
                rs.close();
            }
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return admin;
    }*/

    public static Admin getCampoInfo(AdminBean campo) throws Exception{
        AdminDAO dao= AdminDAO.getInstance();
        String nomeC=campo.getNomeCampo();
        dao.getAdmin(nomeC);
        //Admin x = dao.getCampo(campo);
        Admin x = null;
        return x;
    }
}
