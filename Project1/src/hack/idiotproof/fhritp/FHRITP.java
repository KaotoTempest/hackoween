package hack.idiotproof.fhritp;

import hack.idiotproof.TextMessage;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class FHRITP {

    public static DataTree dataTree = new DataTree();

    public static void main(String[] args) throws Exception {
        String dataRequest = "ReferenceDataRequest";
        String element = "securities";
        String company = "IBM";
        String region = "US";
        String area = "Equity";
        List<String> fields = new LinkedList<>();
        fields.add("DS002");
        fields.add("PX_LAST");
        fields.add("VWAP_VOLUME");

        FHRITPRequest request = new FHRITPRequest();
        try {
            request.sendRequest(dataRequest, company, region, area, element, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] testList3 = new String[]{"IBM", "US", "Equity", "PX_LAST"};
        List<String> list3 = new LinkedList<>();
        Collections.addAll(list3, testList3);

        String[] testList2 = new String[]{"IBM", "US", "Equity", "DS002"};
        List<String> list2 = new LinkedList<>();
        Collections.addAll(list2, testList2);

        System.out.println("IBM BLA:   " + dataTree.get(list3));
        System.out.println("IBM BLA:   " + dataTree.get(list2));

//        TextMessage textMessage =  new TextMessage();
//        textMessage.checkMessages();

    }


}
