package carloni;

import com.example.optic.utilities.ImportCheckInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//LUCA CARLONI
//Il metodo controlla che l'input inserito sia effettivamente un numero
public class TestImportInput {
    @Test
    public void testCheckInput() {
        boolean res = ImportCheckInput.checkInput("2");
        assertEquals(true, res);
    }
}




