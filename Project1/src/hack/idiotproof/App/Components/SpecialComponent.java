package hack.idiotproof.App.Components;

import java.awt.*;

/**
* Created by Adam Bedford on 01/11/2014.
*/
public class SpecialComponent extends Component {
    private int alpha = 255;
    private double scale = 1;

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
