import menu.MenuAdmin;
import menu.MenuUser;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class Login extends JFrame{

    JFrame frameLogin = new JFrame();
    JFrame frameMenuUser = new JFrame();

    private JPanel contentPanel;
    private JPanel ContentPanel999;
    private JPasswordField fieldPassword;
    private JTextField fieldUsername;
    private JButton button1;


    private String matchUsernameUser = "user";
    private String matchPasswordUser = "123";
    private String matchUsernameAdmin = "admin";
    private String matchPasswordAdmin = "1234";


    private String valueLogin;
    private String valuePassword;

    //TODO: Write some password and account manegement in app

    public Login() {
        add(contentPanel);
        setTitle("Log in");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,200);
        setLocation(825,425);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Login login = new Login();

                valueLogin = fieldUsername.getText();
                valuePassword = new String(fieldPassword.getPassword());

                if(valueLogin.equals(matchUsernameUser) && valuePassword.equals(matchPasswordUser)){
                    System.out.println("logged as an user: " + valueLogin);
                    MenuUser menuUser = new MenuUser();
                    menuUser.setVisible(true);
                    login.setVisible(false);
                }
                else if(valueLogin.equals(matchUsernameAdmin) && valuePassword.equals(matchPasswordAdmin)){
                    System.out.println("You logged as an admin: " + valueLogin);
                    MenuAdmin menuAdmin = new MenuAdmin();
                    menuAdmin.setVisible(true);
                    login.setVisible(false);
                }
                else {
                    System.out.println("Try again...");
                }
            }
        });
        /*fieldUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });*/
    }

}
