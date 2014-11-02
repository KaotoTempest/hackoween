package hack.idiotproof.fhritp;

import hack.idiotproof.App.DesktopApp;
import hack.idiotproof.TextMessage;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class FHRITP {

    public static DataTree dataTree = new DataTree();

    public static void main(String[] args) throws Exception {

        // Create dummy bubbles
        String dataRequest = "ReferenceDataRequest";
        String element = "securities";
        String company = "IBM";
        String region = "US";
        String area = "Equity";
        List<String> fields = new LinkedList<>();
        fields.add("DS002");
        fields.add("PX_LAST");
        fields.add("VWAP_VOLUME");

        FHRITPRequest request = new FHRITPRequest();
        try {
            request.sendRequest(dataRequest, company, region, area, element, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create texting app
        TextMessage message = new TextMessage();
        Thread thisThread = new Thread(message);
        thisThread.start();

        DesktopApp desktopApp = new DesktopApp();
        dataTree.addObserver(desktopApp);
        desktopApp.setLayout(null);
        FHRITPTreeNode root = dataTree.getRoot();
        desktopApp.setCurrentElement(root);
        ObserverJFrame frame = new ObserverJFrame();
        dataTree.addObserver(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(desktopApp);
        frame.setVisible(true);
        Insets insets = frame.getInsets();
        frame.setSize(400 + insets.left + insets.right, 400 + insets.top + insets.bottom);

    }
}
