package hello;

import curl.Curl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tarazaky on 28/10/14.
 */
public class Client {
    private final String bicycleURN;
    private final String sensorURN;
    private final String powerResourceURN;
    private final String stationResourceURN;
    private final static String userId = "userUMA";
    private final static String domainApplication = "domainApplicationCurlTest";
    private Map params;

    public Client(String i, Map p){
        bicycleURN = i;
        sensorURN = bicycleURN + "sensorPower";
        powerResourceURN = sensorURN + ".power";
        stationResourceURN = sensorURN + ".station";
        params = p;
    }

    public Client(String i, Map p, boolean autoprov){
        bicycleURN = i;
        sensorURN = bicycleURN + "sensorPower";
        powerResourceURN = sensorURN + ".power";
        stationResourceURN = sensorURN + ".station";
        params = p;
        if(autoprov) provisioning();
    }

    public String getBicycleURN() {
        return bicycleURN;
    }

    public Map getParams() {
        return params;
    }

    public void provisioning()
    {
        String message = "nothing";
        try {
            message = Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "deviceGateway", "deviceGatewayURN=" + bicycleURN, "operatorIdentifier=opIDCurlTest", "deviceGatewaySpecificationURN=deviceGWSpecCurlTest", "deviceGatewayGroupURN=deviceGWGCurlTest", "deviceStatus=active", "connectivityServiceURN=connectivityServiceCurlTest", "password=Password1", "serialNumber=1234", "vendorNumber=9876", "name=Bicycle:" + bicycleURN));
            message += "\n" + Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "sensor", "sensorURN=" + sensorURN, "operatorIdentifier=opIDCurlTest", "sensorSpecificationURN=sensorSpecCurlTest", "parentGatewayURN=" + bicycleURN, "name=Sensor:" + sensorURN, "deviceStatus=active", "serialNumber=1234"));
            message += "\n" + Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "resource", "resourceURN=" + powerResourceURN, "operatorIdentifier=opIDCurlTest", "resourceSpecificationURN=resourceSpecCurlTest", "sensorURN=" + sensorURN, "physicalAddress=888", "resourceSetURN=resourceSetCurlTest"));
            message += "\n" + Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "resource", "resourceURN=" + stationResourceURN, "operatorIdentifier=opIDCurlTest", "resourceSpecificationURN=resourceSpecCurlTest", "sensorURN=" + sensorURN, "physicalAddress=888", "resourceSetURN=resourceSetCurlTest"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }

    public void setParams(Map params) {
        this.params = params;
    }

    @Override
    public String toString() {
//        return "Client{" +
//                "bicycleURN='" + bicycleURN + '\'' +
//                ", params=" + params +
//                '}';
        return toXMLPayloadEntry();
    }

    // TODO
    // Rename Client to "payloadEntry"?
    // Needed: gateway URN, gateway Spec, sensor, resource type/value...
    // Maybe add them in params?

    public String toXMLPayloadEntry() {
        StringBuilder toxml = new StringBuilder();
//        for (PayloadEntry ... ) {
//            toxml.append("<m2m:payloadEntry userId=\"");
//            toxml.append(user);
//            toxml.append("\" gatewayId=\"");
//            toxml.append(bicycleURN);
//            toxml.append("\" sensorId=");
//            toxml.append(sensorURN);
//            toxml.append("\" resourceId=\"");
//            toxml.append(resourceURN);
//            toxml.append("\"><m2m:timestamp>");
//            toxml.append(timestamp);
//            toxml.append("</m2m:timestamp><m2m:value type=\"");
//            toxml.append(valueType);
//            toxml.append("\">");
//            toxml.append(value);
//            toxml.append("</m2m:value></m2m:payloadEntry>");
//        }
        toxml.append("<m2m:payloadEntry userId=\"");
        toxml.append(userId);
        toxml.append("\" gatewayId=\"");
        toxml.append(bicycleURN);
        toxml.append("\" sensorId=\"");
        toxml.append(sensorURN);
        toxml.append("\" resourceId=\"");
        toxml.append(powerResourceURN);
        toxml.append("\"><m2m:timestamp>");
        toxml.append(params.get("timestamp"));
        toxml.append("</m2m:timestamp><m2m:value type=\"");
        toxml.append("double");
        toxml.append("\">");
        toxml.append(params.get("powerResource"));
        toxml.append("</m2m:value></m2m:payloadEntry>");
        toxml.append("<m2m:payloadEntry userId=\"");
        toxml.append(userId);
        toxml.append("\" gatewayId=\"");
        toxml.append(bicycleURN);
        toxml.append("\" sensorId=\"");
        toxml.append(sensorURN);
        toxml.append("\" resourceId=\"");
        toxml.append(stationResourceURN);
        toxml.append("\"><m2m:timestamp>");
        toxml.append(params.get("timestamp"));
        toxml.append("</m2m:timestamp><m2m:value type=\"");
        toxml.append("string"); //TODO Check value type
        toxml.append("\">");
        toxml.append(params.get("stationResource"));
        toxml.append("</m2m:value></m2m:payloadEntry>");
        return toxml.toString();
    }

    public static Collection<Client> getClientsFromXML(String xml) {
        Collection<Client> clients = new HashSet<Client>();
        JSONObject jsonObject = XML.toJSONObject(xml).getJSONObject("m2m:response");
        //System.out.println(jsonObject);

        JSONObject resourceSet = jsonObject.getJSONObject("m2m:resourceset");
        //System.out.println(resourceSet);

        JSONArray resourceEntries = resourceSet.getJSONArray("m2m:resource");
        //System.out.println(resourceEntries.toString());
        Map<String,Map<Long,Map<String,String>>> map = new TreeMap();
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
        return clients;
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
        strb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:request xmlns:m2m=\"urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.SB\" xmlns:xsi=\"https://www.w3.org/2001/XMLSchema-instance\" applicationType=\"" + domainApplication + "\">");
        strb.append(payloadEntries);
        strb.append("</m2m:request>");
        return strb.toString();
    }
}
