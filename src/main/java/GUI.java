import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Created by ivan_ on 15/04/2017.
 */
public class GUI {
    private JList listContactos;
    private JTextField textField1;
    private JList listNumeros;
    private JButton añadirButton;
    private JButton eliminarButton;
    private JButton eliminarButton1;
    private JButton añadirButton1;
    private JPanel panel;
    private JPanel panel2;

    private Agenda agenda = new Agenda();

    public GUI() {
        agenda.Anadir("Prueba",Contacto.stringToPhone("645383793"));
        loadContactos();
        listContactos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("Gestor de Elecciones, Tenrero,Velasco,Loachamín");
        frame.setVisible(true);
    }

    private void loadContactos(){
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Contacto contacto:agenda.getLista_contactos()){
            model.addElement(contacto.getNombre());
        }
        listContactos.setModel(model);

    }
}
