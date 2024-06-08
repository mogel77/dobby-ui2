package de.x8games.dobby;



import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Helper {

    public static class Math {

        public static String roundBytes(float bytes) {
            String[] potenzen = new String[] { "", "k", "M", "G" };
            int potenz = 0;
            while(bytes > 1024.0f) {
                bytes /= 1024.0f;
                potenz++;
            }
            return round(bytes, 2) + " " + potenzen[potenz] + "Bytes";
        }

        public static String round(float number, int stellen) {
            final java.text.NumberFormat nf = java.text.NumberFormat.getInstance(java.util.Locale.US);
            nf.setMaximumFractionDigits(stellen);
            return nf.format(new BigDecimal(number));
        }

        /** @brief keine Ahnung wieso hier die API ein Float liefert, einfach mal Long erzwingen und schauen was passiert
         *  @param secs
         *  @return die Zeit als String */
        public static String calcTimeFromSeconds(float secs) {
             return calcTimeFromSeconds((long)secs);
        }
        public static String calcTimeFromSeconds(long secs) {
            int sek1 = 1;
            int min = (60*sek1);
            int std = (60*min);
            int tag = (24*std);
            int jah = (365*tag);

            long jaherg = secs/(tag)/365;
            long tagerg = (secs%jah)/(tag);
            long stderg = (secs%tag)/(std);
            long minerg = (secs%std)/(min);
            long sekerg = (secs%min*sek1);

            return tagerg + "d : " + stderg + "h : " + minerg + "min : " + sekerg + "s";
        }

        public static String unixtime2date(int unixtime) {
            Date date = new Date(unixtime*1000L);
            // the format of your date
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
            // give a timezone reference for formatting (see comment at the bottom)
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
            return sdf.format(date);
        }

    }

    public static class Gfx {

        public final static Font fBold = new Font("default", Font.BOLD, 20);
        public final static Font fPlain = new Font("default", Font.PLAIN, 16);
        public final static Font fSmall = new Font("default", Font.PLAIN, 12);

        public static void printRightAligned(Graphics2D gfx, String text, int liney, Font font, Color color) {
            int width = gfx.getFontMetrics(font).stringWidth(text);
            int x = gfx.getClipBounds().width - width - 10;
            gfx.setFont(font);
            gfx.setColor(color);
            gfx.drawString(text, x, liney);
        }

        public static void printRightAlignedShadow(Graphics2D gfx, String text, int liney, Font font, Color color, Color shadow) {
            int width = gfx.getFontMetrics(font).stringWidth(text);
            int x = gfx.getClipBounds().width - width - 10;
            gfx.setFont(font);
            gfx.setColor(shadow);
            gfx.drawString(text, x + 1, liney + 1);
            gfx.setColor(color);
            gfx.drawString(text, x, liney);
        }

        public static void printCentered(Graphics2D gfx, String text, int liney, Font font, Color color) {
            int width = gfx.getFontMetrics(font).stringWidth(text);
            int x = gfx.getClipBounds().width / 2 - width / 2;
            gfx.setFont(font);
            gfx.setColor(color);
            gfx.drawString(text, x, liney);
        }

        private static Color color = new Color(128, 128, 255);
        private static Color header = new Color(0,0,255, 16);

        public static void paintHEaderGradient(Graphics2D g2) {
            int w = g2.getClipBounds().width;
            int h = g2.getClipBounds().height;

            GradientPaint gradient = null;

            gradient = new GradientPaint(0, 0, color, 0, h/2, Color.LIGHT_GRAY);
            g2.setPaint(gradient);
            g2.fillRect(0, 0, w, h/2);

            gradient = new GradientPaint(0, h/2, Color.LIGHT_GRAY, 0, h, Color.GRAY);
            g2.setPaint(gradient);
            g2.fillRect(0, h/2, w, h);
        }

        public static void paintHeaderBackground(Graphics2D g) {
            int w = g.getClipBounds().width;
            int h = g.getClipBounds().height;

            g.setColor(header);
            g.fillRect(0, 0, w, h);

            g.setColor(Color.GRAY);
            g.drawLine(0, h - 2, w, h - 2);
            g.setColor(Color.WHITE);
            g.drawLine(0, h - 1, w, h - 1 );
        }

        public static void scale(ImageIcon icon) {
            scale(icon, 32);
        }

        public static void scale(ImageIcon icon, int size) {
            scale(icon, size, size);
        }

        public static void scale(ImageIcon icon, int width, int height) {
            icon.setImage(icon.getImage().getScaledInstance(width,height, Image.SCALE_AREA_AVERAGING));
        }

    }

}
