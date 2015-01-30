package hello;

import curl.Curl;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tarazaky on 28/10/14.
 */
public class Client {
    private final String bicycleURN;
    private final String sensorURN;
    private final String powerResourceURN;
    private final String stationResourceURN;
    private Map params;

    public Client(String i, Map p){
        bicycleURN = i;
        sensorURN = bicycleURN + "sensorPower";
        powerResourceURN = sensorURN + ".power";
        stationResourceURN = sensorURN + ".station";
        String message = null;
        try {
            message = Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "deviceGateway", "deviceGatewayURN=" + bicycleURN, "operatorIdentifier=opIDCurlTest", "deviceGatewaySpecificationURN=deviceGWSpecCurlTest", "deviceGatewayGroupURN=deviceGWGCurlTest", "deviceStatus=active", "connectivityServiceURN=connectivityServiceCurlTest", "password=Password1", "serialNumber=1234", "vendorNumber=9876", "name=Bicycle:" + bicycleURN));
            message += "\n" + Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "sensor", "sensorURN=" + sensorURN, "operatorIdentifier=opIDCurlTest", "sensorSpecificationURN=sensorSpecCurlTest", "parentGatewayURN=" + bicycleURN, "name=Sensor:" + sensorURN, "deviceStatus=active", "serialNumber=1234"));
            message += "\n" + Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "resource", "resourceURN=" + powerResourceURN, "operatorIdentifier=opIDCurlTest", "resourceSpecificationURN=resourceSpecCurlTest", "sensorURN=" + sensorURN, "physicalAddress=888", "resourceSetURN=resourceSetCurlTest"));
            message += "\n" + Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "resource", "resourceURN=" + stationResourceURN, "operatorIdentifier=opIDCurlTest", "resourceSpecificationURN=resourceSpecCurlTest", "sensorURN=" + sensorURN, "physicalAddress=888", "resourceSetURN=resourceSetCurlTest"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        params = p;
    }

    public String getBicycleURN() {
        return bicycleURN;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Client{" +
                "bicycleURN='" + bicycleURN + '\'' +
                ", params=" + params +
                '}';
    }

    // TODO
    // Rename Client to "payloadEntry"?
    // Needed: gateway URN, gateway Spec, sensor, resource type/value...
    // Maybe add them in params?
}
