import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.glass.ui.Cursor.setVisible;

public class MenuUser extends JFrame{

    public JPanel contentPanel;
    public JButton goBackButton;

    MenuUser(){
        add(contentPanel);

        setTitle("User Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        setSize(300,200);
        setLocation(825,425);

        remove(contentPanel);

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
