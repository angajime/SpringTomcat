package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Juanlu on 04/03/2015.
 */
public class Values {

    private Map<String, Object> values;

    public Values() {
        values = new HashMap<String, Object>();
    }

    public Set<String> getAllKeys() {
        return values.keySet();
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    //TODO Complete this method, as it doesn't really return the Values.
    public static Collection<Values> getValuesFromXML(String xml) {
        Collection<Values> values = new HashSet<Values>();
        JSONObject jsonObject = XML.toJSONObject(xml).getJSONObject("m2m:response");
        //System.out.println(jsonObject);

        JSONObject resourceSet = jsonObject.getJSONObject("m2m:resourceset");
        //System.out.println(resourceSet);

        JSONArray resourceEntries = resourceSet.getJSONArray("m2m:resource"); // resourceEntries has all the resources
        //System.out.println(resourceEntries.toString());
        Map<String,Map<Long,Map<String,String>>> map = new TreeMap(); //Map<Gateway, Map<Timestamp, Map<Resource, Value>
        for (int i = 0; i < resourceEntries.length(); i++) {
            JSONObject resourceEntry = resourceEntries.getJSONObject(i);
            System.out.println(resourceEntry.toString());
            String gateway = (String)resourceEntry.get("gatewayid");
            JSONObject payload = resourceEntry.getJSONObject("m2m:payload");
            Long timestamp = (long) 0;
            try {
                timestamp = parseTimestamp((String) payload.get("m2m:timestamp"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String resource = (String) resourceEntry.get("resourceid");
            String value = payload.getJSONObject("m2m:value").get("content").toString();

            if(map.containsKey(gateway)) {
                Map<Long,Map<String,String>> mg = map.get(gateway);
                if (mg.containsKey(timestamp)) {
                    mg.get(timestamp).put(resource, value);
                } else {
                    Map<String, String> m = new TreeMap();
                    m.put(resource, value);
                    mg.put(timestamp, m);
                }
            } else {
                Map<Long,Map<String,String>> mg = new TreeMap();
                Map<String, String> m = new TreeMap();
                m.put(resource,value);
                mg.put(timestamp,m);
                map.put(gateway,mg);
            }
        }
        System.out.println(map);
        return values;
    }

    private static long parseTimestamp(String timestamp) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        try {
            cal.setTime(sdf.parse(timestamp));
        } catch (ParseException e) {
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            cal.setTime(sdf.parse(timestamp));
        }

        return cal.getTimeInMillis();
    }

    public static String getCompleteMessage(String payloadEntries) {
        StringBuilder strb = new StringBuilder();
        strb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:request xmlns:m2m=\"urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.SB\" xmlns:xsi=\"https://www.w3.org/2001/XMLSchema-instance\" applicationType=\"" + Constants.domainApplication + "\">");
        strb.append(payloadEntries);
        strb.append("</m2m:request>");
        return strb.toString();
    }

}
