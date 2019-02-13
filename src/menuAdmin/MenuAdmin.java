package menuAdmin;

import javax.swing.*;
import java.awt.event.*;

public class MenuAdmin extends JDialog {
  private JPanel  contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JPanel panel1;
  private JPanel panel2;


  public MenuAdmin() {

    setContentPane(contentPane);
    setModal(true);
    pack();
    setSize(400,400);
    setLocation(825,425);
    getRootPane().setDefaultButton(buttonOK);

    //initial visibility
    panel1.setVisible(true);

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {onOK();}
    });

    buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {onCancel();}
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
  }

  private void onOK() {
// add your code here
    dispose();
  }

  private void onCancel() {
// add your code here if necessary
    dispose();
  }

  private void createUIComponents() {
    panel1 = new Form1().getPanel();
  }
}
