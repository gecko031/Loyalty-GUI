import menuAdmin.SmartCardManager;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        Login login = new Login();
        login.setVisible(true);
        SmartCardManager scm = new SmartCardManager();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
        }
        });

    }
}
