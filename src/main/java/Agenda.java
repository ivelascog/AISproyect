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
                if (nombre.equals("null")) {
                    contacto = new Contacto();
                } else {
                    contacto = new Contacto(nombre);
                }
                while (st.hasMoreTokens()) {
                    contacto.addTelefono(Integer.parseInt(st.nextToken()));
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

    public void toCSV(String path){
        File file = new File(path);
        FileWriter fr = null;
        BufferedWriter bw = null;
        try {
            fr = new FileWriter(file);
            bw = new BufferedWriter(fr);
            bw.append("Nombre,Tlfnos\n");
            for (Contacto contacto:this.lista_contactos){
                if (contacto.getNombre() != null) {
                    bw.append(contacto.getNombre());
                }
                bw.append(",");
                for (int i = 0; i < contacto.getTelefonos().size(); i++) {
                    if (i == (contacto.getTelefonos().size() - 1)) {
                        bw.append(String.valueOf(contacto.getTelefonos().get(i))).append("\n");
                    } else {
                        bw.append(String.valueOf(contacto.getTelefonos().get(i))).append(",");
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
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void Anadir(String nombre, int telefono) {
        this.lista_contactos.add(new Contacto(nombre, telefono));
        Ordenar();
        toCSV(DEFAULT_PATH);
    }

    public void Anadir(String nombre, List<Integer> telefono) {
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
        Contacto contactoAux = new Contacto(nombre);
        return lista_contactos.contains(contactoAux);
    }

    public List<Contacto> Buscar(String nombre) {
        List<Contacto> filteredContactos = new ArrayList<Contacto>();
        for (Contacto contacto : lista_contactos) {
            if (contacto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                filteredContactos.add(contacto);
            }
        }
        return filteredContactos;
    }

    public List<Contacto> Buscar(int tlf) {
        List<Contacto> filteredContactos = new ArrayList<Contacto>();
        for (Contacto contacto : lista_contactos) {
            boolean añadido = false;
            for (int i = 0; i < contacto.getTelefonos().size() && !añadido; i++) {
                if (Integer.toString(contacto.getTelefonos().get(i)).contains(Integer.toString(tlf))) {
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

    public void Modificar(Contacto contacto, String nombre, List<Integer> telefonos) {
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
}

