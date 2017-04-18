import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;

public class DialogModificar extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textNombre;
    private JTextField textTlf;
    private JButton btnAñadir;
    private JButton btnEliminar;
    private JList listTelefonos;


    List<Phonenumber.PhoneNumber> numbers;
    Contacto contacto;
    Agenda agenda;
    private int indexTlfSelected=-1;

    public DialogModificar(Agenda agenda,Contacto contacto) {
        this.agenda = agenda;
        this.contacto =  contacto;
        this.numbers = contacto.getTelefonos();
        textNombre.setText(contacto.getNombre());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        loadTlfs();

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


        btnAñadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Phonenumber.PhoneNumber phoneNumber = Contacto.stringToPhone(textTlf.getText().trim());
                if (phoneNumber != null) {
                    numbers.add(phoneNumber);
                    loadTlfs();
                    textTlf.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "El numero introducido no es correcto", "Error", JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null,"Selecciona el telefono a eliminar","Aviso",JOptionPane.INFORMATION_MESSAGE);
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
                agenda.Modificar(contacto," ",numbers);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No se permiten contacios sin nombre ni telefono", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            if (!agenda.checkNombre(nombre)){
                agenda.Modificar(contacto,nombre,numbers);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No se permiten contacios con nombre repetido", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private void onCancel() {
        dispose();
    }

}
