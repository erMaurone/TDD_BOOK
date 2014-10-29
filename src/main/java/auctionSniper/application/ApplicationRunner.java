package auctionSniper.application;

import auctionSniper.gui.AuctionSniperDriver;
import auctionSniper.server.FakeAuctionServer;
import auctionSniper.ui.Main;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 27/10/2014
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
        Thread thread = new Thread("TestApplication") {
            @Override public void run() {
            try {
                Main.main(auction.XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD,
                        auction.getItemId());
                } catch(Exception e) {
                e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showSniperStatus(Main.MainWindow.STATUS_JOINING);

    }

    public void showSniperHasLostAuction() {
        driver.showSniperStatus(Main.MainWindow.STATUS_JOINING);
    }

    public void stop() {
        if(driver != null)
            driver.dispose();
    }
}
