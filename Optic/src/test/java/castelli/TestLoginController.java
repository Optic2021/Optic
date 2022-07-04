package castelli;

import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.PlayerBean;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Simone Castelli
public class TestLoginController {
    private static LoginController loginController = new LoginController();
    //testo il login con un utente inesistente
    @Test
    public void testPlayerLoginNotValid(){
        PlayerBean player = new PlayerBean();
        //nome non presente nel database
        player.setUsername("NotValid");
        player.setPassword("1234");
        boolean output = loginController.playerLogin(player);
        assertFalse(output);
    }

    //testo il login con un utente esistente e password giusta
    @Test
    public void testPlayerLoginValid(){
        PlayerBean player = new PlayerBean();
        //nome presente nel database
        player.setUsername("simone");
        //password giusta dell'utente simone
        player.setPassword("simo");
        boolean output = loginController.playerLogin(player);
        assertTrue(output);
    }
}
