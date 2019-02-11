package menuAdmin;

import validator.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Form1 extends JFrame{
  private JPanel panel;
  private JPasswordField passwordField1;
  private JButton verifyButton;
  private byte[] pin;
  private char[] pinMatch = {5, 5, 5, 5};


    private JTextField textField2;
    private JButton creditButton;
    private JButton debitButton;
    private JButton connectButton;
    private JPanel panelConnect;
    private JPanel panelPIN;
    private JPanel panelManager;
    private JComboBox comboBox1;
    private JButton refreshButton;


    public Form1(){
        panelConnect.setVisible(true);
        panelPIN.setVisible(false);
        panelManager.setVisible(false);


        Validator v = new Validator();

        //TODO: Here some real connection to reader
        //if succeed - show pin verification

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPIN.setVisible(true);
                //TODO: here if reader is connected and card is in -> proceed to pin verification
            }
        });
          verifyButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  /*Validator v = new Validator();
                  String pinPrev = passwordField1.getPassword().toString();
                  pin = new String(passwordField1.getPassword()).getBytes();
                  System.out.println("raw bytes: " + pin + "\n");
                  System.out.println("String: " + pinPrev);*/

                  panelManager.setVisible(true);

                  /*if(v.verifyPIN(pin)){
                      tokenManager.setVisible(true);
                  }*/

              }
          });
    }

  public JPanel getPanel() {
    return panel;
  }
}