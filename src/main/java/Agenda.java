import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
* @author Oscar de la Cuesta Campillo. www.palentino.es
*/
public class Agenda {
    private static final String DEFAULT_PATH = "Contactos.txt";
    List<Contacto> lista_contactos = new ArrayList<Contacto>();

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
                Contacto contacto = new Contacto(st.nextToken());
                while (st.hasMoreTokens()) {
                    contacto.addTelefono(Integer.getInteger(st.nextToken()));
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
                bw.append(contacto.getNombre());
                for (Integer telefono:contacto.getTelefonos()){
                    bw.append(",").append(String.valueOf(telefono));
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
            this.lista_contactos.add(new Contacto(nombre,telefono));
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

