package hack.idiotproof.fhritp;

import java.util.LinkedList;
import java.util.List;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class DataTree {

    private long uid = 0;
    private FHRITPTreeNode root;

    public DataTree() {
        root = new FHRITPTreeNode();
    }

    public void add(List<String> values) {
        List<String> list = new LinkedList<>(values);
        add(list, root);
    }
    private void add(List<String> values, FHRITPTreeNode root) {
        if (values.size() != 0) {
            FHRITPTreeNode child = getChild(root, values.get(0));
            if (child == null) {
                FHRITPTreeNode newNode = new FHRITPTreeNode(values.get(0));
                newNode.setUID(uid++);
                root.add(newNode);
                values.remove(0);
                add(values, newNode);
            } else {
                values.remove(0);
                add(values, child);
            }
        }
    }

    public String get(List<String> values) {
        return get(values, root);
    }

    private String get(List<String> values, FHRITPTreeNode root) {
        if (values.size() != 0) {
            FHRITPTreeNode child = getChild(root, values.get(0));
            if (child == null) {
                return null;
            } else {
                values.remove(0);
                return get(values, child).split("=")[1];
            }
        }

        return null;
    }

    private FHRITPTreeNode getChild(FHRITPTreeNode root, String value) {
       int childCount = root.getChildCount();
       for (int i=0; i<childCount; i++) {
           if (value.equals((String)((FHRITPTreeNode) root.getChildAt(i)).getUserObject())) {
               return (FHRITPTreeNode) root.getChildAt(i);
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
