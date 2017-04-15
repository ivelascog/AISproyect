import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan_ on 26/01/2017.
 */
public class Contacto implements Comparable<Contacto> {
    private String nombre;
    private List<Phonenumber.PhoneNumber> telefonos = new ArrayList<Phonenumber.PhoneNumber>();

    public Contacto(String nombre, List<Phonenumber.PhoneNumber> telefonos) {
        this.nombre = nombre;
        this.telefonos = telefonos;

    }

    public Contacto(String nombre) {
        this.nombre = nombre;
    }

    public Contacto() {
        this.nombre = null;
        this.telefonos = new ArrayList<Phonenumber.PhoneNumber>();
    }

    public Contacto(String nombre, Phonenumber.PhoneNumber telefono) {
        this.nombre = nombre;
        this.telefonos.add(telefono);
    }

    public Contacto(Phonenumber.PhoneNumber tlf) {
        this.telefonos.add(tlf);
    }

    public static boolean checkNumber(String tlf) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(tlf, "ES");
            return phoneUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Phonenumber.PhoneNumber> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Phonenumber.PhoneNumber> telefonos) {
        this.telefonos = telefonos;
    }

    public void addTelefono(Phonenumber.PhoneNumber telf) {
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

