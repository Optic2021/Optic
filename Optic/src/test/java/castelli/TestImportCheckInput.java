package castelli;

import com.example.optic.utilities.ImportCheckInput;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestImportCheckInput {
    //testo se il metodo di controllo input è corretto
    //controllo che la stringa inserita contenga solo numeri
    @Test
    public void testIsNumberNotValid(){
        //la stringa contiene lettere, quindi non è corretta
        String check = "1234NotValid";
        //controllo che il metodo restituisca effettivamente false
        boolean res = ImportCheckInput.isNumber(check);
        assertFalse(res);
    }
}
