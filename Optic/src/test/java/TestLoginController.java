import com.example.optic.app_controllers.LoginController;
import com.example.optic.bean.PlayerBean;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Simone Castelli
public class TestLoginController {
    //testo il login con un utente inesistente
    @Test
    public void testPlayerLoginNotValid(){
        LoginController test = new LoginController();
        PlayerBean player = new PlayerBean();
        //nome non presente nel database
        player.setBUsername("NotValid");
        player.setBPassword("1234");
        boolean output = test.playerLogin(player);
        assertFalse(output);
    }

    //testo il login con un utente esistente e password giusta
    @Test
    public void testPlayerLoginValid(){
        LoginController test = new LoginController();
        PlayerBean player = new PlayerBean();
        //nome presente nel database
        player.setBUsername("simone");
        //password giusta dell'utente simone
        player.setBPassword("simo");
        boolean output = test.playerLogin(player);
        assertTrue(output);
    }
}
