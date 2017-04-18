import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by ivan_ on 15/04/2017.
 */
public class GUI {
    private JList listContactos;
    private JTextField txtBusqueda;
    private JList listNumeros;
    private JButton btnAddContacto;
    private JButton btnDelContacto;
    private JButton btnDelTlf;
    private JPanel panel;
    private JPanel panel2;
    private JButton btnModificar;
    private JButton btnExportar;
    private JButton btnImportar;

    private Agenda agenda = new Agenda();
    private Contacto contactoSeleccionado = null;
    private int indexTelefono = -1;

    public GUI() {
        agenda.cargar();
        loadContactos();
        btnDelContacto.setEnabled(false);
        btnDelTlf.setEnabled(false);
        btnModificar.setEnabled(false);

        //Listener Lista Contactos
        listContactos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                contactoSeleccionado = (Contacto) listContactos.getSelectedValue();
                indexTelefono = -1;
                loadTelefonos();
                btnDelContacto.setEnabled(true);
                btnModificar.setEnabled(true);
            }
        });

        //Listener Lista de Telefonos
        listNumeros.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                indexTelefono = listNumeros.getSelectedIndex();
                btnDelTlf.setEnabled(true);
            }
        });

        //Listener eliiminar Telefono
        btnDelTlf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (indexTelefono >= 0) {
                    int result = JOptionPane.showConfirmDialog(new JFrame(), "¿ Está seguro de eliminar el Teléfono ?", "Eliminación", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        contactoSeleccionado.getTelefonos().remove(indexTelefono);
                        loadTelefonos();
                        btnDelTlf.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Seleccione el Teléfono a eliminar", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Listener Eliminar Contacto
        btnDelContacto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (contactoSeleccionado != null) {
                    int result = JOptionPane.showConfirmDialog(new JFrame(), "¿Está seguro de eliminar el contacto?", "Eliminación", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        agenda.Eliminar(contactoSeleccionado);
                        contactoSeleccionado = null;
                        loadContactos();
                        btnDelTlf.setEnabled(false);
                        btnDelContacto.setEnabled(false);
                        btnModificar.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Seleccione el Contacto a eliminar", "Eliminación", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Listener de cuadro de busqueda
        txtBusqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String texto = txtBusqueda.getText();
                Phonenumber.PhoneNumber numnber;
                if (texto != null && !texto.equals("")) {
                    if ((numnber = Contacto.stringToPhone(texto)) != null) {
                        loadContactos(agenda.BuscarTlf(numnber));
                    } else {
                        loadContactos(agenda.BuscarNombre(texto));
                    }
                }
            }
        });

        //Boton Añadir Contacto
        btnAddContacto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialogAddContacto();
                btnDelTlf.setEnabled(false);
                btnDelContacto.setEnabled(false);
                btnModificar.setEnabled(false);
            }
        });
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialogModificar();
                btnDelTlf.setEnabled(false);
                btnDelContacto.setEnabled(false);
                btnModificar.setEnabled(false);
            }
        });
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser();
                int result = jFileChooser.showOpenDialog(new JFrame());
                if (result ==JFileChooser.APPROVE_OPTION){
                    try {
                        agenda.exportar(jFileChooser.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(null,"Se ha exportado con éxito","Exporaticon",JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(null,"No se pudo llevar a cabo la exportación por:"+ e.getLocalizedMessage(),"Error de Exportación",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnImportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser();
                int result = jFileChooser.showOpenDialog(new JFrame());
                if (result ==JFileChooser.APPROVE_OPTION){
                    try {
                        agenda.impotar(jFileChooser.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(null,"Se ha importado con éxito","Importacion",JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(null,"No se pudo llevar a cabo la Importación devido a: "+ e.getLocalizedMessage(),"Error de Importación",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void dialogModificar() {
        DialogModificar dialog = new DialogModificar(agenda,contactoSeleccionado);
        dialog.pack();
        dialog.setVisible(true);
        loadContactos();
        loadTelefonos();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("Agenda Telefónica");
        frame.setVisible(true);
    }

    private void loadContactos(List<Contacto> contactos) {
        DefaultListModel<Contacto> model = new DefaultListModel<>();
        for (Contacto contacto : contactos) {
            model.addElement(contacto);
        }
        listContactos.setModel(model);
    }

    private void loadTelefonos() {
        DefaultListModel<String> model = new DefaultListModel<>();
        if (contactoSeleccionado != null) {
            for (Phonenumber.PhoneNumber numero : contactoSeleccionado.getTelefonos()) {
                model.addElement(Contacto.phoneUtil.format(numero, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
            }
        }
        listNumeros.setModel(model);
    }

    private void loadContactos() {
        loadContactos(agenda.getLista_contactos());

    }

    private void dialogAddContacto() {
        DialogAñadirContacto dialog = new DialogAñadirContacto(agenda);
        dialog.pack();
        dialog.setVisible(true);
        loadContactos();
        loadTelefonos();
    }
}
