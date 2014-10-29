package auctionSniper;

import auctionSniper.application.ApplicationRunner;
import auctionSniper.server.FakeAuctionServer;
import org.junit.After;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 27/10/2014
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */
public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem(); //step 1
        application.startBiddingIn(auction); //step 2
        auction.hasReceivedJoinRequestFromSniper();   //step 3
        auction.announceClosed(); //step 4
        application.showSniperHasLostAuction(); //step 5
    }

    //Additional cleanup
    @After
    public void stopAuction() {
        auction.stop();
    }

    @After public void stopApplication() {
        application.stop();
    }
}
