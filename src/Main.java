import menu.SmartCardManager;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        Login login = new Login();
        login.setVisible(true);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
        }
        });

    }
}
