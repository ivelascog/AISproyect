import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DialogAñadirContacto extends JDialog {
    private List<Phonenumber.PhoneNumber> numbers = new ArrayList<>();
    private Agenda agenda;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textNombre;
    private JList listTelefonos;
    private JTextField textTelefono;
    private JButton añadirButton;
    private JButton btnEliminar;

    private int indexTlfSelected = -1;

    public DialogAñadirContacto(Agenda agenda) {
        this.agenda = agenda;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        añadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Phonenumber.PhoneNumber phoneNumber = Contacto.stringToPhone(textTelefono.getText().trim());
                if (phoneNumber != null) {
                    numbers.add(phoneNumber);
                    loadTlfs();
                    textTelefono.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "El número introducido no es correcto", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        listTelefonos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                indexTlfSelected = listTelefonos.getSelectedIndex();
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (indexTlfSelected>=0){
                    numbers.remove(indexTlfSelected);
                    loadTlfs();
                } else {
                    JOptionPane.showMessageDialog(null,"Selecciona el teléfono a eliminar","Aviso",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void loadTlfs() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Phonenumber.PhoneNumber phoneNumber : numbers) {
            model.addElement(Contacto.phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
        }
        listTelefonos.setModel(model);
    }

    private void onOK() {
        String nombre = textNombre.getText().trim();
        if (nombre.equals("")) {
            if (numbers.size() != 0) {
                agenda.Anadir(" ", numbers);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No se permiten contactos sin nombre ni teléfono", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            if (!agenda.checkNombre(nombre)){
                agenda.Anadir(nombre,numbers);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No se permiten contactos con nombre repetido", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private void onCancel() {
        dispose();
    }

}
