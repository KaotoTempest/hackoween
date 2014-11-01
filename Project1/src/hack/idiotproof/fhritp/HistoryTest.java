package hack.idiotproof.fhritp;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class HistoryTest {

    private String[] testList = new String[]{"IBM", "US", "Index", "Equity", "PX_LAST", "20"};
    private String[] testList2 = new String[]{"IBM", "UK", "Index", "Equity", "PX_LAST", "1"};
    private String[] testList3 = new String[]{"IBM", "UK", "Index", "Equity", "DESC", "12"};


    @Test
    public void testAdd() throws Exception {
        List<String> list = new LinkedList<>();
        Collections.addAll(list, testList);

        List<String> list2 = new LinkedList<>();
        Collections.addAll(list2, testList2);

        List<String> list3 = new LinkedList<>();
        Collections.addAll(list3, testList3);
        DataTree history = new DataTree();
        history.add(list);
        System.out.println(history.get(list));
        history.add(list2);
        history.add(list3);

        String value = history.get(list);
        System.out.println("MOLOOOOOOOOOOOOOZ " + value);
    }
}