import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
* @author Oscar de la Cuesta Campillo. www.palentino.es
*/
public class Agenda {
    public static final String DEFAULT_PATH = "Contactos.txt";
    private List<Contacto> lista_contactos = new ArrayList<Contacto>();


    public List<Contacto> getLista_contactos() {
        return lista_contactos;
    }

    public void setLista_contactos(List<Contacto> lista_contactos) {
        this.lista_contactos = lista_contactos;
    }

    public Contacto getContacto(int i) {
        return lista_contactos.get(i);
    }

    public List<Contacto> fromCSV(String path) {
        File file = new File(path);
        FileReader fr = null;
        BufferedReader br = null;
        List<Contacto> contactos = new ArrayList<Contacto>();
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            br.readLine();
            String line = null;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                String nombre = st.nextToken();
                Contacto contacto;
                contacto = new Contacto(nombre);
                while (st.hasMoreTokens()) {
                    contacto.addTelefono(Contacto.stringToPhone(st.nextToken()));
                }
                contactos.add(contacto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return contactos;
    }

    public void toCSV(String path) {
        File file = new File(path);
        FileWriter fr = null;
        BufferedWriter bw = null;
        try {
            fr = new FileWriter(file);
            bw = new BufferedWriter(fr);
            bw.append("Nombre,Tlfnos\n");
            for (Contacto contacto : this.lista_contactos) {
                bw.append(contacto.getNombre()).append(",");
                if (contacto.getTelefonos().size()==0){
                    bw.append("\n");
                } else {
                    for (int i = 0; i < contacto.getTelefonos().size(); i++) {
                        if (i == (contacto.getTelefonos().size() - 1)) {
                            bw.append(Contacto.phoneUtil.format(contacto.getTelefonos().get(i), PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)).append("\n");
                        } else {
                            bw.append(Contacto.phoneUtil.format(contacto.getTelefonos().get(i), PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)).append(",");
                        }
                    }
                }
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Anadir(String nombre, Phonenumber.PhoneNumber telefono) {
        this.lista_contactos.add(new Contacto(nombre, telefono));
        Ordenar();
        toCSV(DEFAULT_PATH);
    }

    public void Anadir(String nombre, List<Phonenumber.PhoneNumber> telefono) {
        this.lista_contactos.add(new Contacto(nombre, telefono));
        Ordenar();
        toCSV(DEFAULT_PATH);
    }

    public void impotar(String path) {
        lista_contactos.addAll(fromCSV(path));
        toCSV(DEFAULT_PATH);
    }

    public void exportar(String path) {
        toCSV(path);
    }

    public boolean checkNombre(String nombre) {
        if (nombre==null){
            return false;
        } else if (nombre.equals("") || nombre.equals(" ")){
            return false;
        } else {
            for (Contacto contacto :lista_contactos){
                if (contacto.getNombre().equals(nombre)){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Contacto> BuscarNombre(String nombre) {
        List<Contacto> filteredContactos = new ArrayList<Contacto>();
        for (Contacto contacto : lista_contactos) {
            if (contacto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                filteredContactos.add(contacto);
            }
        }
        return filteredContactos;
    }

    public List<Contacto> BuscarTlf(Phonenumber.PhoneNumber tlf) {
        List<Contacto> filteredContactos = new ArrayList<Contacto>();
        for (Contacto contacto : lista_contactos) {
            boolean añadido = false;
            for (int i = 0; i < contacto.getTelefonos().size() && !añadido; i++) {
                PhoneNumberUtil.MatchType match = Contacto.phoneUtil.isNumberMatch(contacto.getTelefonos().get(i), tlf);
                if (match == PhoneNumberUtil.MatchType.NSN_MATCH || match == PhoneNumberUtil.MatchType.SHORT_NSN_MATCH) {
                    filteredContactos.add(contacto);
                    añadido = true;
                }
            }
        }
        return filteredContactos;
    }


    public void Ordenar() {
        Collections.sort(lista_contactos);
    }

    public void Modificar(Contacto contacto, String nombre, List<Phonenumber.PhoneNumber> telefonos) {
        int index = lista_contactos.indexOf(contacto);
        Contacto contactoAux = lista_contactos.get(index);
        contacto.setNombre(nombre);
        contacto.setTelefonos(telefonos);
        toCSV(DEFAULT_PATH);
    }

    public void Eliminar(Contacto contacto) {
        lista_contactos.remove(contacto);
        toCSV(DEFAULT_PATH);
    }

    public void cargar() {
        File file = new File("Contactos.txt");
        if (file.exists()) {
            this.setLista_contactos(fromCSV(DEFAULT_PATH));
        }
        Ordenar();
    }

}

