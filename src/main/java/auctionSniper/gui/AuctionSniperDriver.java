package auctionSniper.gui;

import auctionSniper.ui.Main;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 27/10/2014
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        named(Main.MainWindow.MAIN_WINDOW_NAME),
                        showingOnScreen()),
                 new AWTEventQueueProber(timeoutMillis, 100));
    }

    public void showSniperStatus(String statusText) {
        new JLabelDriver(
                this,
                named(Main.MainWindow.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
    }

}
