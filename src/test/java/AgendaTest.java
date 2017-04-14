

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
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

    @Test
    public void testModificar() {
        Agenda agenda = new Agenda();
        agenda.Anadir("1", 1);
        agenda.Anadir("2", 2);
        agenda.Anadir("3", 3);
        Contacto contacto = agenda.getLista_contactos().get(1);
        agenda.Modificar(contacto, "dos", new ArrayList<Integer>());
        Assert.assertEquals(contacto.getNombre(), "dos");
        Assert.assertEquals(contacto.getTelefonos().size(), 0);
    }

    @Test
    public void testEliminar() {
        Agenda agenda = new Agenda();
        agenda.Anadir("A", 1);
        agenda.Anadir("B", 2);
        agenda.Anadir("C", 3);
        List<Contacto> listAux = new ArrayList<Contacto>();
        listAux.addAll(agenda.getLista_contactos());
        listAux.remove(2);
        Contacto contacto = agenda.getContacto(2);
        agenda.Eliminar(contacto);
        Assert.assertEquals(listAux.size(), agenda.getLista_contactos().size());
        for (int i = 0; i < listAux.size(); i++) {
            Assert.assertEquals(listAux.get(i), agenda.getContacto(i));
        }
    }

    @Test
    public void testAñadir() {
        Agenda agenda = new Agenda();
        agenda.Anadir("A", 1);
        agenda.Anadir("C", 2);
        agenda.Anadir("D", 3);
        agenda.Anadir("B", 4);
        Assert.assertEquals(agenda.getContacto(1).getNombre(), "B");
    }
}
