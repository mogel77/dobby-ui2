package de.x8games.dobby.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;




public abstract class BaseDesktop<T> extends JPanel {

    protected Logger logger = LogManager.getLogger("ui");

    private static BufferedImage logo;

    private T root;

    protected BaseDesktop(ObjectUI root) {
        super(new BorderLayout());
        add(root.getUIPane(), BorderLayout.CENTER);
        this.root = (T) root;
        root.getUIPane().setOpaque(false);
    }

    protected T ui() {
        return root;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Qualitatives Zeichen
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        paintLogo(g);
    }

    protected synchronized void paintLogo(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        if (logo == null) {
//            try {
//                logo = ImageIO.read(new File("gfx/x8games.png"));
//            } catch (IOException e) {
//                Logging.error(e);
//            }
        } else {
            g2.drawImage(logo, 650, 256, 128, 128, this);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        int w = getWidth();
        int h = getHeight();

        g.setColor(Color.WHITE);
        g.drawLine(0, 1, w, 1);
        g.drawLine(0, h - 1, w, h - 1);
        g.setColor(Color.GRAY);
        g.drawLine(0, 0, w, 0);
        g.drawLine(0, h - 2, w, h - 2);

        // -> in Footer - Helper.Gfx.printRightAligned(g2,serverVersion,25, Helper.Gfx.fSmall, Color.GRAY);
        // TODO Helper.Gfx.printRightAlignedShadow(g2, printer.getProfile().getName(), 43, Helper.Gfx.fBold, Color.WHITE, Color.DARK_GRAY);

//        Helper.Gfx.printRightAligned(g2,"x8Games.de",h - 10, Helper.Gfx.fSmall, Color.GRAY);
    }

    /**
     * wird aufgerufen, wenn der Desktop aktiviert wurde
     */
    public abstract void activated();

    /**
     * wird aufgerufen, wenn der Desktop deaktiviert wurde
     */
    public abstract void deactivated();

}
