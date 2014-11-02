package hack.idiotproof.fhritp;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ObserverJFrame extends JFrame implements Observer {

    public ObserverJFrame() {

    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}