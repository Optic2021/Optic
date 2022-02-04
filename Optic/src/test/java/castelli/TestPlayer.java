package castelli;

import com.example.optic.entities.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//Simone Castelli
public class TestPlayer {
    //controllo il metodo per trasformare il valore intero della valutazione nella stringa con il numero di stelle corrispondenti
    @Test
    public void playerSetStarFromVal(){
        Player player = new Player("user",5);
        String stelle = player.getStelle();
        //una valutazione = 5 deve corrispondere ad una stringa con 5 stelle
        assertEquals("* * * * *",stelle);
    }
}
