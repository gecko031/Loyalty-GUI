package menuAdmin;

import validator.Validator;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Form1 extends JFrame{

    private JPanel panel;

    private JPanel panelConnect;
    private JButton connectButton;
    private JComboBox comboBox1;
    private JButton loadReaderButton;
    private JLabel readerStatus;

    private JPanel panelPIN;
    private JPasswordField fieldPIN;
    private JButton verifyButton;
    private JLabel labelStatus;

    private JPanel panelManager;
    private JTextField fieldPoint;
    private JButton creditButton;
    private JButton debitButton;
    private JLabel labelBalance;

    //SmartCard connection
    SmartCardManager javaCard;
    protected TerminalFactory factory;
    protected CardTerminal terminal;
    protected Card card;

    protected byte[] apduSelect;
    protected byte[] apduVerifyPIN;
    protected byte[] apduCreditToken;
    protected byte[] apduDebitToken;
    protected byte[] apduConfirmTransaction;
    protected String apduBalance;

    protected byte[] quantity;
    protected byte[] beforeQuantity;
    protected byte[] afterQuantity;


    public Form1(){

        javaCard = new SmartCardManager();

        panelConnect.setVisible(true);
        panelPIN.setVisible(false);
        panelManager.setVisible(false);

        comboBox1 = new JComboBox();
        comboBox1.setModel(new DefaultComboBoxModel(new String[] { "--select--" }));

        final Validator v = new Validator();

        loadReaderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<CardTerminal> terminals = javaCard.getTerminals(); //!!!
                    comboBox1.removeAllItems();
                    for (int i = 0; i < terminals.size(); i++)
                    {
                        comboBox1.addItem(terminals.get(i).getName());
                        System.out.println("Availble terminal name: " + terminals.get(i).getName());
                        readerStatus.setText(terminals.get(i).getName());
                    }
                    System.out.println("terminal array [0]: " + terminals.get(0).getName());
                    System.out.println("terminal array [1]: " + terminals.get(1).getName());
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(panel, "Getting problems while tried to access terminal list\n"+ex.getMessage()+".\nReresh agin or restart", "Could not get Terminals", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem().equals("--select--"))
                {
                    return;
                }
                CardTerminal cardReader = javaCard.getCardReader((String)comboBox1.getSelectedItem());
                try
                {
                    javaCard.connectToCard(cardReader);
                    panelManager.setVisible(true);
                }
                catch (CardException ex)
                {
                    JOptionPane.showMessageDialog(panel, "Problems while tried to connect with the smart card.\n"+ex.getMessage(), "Card Error", JOptionPane.ERROR_MESSAGE);
                }

                //Select applet
                String command = "00A404000500112233447F";
                apduSelect = SmartCardManager.hexStringToByteArray(command);
                try {
                    javaCard.sendApdu(apduSelect);
                } catch (CardException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Select response (int to hex):" + javaCard.getModuloSW());
                labelStatus.setText("Select: " + javaCard.getModuloSW());

                //send APDU - balance
                apduBalance = "00060000";
                try {
                    javaCard.sendApdu(SmartCardManager.hexStringToByteArray(apduBalance));
                } catch (CardException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Balance response (int to hex):" + javaCard.getModuloSW());
                labelBalance.setText("Balance: " + SmartCardManager.byteArrayToHexString(javaCard.getData()));
            }
        });

        creditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!fieldPoint.getText().equals("")) {
                    panelPIN.setVisible(true);
                    debitButton.setVisible(false);
                    creditButton.setEnabled(false);


                    quantity = SmartCardManager.hexStringToByteArray(fieldPoint.getText());

                    System.out.println("Value of fieldPoint: " + Arrays.toString(quantity));

                    byte beforeQuantity[] = new byte[]{0x00,0x02,0x00,0x00,0x01};
                    byte afterQuantity[] = new byte[]{0x01};

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                    try {
                        outputStream.write( beforeQuantity );
                        outputStream.write( quantity );
                        outputStream.write( afterQuantity );
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    apduCreditToken = outputStream.toByteArray();

                    System.out.println("var apduCreditToken value: " + Arrays.toString(apduCreditToken));
                }
                else {
                    JOptionPane.showMessageDialog(panel,
                            "nothing to add",
                            "panelManager",
                            JOptionPane.WARNING_MESSAGE);
                    System.out.println("Warning empty PIN field Entry");
                }
            }
        });

        debitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!fieldPoint.getText().equals("")) {
                    panelPIN.setVisible(true);
                    creditButton.setVisible(false);
                    debitButton.setEnabled(false);

                    quantity = SmartCardManager.hexStringToByteArray(fieldPoint.getText());

                    System.out.println("Value of fieldPoint: " + Arrays.toString(quantity));

                    byte beforeQuantity[] = new byte[]{0x00,0x04,0x00,0x00,0x01};
                    byte afterQuantity[] = new byte[]{0x01};

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                    try {
                        outputStream.write( beforeQuantity );
                        outputStream.write( quantity );
                        outputStream.write( afterQuantity );
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    apduDebitToken = outputStream.toByteArray( );

                    System.out.println("var apduDebitToken value: " + Arrays.toString(apduDebitToken));//TODO:[DONE] check if print apduDebitToken byte array
                }
                else {
                    JOptionPane.showMessageDialog(panel,
                            "Nothing to add",
                            "panelManager",
                            JOptionPane.WARNING_MESSAGE);
                    System.out.println("Warning empty PIN field Entry");
                }
            }
        });

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(fieldPIN.getPassword() == null){
                   return;
                }else {
                    String pinVault;

                    /*
                    System.out.println("PIN field is not empty. Success");
                    pinVault = new String(fieldPIN.getPassword());
                    apduVerifyPIN = SmartCardManager.hexStringToByteArray(pinVault);
                    System.out.println("[Debug] PINcontainer(delete after finish)" + Arrays.toString(apduVerifyPIN));
                    pinVault = "0";//"better safe than sorry"*/

                    quantity = SmartCardManager.hexStringToByteArray(new String(fieldPIN.getPassword()));
                    System.out.println("Value of fieldPoint: " + Arrays.toString(quantity));

                    byte beforeQuantity[] = new byte[]{0x00,0x20,0x00,0x00,0x04};
                    byte afterQuantity[] = new byte[]{0x7F};

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                    try {
                        outputStream.write( beforeQuantity );
                        outputStream.write( quantity );
                        outputStream.write( afterQuantity );
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    apduVerifyPIN = outputStream.toByteArray( );
                }

                //PIN verification
                try {
                    javaCard.sendApdu(apduVerifyPIN);
                    //int apduResponse = javaCard.getStatusWords();

                    System.out.println("Veryfy PIN response(int): " + javaCard.getModuloSW());
                    labelStatus.setText("Verify PIN: " + javaCard.getModuloSW());

                } catch (CardException e1) {
                    e1.printStackTrace();
                }

                //Transaction confirm
                if((javaCard.getSW1() == 144) && (javaCard.getSW2() == 0)) {
                    System.out.println(
                            "[Debug] Pin verification and check all global variables:\n" +
                            "PIN responce SW(int): SW1: " + javaCard.getSW1() + " SW2: " + javaCard.getSW2() +"\n" +
                                    "apduCreditToken: " + Arrays.toString(apduCreditToken) + "\n" +
                                    "apduDebitToken: " + Arrays.toString(apduDebitToken)
                    );
                    //System.out.println("Correct PIN number");


                    //TODO:[DONE]send all script APDUs and verify PIN at once
                    //Gather data transaction
                    try {
                        if(apduCreditToken != null) {
                            javaCard.sendApdu(apduCreditToken);
                            System.out.println("apduCreditToken: " + javaCard.getModuloSW());
                        }
                        else if(apduDebitToken != null) {
                            javaCard.sendApdu(apduDebitToken);
                            System.out.println("apduDebitToken: " + javaCard.getModuloSW());
                        }else
                            System.out.println("Both credit and debit were constructed incorrectly ");

                    } catch (CardException e1) {
                        e1.printStackTrace();
                    }
                    //send APDU confirmTransaction
                    try {
                        apduConfirmTransaction = SmartCardManager.hexStringToByteArray("00660000");
                        javaCard.sendApdu(apduConfirmTransaction);
                        System.out.println("apduConfirmTransaction: " + javaCard.getModuloSW());
                    } catch (CardException e1) {
                        e1.printStackTrace();
                    }

                }
                else {
                    System.out.println("Wrong PIN number\n" + "Transaction can't be verified");
                }
            }
        });
    }


  public JPanel getPanel() {
    return panel;
  }
}