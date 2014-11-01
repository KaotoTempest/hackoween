package hack.idiotproof.fhritp;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class History {

    private DefaultMutableTreeNode root;

    public History() {
        root = new DefaultMutableTreeNode();
    }

    public void add(List<String> values, DefaultMutableTreeNode root) {
        if (values.size() != 0) {
            DefaultMutableTreeNode child = getChild(root, values.get(0));
            if (child == null) {
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(values.get(0));
                root.add(newNode);
                values.remove(0);
                add(values, newNode);
            } else {
                values.remove(0);
                add(values, child);
            }
        }
    }

    private DefaultMutableTreeNode getChild(DefaultMutableTreeNode root, String value) {
       int childCount = root.getChildCount();
       for (int i=0; i<childCount; i++) {
           if (value.equalsIgnoreCase((String)((DefaultMutableTreeNode) root.getChildAt(i)).getUserObject())) {
               return (DefaultMutableTreeNode) root.getChildAt(i);
           }
       }
       return null;
    }

//    public List<String> getHistory(String company, String fieldName) {
//        try {
//            return historyMap.get(company).get(fieldName);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public String getLastValue(String company, String fieldName) {
//        try {
//            List<String> list = getHistory(company, fieldName);
//            return list.get(list.size() - 1);
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
}
