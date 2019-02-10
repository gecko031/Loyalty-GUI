package MenuAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdmin extends JDialog{

    public JPanel panelRoot;
    private JPanel panel1;
    private JPanel panel2;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JPanel panelOne;
    private JPanel panelTwo;


    public MenuAdmin() {
        //panelRoot.add(panelOne, "1");
        //panelRoot.add(panelTwo, "2");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onOK();}
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onCancel();}
        });



    }



    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MenuAdmin dialog = new MenuAdmin();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
    /*public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuAdmin();
            }
        });

    }*/
    private void createUIComponents() {
        panelOne = new PanelOne().getPanelOne();
        panelTwo = new PanelTwo().getPanelTwo();

    }
}
