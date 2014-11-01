//package hack.idiotproof;
//
//import com.bloomberglp.blpapi.Event;
//import com.bloomberglp.blpapi.impl.ez;
//import com.twilio.sdk.*;
//import com.bloomberglp.blpapi.*;
//import hack.idiotproof.Server.DServer;
//import hack.idiotproof.Server.Server;
//import hack.idiotproof.Server.XServer;
//import org.apache.commons.codec.binary.Base64;
//import org.jwebsocket.console.JWebSocketServer;
//import org.jwebsocket.factory.JWebSocketFactory;
//
//import javax.swing.tree.MutableTreeNode;
//import javax.swing.tree.TreeNode;
//import java.util.*;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.LinkedList;
//
///**
// * Created by Adam Bedford on 31/10/2014.
// */
//public class Testbed {
//
//    private static void handleDataEvent(Event event)throws Exception
//    {
//
//    }
//
//    private static void handleOtherEvent(Event event) throws Exception
//    {
//
//    }
//
//    public static void main(String[] args)
//    {
////        String s1 = "dGhlIHNhbXBsZSBub25jZQ==258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
////String s2 = "";
////        for (byte b : s1.getBytes()) {
////            s2 += Integer.toHexString(b);
////        }
//
////        try {
////            MessageDigest digest = MessageDigest.getInstance("SHA-1");
////            byte[] bytes = digest.digest(s1.getBytes());
////            bytes = Base64.encodeBase64(bytes);
////
////            try {
////                System.out.println(new String(bytes, "UTF-8"));
////            } catch (UnsupportedEncodingException e) {
////                e.printStackTrace();
////            }
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        }
//        String path[] = {"MSFT US Index", "IBM US Equity", "MSFT US EQUITY"};
//        String field[] = {"PX_LAST=0", "PX_LAST=0", "PX_LAST=0"};
//
//
//        try {
//            Server server = new Server(5000);
//            server.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            DServer server = new DServer(8000);
//            server.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////    class TreeNode {
////        String name;
////        List<TreeNode> children = new LinkedList<>();
////
////        public TreeNode getChild(List<String> path){
////
////        }
////    }
////
////    private add(String path, String field){
////
////    }
//}
