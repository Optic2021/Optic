package carloni;

import com.example.optic.utilities.ImportUrl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//LUCA CARLONI
//Il metodo richiamato controlla che il social selezionato tramite variabile social sia valido
public class TestImportUrl{
    @Test
    public void TestControlliUrl(){
        boolean res=ImportUrl.controlliUrl("","https://www.facebook.com/","",1);
        assertEquals(true,res);
    }

}
