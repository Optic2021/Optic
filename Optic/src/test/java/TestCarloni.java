import com.example.optic.app_controllers.LoginController;
import com.example.optic.app_controllers.RegisterController;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.PrenotazioneDAO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
public class TestCarloni {
    //Luca Carloni
    //testo il login con un utente inesistente
    @Test
    public void testPlayerBooked(){
        boolean res=PrenotazioneDAO.isPlayerBooked("carlo",5);
        assertEquals(true,res);
    }

    @Test
    public void testPlayerBookedFail(){
        boolean res=PrenotazioneDAO.isPlayerBooked("giuseppe",5);
        assertEquals(true,res);
    }

    //testo il login con un utente esistente e password giusta
    @Test
    public void TestUsernameUsedFail(){
        UserBean user=new UserBean();
        user.setUsername("Used");
        user.setPassword("Used");
        boolean res=false;
        try {
            res = RegisterController.isUsernameUsed(user,1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(true,res,"Expected to be used");
    }

    @Test
    public void TestUsernameUsed(){
        UserBean user=new UserBean();
        user.setUsername("NotUsed");
        user.setPassword("NotUsed");
        boolean res= false;
        try {
            res = RegisterController.isUsernameUsed(user,2);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(false,res,"Expected not to be used");
    }
}


