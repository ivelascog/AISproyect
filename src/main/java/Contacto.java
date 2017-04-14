/**
 * Created by ivan_ on 26/01/2017.
 */
public class Contacto implements Comparable<Contacto>{
    private String nombre;
    private int telefono;

    public Contacto()
    {
        this.nombre=null;
        this.telefono=0;
    }
    public Contacto(String nombre, int telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }
    public void set_nombre(String nomb){
        this.nombre=nomb.toUpperCase();
    }
    public void set_telefono(int telf){
        this.telefono=telf;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public int compareTo(Contacto o) {
        return 0;
    }
}

