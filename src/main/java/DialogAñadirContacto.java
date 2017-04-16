import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DialogAñadirContacto extends JDialog {
    List<Phonenumber.PhoneNumber> numbers = new ArrayList<>();
    Agenda agenda;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textNombre;
    private JList listTelefonos;
    private JTextField textTelefono;
    private JButton añadirButton;

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
                Phonenumber.PhoneNumber phoneNumber = Contacto.stringToPhone(textTelefono.getText());
                if (phoneNumber != null) {
                    numbers.add(phoneNumber);
                    loadTlfs();
                    textTelefono.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "El numero introducido no es correcto", "Error", JOptionPane.WARNING_MESSAGE);
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
        agenda.Anadir(textNombre.getText(), numbers);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
