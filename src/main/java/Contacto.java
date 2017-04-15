import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan_ on 26/01/2017.
 */
public class Contacto implements Comparable<Contacto> {
    private String nombre;
    private List<Integer> telefonos = new ArrayList<Integer>();

    public Contacto(String nombre, List<Integer> telefonos) {
        this.nombre = nombre;
        this.telefonos = telefonos;

    }

    public Contacto(String nombre) {
        this.nombre = nombre;
    }

    public Contacto() {
        this.nombre = null;
        this.telefonos = new ArrayList<Integer>();
    }

    public Contacto(String nombre, int telefono) {
        this.nombre = nombre;
        this.telefonos.add(telefono);
    }

    public Contacto(int tlf) {
        this.telefonos.add(tlf);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Integer> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Integer> telefonos) {
        this.telefonos = telefonos;
    }

    public void addTelefono(int telf) {
        telefonos.add(telf);
    }

    public int compareTo(Contacto contacto) {
        if (this.getNombre() == null) {
            return contacto.getNombre() == null ? 0 : 1;
        }
        return this.nombre.compareTo(contacto.getNombre());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Contacto contacto = (Contacto) o;
        if (this.getNombre() == null) {
            return contacto.getNombre() == null;
        }
        return nombre.equals(contacto.nombre);
    }

}

