import menuAdmin.MenuAdmin;

import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame{

    JFrame frameLogin = new JFrame();
    JFrame frameMenuUser = new JFrame();

    private JPanel contentPanel;
    private JPanel ContentPanel999;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton button1;


    private String matchUsernameUser = "user";
    private String matchPasswordUser = "123";
    private String matchUsernameAdmin = "admin";
    private String matchPasswordAdmin = "1234";


    private String valueLogin;
    private String valuePassword;



    public Login() {
        add(contentPanel);
        setTitle("Log in");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,200);
        setLocation(825,425);

        // TODO: move to another window
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                //MenuUser menuUser = new MenuUser();
                MenuAdmin menuAdmin = new MenuAdmin();

                valueLogin = textField1.getText();

                if(valueLogin.equals(matchUsernameUser)){
                    MenuUser menuUser = new MenuUser();
                    System.out.println("logged as an user: " + valueLogin);

                    menuUser.setVisible(true);
                    dispose();
                }
                else if(valueLogin.equals(matchUsernameAdmin)){
                    System.out.println("You logged as an admin: " + valueLogin);

                    menuAdmin.setVisible(true);
                    login.setVisible(false);
                }else {
                    System.out.println("You loggged nowhere, keep on trying...");
                }
            }
        });
        /*textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });*/
    }

}
