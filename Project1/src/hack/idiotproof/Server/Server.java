package hack.idiotproof.Server;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Adam Bedford on 01/11/2014.
 */
public class Server implements Runnable {
    private ServerSocket serverSocket;
    private boolean running = false;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        System.out.println("Server Started");
        while (running) {
            try {
                Socket socket = serverSocket.accept();

                if (socket != null) {
                    System.out.println(socket.getLocalAddress());

                    new Thread(new SocketRunnable(socket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
        running = true;
    }

    class SocketRunnable implements Runnable {
        private Socket socket;
        private boolean running = true;

        SocketRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                DataInputStream dis = new DataInputStream(inputStream);
                DataOutputStream dos = new DataOutputStream(outputStream);

                PrintStream ps = new PrintStream(outputStream);
                System.out.println("Thread for " + socket.getLocalAddress() + " started.");

                Map<String, String> handshakeMap = new TreeMap<>();
                String str;
                int idx = 0;
                while ((str = dis.readLine()) != null) {
                    if (str.isEmpty()) break;

                    System.out.println((idx++) + ": " + str);
                    String[] split = str.split(":");
                    if (split.length > 1)
                        handshakeMap.put(split[0], split[1]);
                }

                String key = handshakeMap.get("Sec-WebSocket-Key");
                if (key != null) {
                    System.out.println(key);
                    key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

                    try {
                        byte[] bytes = MessageDigest.getInstance("SHA-1").digest(key.getBytes());
                        bytes = Base64.encodeBase64(bytes);
                        String s = new String(bytes);
                        System.out.println(s);
                        String s1 = "HTTP/1.1 101 Switching Protocols\n" +
                                "Upgrade: websocket\n" +
                                "Connection: Upgrade\n" +
                                "Sec-WebSocket-Accept: " + s.trim()+"\n";
                        System.out.println(s1);
//                        ps.println(s1);

                        new FileOutputStream(new File("dump.file")).write(s1.getBytes());
                        dos.write(s1.getBytes());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }

                byte[] buf = new byte[64];
                while (running) {

                    while ((str = dis.readLine()) != null) {
                        if(str.isEmpty()) break;
                        System.out.println((idx++) + ": " + str);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
