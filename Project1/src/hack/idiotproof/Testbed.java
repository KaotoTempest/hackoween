package hack.idiotproof;

import com.bloomberglp.blpapi.Event;
import hack.idiotproof.server.DServer;
import hack.idiotproof.server.Server;

import java.io.IOException;

/**
 * Created by Adam Bedford on 31/10/2014.
 */
public class Testbed {

    private static void handleDataEvent(Event event)throws Exception
    {

    }

    private static void handleOtherEvent(Event event) throws Exception
    {

    }

    public static void main(String[] args)
    {
        try {
            Server server = new Server(5000);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DServer server = new DServer(8000);
            server.startWithTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
