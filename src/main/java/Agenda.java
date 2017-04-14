import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* @author Oscar de la Cuesta Campillo. www.palentino.es
*/
public class Agenda {

    List<Contacto> lista_contactos = new ArrayList<Contacto>();
    private int contador_contactos = 0; // Contador de objetos creados. Variable muy importante.

    public void Consultar(String nombre, int telefono) {
        for (int i = 0; i < this.contador_contactos; i++) {

            if (nombre.equals(this.lista_contactos.get(i).getNombre())) {
                System.out.println("Ya existe un contacto con ese nombre");
            }
        }

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
    }

    public void Buscar(String nombre) {
        boolean encontrado = false;

        for (int i = 0; i < contador_contactos; i++) {
            if (nombre.equals(this.lista_contactos.get(i).getNombre())) {
                System.out.println(this.lista_contactos.get(i).getNombre() + "-" + "Tf:" + this.lista_contactos.get(i).getTelefono());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Contacto inexistente");
        }
    }

    public void Ordenar() {
        Collections.sort(lista_contactos);
    }

    public void Mostrar() {
        if (this.contador_contactos == 0) {
            System.out.println("No hay contactos");
        } else {
            for (int t = 0; t < this.contador_contactos; t++) {
                // Es necesario por precaución usar el this para el metodo, puesto que si se ejecuta muchas veces la referencias a memoria pueden fallar.
                System.out.println(this.lista_contactos.get(t).getNombre() + "-" + "Tf:" + Integer.toString(this.lista_contactos.get(t).getTelefono()));
            }
        }
    }

    public void Vaciar() {
        try {
            System.out.println("Se eliminarán todos los contactos");
            System.out.println("¿Estas seguro (S/N)?");
            String respuesta;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            respuesta = teclado.readLine();
            respuesta = respuesta.toUpperCase();
            if (respuesta.equals("S")) {

                //Lo hago por mera formalidad porque java se encarga de liberar los objetos no referenciados creados. El garbage collector
                for (int i = 0; i < this.contador_contactos; i++) {
                    this.lista_contactos.get(i).setNombre("");
                    this.lista_contactos.get(i).set_telefono(0);
                }
                contador_contactos = 0;
                System.out.println("Agenda vaciada correctamente");
            } else {
                System.out.println("Acción cancelada");
            }
        } catch (IOException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void EliminarNombre(String nombre) {


    }

    public void Modificar() {
        try {
            boolean encontrado = false;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Nombre de contacto a modificar:");
            String eliminar = teclado.readLine().toUpperCase();
            if (contador_contactos == 0) {
                System.out.println("No hay contactos");
            } else {
                for (int i = 0; i < this.contador_contactos; i++) {

                    if (eliminar.equals(this.lista_contactos.get(i).getNombre())) {
                        System.out.println(i + 1 + ". " + this.lista_contactos.get(i).getNombre() + "-" + "Tf:" + this.lista_contactos.get(i).getTelefono());
                        encontrado = true;
                    }
                }
                if (encontrado) {
                    System.out.println("¿Qué contacto quieres modificar?, introduce el número:");
                    int modificar_numero = Integer.parseInt(teclado.readLine());

                    System.out.println("Introduce nombre:");
                    String nombre_nuevo = teclado.readLine();
                    System.out.println("Introduce teléfono, formato numerico:");
                    int telefono_nuevo = Integer.parseInt(teclado.readLine());

                    this.lista_contactos.get(modificar_numero - 1).setNombre(nombre_nuevo);
                    this.lista_contactos.get(modificar_numero - 1).set_telefono(telefono_nuevo);
                    Ordenar();
                } else {
                    System.out.println("No hay contactos con ese nombre");
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Eliminar() {

    }
}

