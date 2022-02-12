package carloni;

import com.example.optic.app_controllers.RegisterController;
import com.example.optic.bean.UserBean;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
//LUCA CARLONI
public class TestRegister {
    //testo il login con un utente esistente e password giusta
    @Test
    public void testUsernameUsed(){
        UserBean user=new UserBean();
        user.setUsername("Not Used");
        boolean res= false;
        try {
            res = RegisterController.isUsernameUsed(user,1);
        } catch (ClassNotFoundException |SQLException e ) {
            e.printStackTrace();
        }
        assertEquals(false,res,"The Username is already used");
    }
}

