package hack.idiotproof.App.Components;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by Adam Bedford on 01/11/2014.
 */
public class CircleComponent extends SpecialComponent {

    private final String text;

    public CircleComponent(String text) {
        this.text = text;
    }

    @Override
    public void paint(Graphics g) {
        paint((Graphics2D) g);
    }

    public void paint(Graphics2D g) {
        g.scale(getScale(), getScale());
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        float d = getWidth();
        if (getWidth() > getHeight()) {
            d = getHeight();
        }

        Color red = new Color(255, 0, 0, getAlpha());
        g.setColor(red);
        g.fill(new Ellipse2D.Float((getWidth() - d) / 2, (getHeight() - d) / 2, d, d));

        Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);

        float s = 1;
//        if (bounds.getWidth() > getWidth() * 0.75) {
//            s = (getWidth() * 0.75f) / (float) bounds.getWidth();
//            g.setFont(g.getFont().deriveFont(Font.BOLD, s));
//        }

        s = (getWidth() * 0.75f) / (float) bounds.getWidth();
        g.setFont(g.getFont().deriveFont(Font.BOLD, g.getFont().getSize() * s));
        bounds = g.getFontMetrics().getStringBounds(text, g);

        Color white = new Color(255, 255, 255, getAlpha());
        g.setColor(white);
        g.translate(getWidth() / 2, getHeight() / 2);
        g.drawString(text, -(float) bounds.getWidth() / 2, (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
    }
}
