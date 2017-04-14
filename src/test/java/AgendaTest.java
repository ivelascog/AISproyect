

import org.junit.Assert;
import org.junit.Test;

import java.util.List;


/**
 * Created by ivan_ on 14/04/2017.
 */
public class AgendaTest {
    @Test
    public void testPersistencia() {
        Agenda agenda = new Agenda();
        agenda.Anadir("1", 1);
        agenda.Anadir("2", 2);
        agenda.Anadir("3", 3);
        List<Contacto> contactos = agenda.fromCSV(Agenda.DEFAULT_PATH);
        Assert.assertTrue(agenda.getLista_contactos().equals(contactos));
    }
}
