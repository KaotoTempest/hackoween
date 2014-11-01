package hack.idiotproof.Server;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static hack.idiotproof.Server.UIDataRequest.SubEnum;
import static hack.idiotproof.Server.UIDataRequest.SubEnum.*;

/**
 * Created by Adam Bedford on 01/11/2014.
 */
public class DServer implements Runnable {
    private DatagramSocket serverSocket;
    private boolean running = false;

    public DServer(int port) throws IOException {
        serverSocket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        System.out.println("Server Started");
        byte[] bytes = new byte[256];

        while (running) {
            DatagramPacket packet = new DatagramPacket(bytes, 64);
            try {
                serverSocket.receive(packet);

                UIDataRequest request = new UIDataRequest(packet.getData());

                processRequest(request);

                System.out.println("Data: " + request.value);
                System.out.println("Type: " + request.submissionType);
                System.out.println("Address: " + packet.getAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processRequest(UIDataRequest request) {
        switch (request.submissionType) {
            case NEW_ENTITY: {
                /**
                 * TODO - Insert code to handle new entities here, eg. IBM, MSFT, etc.
                 */
            }
            break;
            case NEW_FIELD: {
                String[] split = request.value.split("\\ ");

                List<String> list = new LinkedList<>();
                Collections.addAll(list, split);

                /**
                 * TODO - Insert code to handle new fields
                 */
            }
            break;
            case REQUEST: {
                /**
                 *
                 */
            }
            break;

        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void startWithTest(){
        start();

        InetAddress address = null;
        try {
            address = InetAddress.getByName("192.168.176.244");

            testDatagramSocket(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void testDatagramSocket(InetAddress address) {
        UIDataRequest request = new UIDataRequest(NEW_ENTITY, "MSFT");
        byte[] bytes = new byte[0];

        try {
            bytes = request.toByteArray();
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 8000);
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
