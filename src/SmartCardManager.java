import javax.smartcardio.*;
import java.util.List;

public class SmartCardManager {

    protected List<CardTerminal> terminals;
    protected static CardTerminal terminal;
    protected static TerminalFactory factory;
    protected static Card card;
    public static ResponseAPDU rAPDU;
    protected static CardChannel channel;
    static boolean IsConnectedToCard = false;
    static String lastMessage ;

    public SmartCardManager(List<CardTerminal> terminals) {
        //this.terminals = terminals;
    }

    public List<CardTerminal> getTerminals() throws Exception {
        factory = TerminalFactory.getDefault();
        terminals = factory.terminals().list();

        return terminals;
    }
    protected void connectToCard(CardTerminal terminalSource) throws Exception {
        terminal = terminalSource;
        card = terminal.connect("T=1");//if "*" detects protocol automatically
    }


}