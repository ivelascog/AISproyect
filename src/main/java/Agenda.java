
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

    public void toCSV(String path){

    }

    public void Anadir(String nombre, int telefono) {
            Contacto contacto = new Contacto();
            contacto.set_nombre(nombre);
            contacto.set_telefono(telefono);
            this.lista_contactos.add(contacto);
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
                    this.lista_contactos.get(i).set_nombre("");
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

                    this.lista_contactos.get(modificar_numero - 1).set_nombre(nombre_nuevo);
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

