package carloni;

import com.example.optic.app_controllers.BookSessionAppController;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.utilities.ImportCheckInput;
import com.example.optic.utilities.ImportStar;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.assertEquals;
//LUCA CARLONI
public class TestImportCheck {
    @Test
    public void testCheckInput() {
        boolean res = ImportCheckInput.checkInput("2");
        assertEquals(true, res,"User not booked");
    }
}




