package hack.idiotproof.fhritp;



import java.util.*;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class DataTree extends Observable {

    private boolean nodesHaveBeenAdded = false;
    private long uid = 0;
    private FHRITPTreeNode root;
    private FHRITPRequest fhritpRequest;

    public DataTree() {
        fhritpRequest = new FHRITPRequest();
        root = new FHRITPTreeNode("Portfolio");
    }

    public synchronized void add(List<String> values) {
        List<String> list = new LinkedList<>(values);
        add(list, root);

    }

    private synchronized void add(List<String> values, FHRITPTreeNode node) {
        if (!values.isEmpty()) {
            FHRITPTreeNode child = getChild(node, values.get(0));
            if (child == null) {
                FHRITPTreeNode newNode = new FHRITPTreeNode(values.get(0));
                newNode.setUID(uid++);
                node.add(newNode);
                values.remove(0);
                add(values, newNode);

            } else {
                values.remove(0);
                add(values, child);
            }
        }
    }

    public String get(List<String> values) {
        try {
            List<String> list = new LinkedList<>(values);
            list.add("");
            String value = getRec(list, root);
            return value.split("=")[1];
        } catch (Exception e) {
            return null;
        }
    }

    private String getRec(List<String> values, FHRITPTreeNode node) {
        if (!values.isEmpty()) {
            FHRITPTreeNode child = getChild(node, values.get(0));
            if (child == null && ((String) node.getUserObject()).contains("=")) {
                return ((String) node.getUserObject());
            } else if (child == null) {
                return null;
            } else {
                values.remove(0);
                return getRec(values, child);
            }
        } else {
            return null;
        }
    }

    private FHRITPTreeNode getChild(FHRITPTreeNode root, String value) {
        int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((String) ((FHRITPTreeNode) root.getChildAt(i)).getUserObject()).contains(value)) {
                return (FHRITPTreeNode) root.getChildAt(i);
            }
        }
        return null;
    }

    public FHRITPTreeNode getRoot() {
        return root;
    }

    public String getAndUpdate(List<String> values) throws Exception {
        if (get(values) == null) {
            fhritpRequest.sendRequest("ReferenceDataRequest", values.get(0), values.get(1), values.get(2),
                    "securities", Collections.singletonList(values.get(3)));
        }
        setChanged();
        notifyObservers();
        return get(values);
    }
}
