package hack.idiotproof.fhritp;

import hack.idiotproof.app.DesktopApp;
import hack.idiotproof.TextMessage;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class FHRITP {

    public static DataTree dataTree = new DataTree();

    public static void main(String[] args) throws Exception {

        initializeDemoData();

        // Create texting app
        // Message example: IBM US EQUITY PX_LAST to +441484598166
        TextMessage message = new TextMessage();
        Thread thread = new Thread(message);
        thread.start();

        // Create Bloomberg visualizer
        DesktopApp desktopApp = new DesktopApp();
        desktopApp.setLayout(null);
        FHRITPTreeNode root = dataTree.getRoot();
        desktopApp.setCurrentElement(root);
        ObserverJFrame frame = new ObserverJFrame();

        dataTree.addObserver(frame);
        dataTree.addObserver(desktopApp);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(desktopApp);
        frame.setVisible(true);
        Insets insets = frame.getInsets();
        frame.setSize(400 + insets.left + insets.right, 400 + insets.top + insets.bottom);

    }

    private static void initializeDemoData() {
        // Create some starter data
        // It may take a while
        String dataRequest = "ReferenceDataRequest";
        String element = "securities";
        String[] companies = new String[]{"IBM", "C", "MSFT", "APPL", "ADM"};
        String[] region = new String[]{"US", "LN", "CN", "UA"};
        String[] area = new String[]{"Index", "Equity"};
        List<String> fields = new LinkedList<>();
        fields.add("DS002");
        fields.add("PX_LAST");
        fields.add("VWAP_VOLUME");

        FHRITPRequest request = new FHRITPRequest();
        try {
            for (String company : companies) {
                for (String s : area) {
                    for (String r : region) {
                        request.sendRequest(dataRequest, company, r, s, element, fields);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
