package hack.idiotproof.fhritp;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * StudentHack
 * Created by dragosmc on 11/1/2014.
 */
public class History {

    private Map<String, Map<String, List<String>>> historyMap;

    public History() {
        historyMap = new TreeMap<>();
    }

    public void add(String company, String fieldName, String value) {
        Map<String, List<String>> stringListMap = historyMap.get(company);
        if (stringListMap == null) {
            Map<String, List<String>> map = new TreeMap<>();
            List<String> list = new LinkedList<>();

            list.add(value);
            map.put(fieldName, list);

            historyMap.put(company, map);
        } else if (stringListMap.get(fieldName) == null) {
            List<String> list = new LinkedList<>();
            list.add(value);

            historyMap.get(company).put(fieldName, list);
        } else {
            stringListMap.get(fieldName).add(value);
        }
    }

    public List<String> getHistory(String company, String fieldName) {
        try {
            return historyMap.get(company).get(fieldName);
        } catch (Exception e) {
            return null;
        }
    }

    public String getLastValue(String company, String fieldName) {
        try {
            List<String> list = getHistory(company, fieldName);
            return list.get(list.size() - 1);
        } catch (Exception e) {
            return null;
        }

    }
}
