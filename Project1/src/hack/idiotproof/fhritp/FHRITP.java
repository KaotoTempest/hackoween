package hack.idiotproof.fhritp;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.List;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class FHRITP {

    public static DataTree dataTree = new DataTree();

    public static void main(String[] args) {
        String dataRequest = "ReferenceDataRequest";
        String element = "securities";
        String company = "IBM";
        String region = "US";
        String area = "Index";
        List<String> fields = Collections.singletonList("PX_LAST");

        FHRITPRequest request = new FHRITPRequest();
        try {
            request.sendRequest(dataRequest, company, region, area, element, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
