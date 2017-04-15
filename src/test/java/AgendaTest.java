

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
        Assert.assertEquals(contactos.size(), agenda.getLista_contactos().size());
        compareContactList(agenda, contactos);
    }

    private void compareContactList(Agenda agenda, List<Contacto> contactos) {
        for (int i = 0; i < contactos.size(); i++) {
            Assert.assertEquals(contactos.get(i).getNombre(), agenda.getContacto(i).getNombre());
            Assert.assertEquals(contactos.get(i).getTelefonos().size(), agenda.getContacto(i).getTelefonos().size());
            for (int j = 0; j < contactos.get(i).getTelefonos().size(); j++) {
                Assert.assertEquals(contactos.get(i).getTelefonos().get(j), agenda.getContacto(i).getTelefonos().get(j));
            }
        }
    }

    @Test
    public void testPersistenciaSinNombre() {
        Agenda agenda = new Agenda();
        agenda.Anadir(null, 1);
        List<Integer> tlfs = new ArrayList<Integer>();
        tlfs.add(2);
        tlfs.add(3);
        agenda.Anadir(null, tlfs);
        List<Contacto> contactos = agenda.fromCSV(Agenda.DEFAULT_PATH);
        Assert.assertEquals(contactos.size(), agenda.getLista_contactos().size());
        compareContactList(agenda, contactos);
    }

    @Test
    public void testPersistenciaSinNumero() {
        //TODO Cuando este listo la representacion de los numeros de los contactos
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
