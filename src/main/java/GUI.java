import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ivan_ on 15/04/2017.
 */
public class GUI {
    private JList listContactos;
    private JTextField textField1;
    private JList listNumeros;
    private JButton btnAddContacto;
    private JButton btnDelContacto;
    private JButton btnDelTlf;
    private JButton btnAddTlf;
    private JPanel panel;
    private JPanel panel2;

    private Agenda agenda = new Agenda();
    Contacto contactoSeleccionado = null;
    int indexTelefono = -1;
    public GUI() {
        agenda.cargar();
        loadContactos();
        listContactos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                contactoSeleccionado = (Contacto) listContactos.getSelectedValue();
                indexTelefono=-1;
                loadTelefonos();
            }
        });
        listNumeros.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                indexTelefono = listNumeros.getSelectedIndex();
            }
        });
        btnDelTlf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (indexTelefono>=0){
                   int result = JOptionPane.showConfirmDialog(new JFrame(),"esta seguro de eliminar el contacto","Eliminacion",JOptionPane.YES_NO_OPTION);
                   if (result == JOptionPane.YES_OPTION){
                       contactoSeleccionado.getTelefonos().remove(indexTelefono);
                       loadTelefonos();
                   }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"Seleccione el Telefono a eliminar","Eliminacion",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void loadTelefonos() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Phonenumber.PhoneNumber numero:contactoSeleccionado.getTelefonos()){
            model.addElement(Contacto.phoneUtil.format(numero, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
        }
        listNumeros.setModel(model);
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
        DefaultListModel<Contacto> model = new DefaultListModel<>();
        for (Contacto contacto:agenda.getLista_contactos()){
            model.addElement(contacto);
        }
        listContactos.setModel(model);

    }
}
