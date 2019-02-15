package menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cartUser extends JFrame{
    private JPanel panelSummary;
    private JLabel itemQuantity;
    private JLabel itemTotalPrice;
    private JLabel balanceAfterOrder;
    private JButton checkoutButton;

    public cartUser() {

        setTitle("Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        setSize(300,200);
        setLocation(825,425);




        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
