package hack.idiotproof.Server;

import java.io.*;
import java.net.*;

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
        byte[] bytes = new byte[64];

        while (running) {
            DatagramPacket packet = new DatagramPacket(bytes, 64);
            try {
                serverSocket.receive(packet);
                System.out.println("Data: "+new String(packet.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
        running = true;

        InetAddress address = null;
        try {
            address = InetAddress.getByName("192.168.176.244");

            testDatagramSocket(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }

    public static void testDatagramSocket(InetAddress address) {
        byte[] bytes = new byte[64];
        bytes[0] = 'h';
        bytes[1] = 'e';
        bytes[2] = 'l';
        bytes[3] = 'l';
        bytes[4] = 'o';

        DatagramPacket packet = null;
        packet = new DatagramPacket(bytes, 64, address, 8000);
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
