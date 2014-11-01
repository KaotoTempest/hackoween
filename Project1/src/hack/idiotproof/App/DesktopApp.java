package hack.idiotproof.App;

import hack.idiotproof.fhritp.DataTree;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adam Bedford on 01/11/2014.
 */
public class DesktopApp extends JPanel {
    private DataTree history;
    // private ## currentElement

    public DesktopApp(LayoutManager layoutManager, boolean b, DataTree history) {
        super(layoutManager, b);
        this.history = history;
    }

    public DesktopApp(LayoutManager layoutManager, DataTree history) {
        super(layoutManager);
        this.history = history;
    }

    public DesktopApp(boolean b, DataTree history) {
        super(b);
        this.history = history;
    }

    public DesktopApp(DataTree history) {
        this.history = history;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        /**
         * Get current Element
         * Get children (orbital elements)
         * Get parent (back button)
         */

        /**
         * Draw Circle for element
         * Draw orbiting circles for child elements (make gravitate towards mouse cursor)
         * Draw smaller orbiting circle for parent
         */
    }
}
