package menu;

import javax.swing.*;

import static java.lang.Integer.parseInt;

public class MenuUser extends JDialog {

    private JPanel panelStore;

    public MenuUser(){

        setContentPane(panelStore);
        setModal(true);
        pack();
        setSize(500,500);
        setLocation(825,425);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );

    }
    private void createUIComponents() {
        panelStore = new Store().getPanel();
    }
}
