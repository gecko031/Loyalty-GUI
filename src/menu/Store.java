package menu;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Store extends JFrame{

    private JPanel panelHeader;
    private JPanel panelParts;
    private JPanel Item1;
    private JLabel nameItem1;
    private JSpinner spinnerItem1;
    private JLabel priceItem1;
    private JPanel Item2;
    private JLabel nameItem2;
    private JLabel priceItem2;
    private JSpinner spinnerItem2;
    private JPanel Item3;
    private JLabel nameItem3;
    private JLabel priceItem3;
    private JSpinner spinnerItem3;
    private JPanel Item4;
    private JLabel nameItem4;
    private JLabel priceItem4;
    private JSpinner spinnerItem4;
    private JPanel Item5;
    private JLabel nameItem5;
    private JLabel priceItem5;
    private JSpinner spinnerItem5;
    private JPanel Item6;
    private JLabel nameItem6;
    private JLabel priceItem6;
    private JSpinner spinnerItem6;
    private JPanel panelManagement;
    private JPanel panelNavigation;
    private JButton logOutButton;
    private JButton goToCartButton;
    private JPanel panelBalance;
    private JLabel labelBalance;
    private JPanel panelSummary;
    private JLabel labelItemQuantity;
    private JLabel labelItemTotalPrice;
    private JLabel labelBalanceAfterOrder;
    private JButton checkoutButton;
    private JPanel panelStore;
    private JPasswordField fieldPassword;

    private byte[] apduSelect;
    private byte[] apduVerifyPIN;
    private byte[] apduDebitToken;
    private byte[] apduConfirmTransaction;
    private String apduBalance;

    private Integer orderQuantity;
    private int orderPrice;
    private String balance;


    public Store() {

        add(panelStore);
        pack();
        setVisible(true);
        setSize(500,500);
        setLocation(825,425);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );

        panelSummary.setVisible(false);

        final SmartCardManager javaCard = new SmartCardManager();

        List<CardTerminal> terminals;
        try {
            terminals = javaCard.getTerminals();
            CardTerminal cardReader = javaCard.getCardReader(terminals.get(0).getName());
            javaCard.connectToCard(cardReader);
        } catch (Exception e) {
            e.printStackTrace();
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

        //send APDU - balance
        String apduBalance = "00060000";
        try {
            javaCard.sendApdu(SmartCardManager.hexStringToByteArray(apduBalance));
        } catch (CardException e1) {
            e1.printStackTrace();
        }
        System.out.println("Balance response (int to hex):" + javaCard.getModuloSW());
        System.out.println("Balance response (int to hex):" + javaCard.getModuloSW());
        balance = SmartCardManager.byteArrayToString(javaCard.getData());
        labelBalance.setText("Balance: " + Long.parseLong(balance, 16));



        goToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderPrice = sumOfCosts();
                orderQuantity = sumOfPieces();

                labelItemQuantity.setText(String.valueOf(orderQuantity));
                labelItemTotalPrice.setText(String.valueOf(orderPrice));

                int balanceConvert = parseInt(balance, 16);
                int balanceAfterDebit = balanceConvert - orderPrice;

                labelBalanceAfterOrder.setText(String.valueOf(balanceAfterDebit));


                if(balanceAfterDebit > 0){

                    apduDebitToken = new byte[] {
                            0x00, 0x04, 0x00, 0x00, 0x01,  // quantity start
                            (byte) orderPrice,
                            0x01
                    };
                    System.out.println(Arrays.toString(apduDebitToken));
                    System.out.println("[BYTE ARRAY]Display array effect");
                    System.out.format("0x%x ", apduDebitToken[5]);

                    panelSummary.setVisible(true);
                }else
                    JOptionPane.showMessageDialog(panelNavigation, "You can not afford these products\nPlease remove some of goods");
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("logOut listener");
                dispose();
            }
        });


        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("checkout button working");

                //PIN verification
                if(fieldPassword.getPassword() == null){
                    return;
                }else {

                    byte[] quantity = SmartCardManager.hexStringToByteArray(new String(fieldPassword.getPassword()));
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
                    //labelStatus.setText("Verify PIN: " + javaCard.getModuloSW());

                } catch (CardException e1) {
                    e1.printStackTrace();
                }

                //Transaction confirm
                if((javaCard.getSW1() == 144) && (javaCard.getSW2() == 0)) {
                    System.out.println(
                            "[Debug] Pin verification and check all global variables:\n" +
                                    "PIN responce SW(int): SW1: " + javaCard.getSW1() + " SW2: " + javaCard.getSW2() +"\n" +
                                    "apduDebitToken: " + Arrays.toString(apduDebitToken)
                    );

                    //Gather data transaction
                    try {
                        if(apduDebitToken != null) {
                            javaCard.sendApdu(apduDebitToken);
                            System.out.println("apduDebitToken: " + javaCard.getModuloSW());
                        }else
                            System.out.println("Debit was constructed incorrectly ");

                    } catch (CardException e1) {
                        e1.printStackTrace();
                    }
                    //TODO: Uncomment this
                    //send APDU confirmTransaction
                    try {
                        apduConfirmTransaction = SmartCardManager.hexStringToByteArray("00660000");
                        javaCard.sendApdu(apduConfirmTransaction);
                        System.out.println("apduConfirmTransaction: " + javaCard.getModuloSW());
                    } catch (CardException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(panelNavigation, "Payment completed, thank You.");

                }
                else {
                    System.out.println("Wrong PIN number\n" + "Transaction can't be verified");
                }
            }
        });
    }
    public int sumOfPieces(){
        int sum = 0;
        sum += (int) spinnerItem1.getValue();
        sum += (int) spinnerItem2.getValue();
        sum += (int) spinnerItem3.getValue();
        sum += (int) spinnerItem4.getValue();
        sum += (int) spinnerItem5.getValue();
        sum += (int) spinnerItem6.getValue();
        System.out.println("sumOfCostsMethod: " + sum);
        return sum;
    }
    public int sumOfCosts(){
        int sum = 0;
        sum += (int) spinnerItem1.getValue() * parseInt(priceItem1.getText());
        sum += (int) spinnerItem2.getValue() * parseInt(priceItem2.getText());
        sum += (int) spinnerItem3.getValue() * parseInt(priceItem3.getText());
        sum += (int) spinnerItem4.getValue() * parseInt(priceItem4.getText());
        sum += (int) spinnerItem5.getValue() * parseInt(priceItem5.getText());
        sum += (int) spinnerItem6.getValue() * parseInt(priceItem6.getText());
        System.out.println("sumOfCostsMethod: " + sum);
        return sum;
    }
    private byte[] bigIntToByteArray( final int i ) {
        BigInteger bigInt = BigInteger.valueOf(i);
        return bigInt.toByteArray();
    }
    public JPanel getPanel() {
        return panelStore;
    }
}
