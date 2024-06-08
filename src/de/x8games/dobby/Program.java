package de.x8games.dobby;

import de.x8games.dobby.ui.MainPanelUI;
import de.x8games.octolib3.OctoPrinter;
import de.x8games.octolib3.requests.Connection;
import de.x8games.octolib3.requests.CurrentUser;
import de.x8games.octolib3.requests.Server;
import de.x8games.octolib3.requests.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import static java.awt.GraphicsEnvironment.*;





public class Program {

    static {
        System.setProperty("log4j.configurationFile", "logger.xml");
    }

    protected static Logger logger = LogManager.getLogger("dobby");



    private static OctoPrinter octopi;
    public static OctoPrinter Printer() {
        return octopi;
    }



    public static void main(String [] args) {
        Settings.init(".", "configuration.txt");
        octopi = new OctoPrinter(args[0], args[1]);
        initLAF();
        SwingUtilities.invokeLater(() -> initUI());
    }

    private void checkAPI() {
        octopi.execute(new Version());
        octopi.execute(new CurrentUser());
        octopi.execute(new Server());
        Connection c = new Connection();
        octopi.execute(c);
        logger.info(c);
    }

    private static void initUI() {
        Thread.currentThread().setName("ui-thread");

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setUndecorated(true);
        frame.setSize(800, 480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(new MainPanelUI(octopi).getRootPanel(), BorderLayout.CENTER);
    }

    private static void initLAF() {
        loadFont("RobotoCondensed-Regular.ttf");
        try {
            Properties props = new Properties();
            props.put("windowDecoration", "off");
            props.put("userTextFont", "Roboto Condensed 16");
            props.put("controlTextFont", "Roboto Condensed 16");
            AluminiumLookAndFeel.setCurrentTheme(props);
            String laf = Settings.I().get("ui.lookandfeel.laf", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            UIManager.setLookAndFeel(laf);
        } catch(Exception e) {
            LogManager.getLogger().error(e);
        }
    }
    private static void loadFont(String fontname) {
        fontname = "./fonts/" + fontname;
        try {
            InputStream is = new FileInputStream(fontname);
            if (is != null) {
                Font f = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment genv = getLocalGraphicsEnvironment();
                boolean result = genv.registerFont(f);
                if (!result) {
                    LogManager.getLogger().warn("Font konnte nicht registriert werden - existier evt. schon");
                } else {
                    LogManager.getLogger().info("Font erfolgreich intialisiert");
                }
            }
        } catch(Exception ex) {
            LogManager.getLogger().error(ex);
        }
    }

}
