package auctionSniper.ui;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 27/10/2014
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    @SuppressWarnings("unused") private Chat notToBeGCd;

    private MainWindow ui;
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    public Main() throws Exception {
        startUserInterface();

    }

    public static void main(String... args) throws Exception {
        System.out.println(args[ARG_HOSTNAME] + " " +
                args[ARG_USERNAME] + " " + args[ARG_PASSWORD] + " " + args[ARG_ITEM_ID]);
        Main main = new Main();
        main.joinAuction(connection(args[ARG_HOSTNAME],args[ARG_USERNAME], args[ARG_PASSWORD]),
                args[ARG_ITEM_ID]);
    }

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        final Chat chat = connection.getChatManager().createChat(
                auctionId(itemId, connection), new MessageListener() {
            @Override
            public void processMessage(Chat aChat, Message message) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ui.showStatus(MainWindow.STATUS_LOST);
                        System.out.println("set message to " + MainWindow.STATUS_LOST);
                    }
                });
            }
        });

        this.notToBeGCd = chat;
        chat.sendMessage(new Message());
    }

    private static XMPPConnection connection(String hostname, String username, String password) throws XMPPException {

        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        System.out.println(String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName()));
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    public static class MainWindow extends JFrame {
        public static final String MAIN_WINDOW_NAME = "sniper status";
        public static final String STATUS_LOST = "lost";
        public static final String SNIPER_STATUS_NAME ="sniper status name";
        public static final String STATUS_JOINING = "joining";
        private final JLabel sniperStatus = createLabel(STATUS_JOINING);

        public MainWindow() {
            super("Auction Sniper");
            setName(MAIN_WINDOW_NAME);
            add(sniperStatus);
            pack();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            setBackground(Color.CYAN);
        }

        private static JLabel createLabel(String initialText) {
            JLabel result = new JLabel(initialText);
            result.setName(SNIPER_STATUS_NAME);
            result.setBorder(new LineBorder(Color.BLACK));
            result.setBackground(Color.BLUE);
            result.setVisible(true);
            return result;
        }

        public void showStatus(String status) {
            sniperStatus.setText(status);
        }
    }
}
