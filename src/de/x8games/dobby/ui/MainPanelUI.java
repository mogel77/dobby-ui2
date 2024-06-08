package de.x8games.dobby.ui;

import de.x8games.dobby.Helper;
import de.x8games.dobby.ui.desktops.Home;
import de.x8games.dobby.ui.desktops.Spam;
import de.x8games.octolib3.OPiCallback;
import de.x8games.octolib3.OPiRequest;
import de.x8games.octolib3.OctoPrinter;
import de.x8games.octolib3.requests.Version;
import de.x8games.octolib3.events.ConnectionStateListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;





public class MainPanelUI {
    private JPanel rootPanel;
    private JPanel pnlHeader;
    private JPanel pnlFooter;
    private JPanel pnlDesktops;
    private JLabel lblIcon;
    private JLabel lblTime;
    private JLabel lblMessage;
    private JLabel lblConnectionState;
    private JLabel lblHotend;
    private JLabel lblBed;
    private JPanel pnlIcons;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    //region . Card-Namen .
    public static final String CARD_SPAM = "spam";
    public static final String CARD_HOME = "home";
    public static final String CARD_GRID = "grid";
    public static final String CARD_MANUAL = "manual";
    public static final String CARD_CAMERA = "camera";
    public static final String CARD_DISK = "disk";
    public static final String CARD_SETTINGS = "settings";
    public static final String CARD_TEMPERATURE = "temperatur";
    public static final String CARD_LOGGER = "logger";
    //endregion

    private Logger logger = LogManager.getLogger("ui");
    private final CardLayout cards;
    private final Map<String, BaseDesktop> tuple = new HashMap<>();
    private BaseDesktop olddesktop;

    private OctoPrinter dobby;

    private ImageIcon CON_OFFLINE = new ImageIcon("gfx/connect_no.png");
    private ImageIcon CON_CONNECT = new ImageIcon("gfx/connect_established.png");

    public MainPanelUI(OctoPrinter dobby) {
        this.dobby = dobby;
        dobby.addConnectionStateListener(new ConnectionStateListener() {
            @Override
            public void OnConnected() {
                lblConnectionState.setIcon(CON_CONNECT);
            }

            @Override
            public void OnDisconnected() {
                lblConnectionState.setIcon(CON_OFFLINE);
            }
        });

        Helper.Gfx.scale(CON_OFFLINE);
        Helper.Gfx.scale(CON_CONNECT);

        cards = (CardLayout) pnlDesktops.getLayout();
        initButtons();
        initCards();
        lblConnectionState.setIcon(CON_OFFLINE);
        updateVersionInformation();
    }

    private void updateVersionInformation() {
        dobby.execute(new OPiCallback(new Version()) {
            @Override
            public void OnSuccessful(OPiRequest command) {
                logger.info("conncted");
            }
            @Override
            public void OnFailedAsync(OPiRequest command) {
                try { Thread.sleep(5 * 1000); } catch (Exception ex) { logger.error(ex); }
                updateVersionInformation();
            }
        });
    }

    private void addToolButton(String image, String card) {
        ImageIcon icon = new ImageIcon(image);
        Helper.Gfx.scale(icon);
        JLabel l = new JLabel(icon, JLabel.CENTER);
        l.setPreferredSize(new Dimension(40, 40));
        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            logger.info("wechsel auf Desktop '" + card + "'");
            showDesktop(card);
            }
        });
        pnlIcons.add(l);
    }

    //region . Card-Verwaltung .
    private void initCards() {
        addCard(new Spam(), CARD_SPAM);
        addCard(new Home(), CARD_HOME);
        addCard(new Spam(), CARD_MANUAL);
    }
    private void initButtons() {
        addToolButton("gfx/toolbar/home.png", MainPanelUI.CARD_HOME);
        addToolButton("gfx/toolbar/manual.png", MainPanelUI.CARD_MANUAL);
    }
    private void showDesktop(String desktopid) {
        cards.show(pnlDesktops, desktopid);
        var d = tuple.get(desktopid);
        d.activated();

        // alten Kram abschalten
        if (olddesktop != null) olddesktop.deactivated();
        olddesktop = d;
    }
    private void addCard(BaseDesktop desktop, String card) {
        tuple.put(card, desktop);
        pnlDesktops.add(desktop, card);
    }
    //endregion

}
