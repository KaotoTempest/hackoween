package hack.idiotproof.fhritp;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class FHRITPTreeNode extends DefaultMutableTreeNode {

    private long uid;

    public FHRITPTreeNode() {
        // Empty
    }

    public FHRITPTreeNode(String value) {
        super(value);
    }
}
