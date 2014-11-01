package hack.idiotproof.fhritp;

import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class HistoryTest {

    private String[] testList = new String[]{"IBM", "US", "Index", "Equity", "PX_LAST"};
    private String[] testList2 = new String[]{"IBM", "UK", "Index", "Equity", "PX_LAST"};
    private String[] testList3 = new String[]{"IBM", "UK", "Index", "Equity", "DESC"};


    @Test
    public void testAdd() throws Exception {
        List<String> list = new LinkedList<>();
        Collections.addAll(list, testList);

        List<String> list2 = new LinkedList<>();
        Collections.addAll(list2, testList2);

        List<String> list3 = new LinkedList<>();
        Collections.addAll(list3, testList3);
        History history = new History();
        history.add(list);
        history.add(list2);
        history.add(list3);
    }
}