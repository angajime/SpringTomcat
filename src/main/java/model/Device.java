package model;

import curl.Curl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Juanlu on 20/02/2015.
 */
public class Device {

    /**
     * In this map, the Long is a timestamp.
     */
    private Map<Long, Values> valuesMap;
    private String name;

    public Device(String name, boolean mustProvision) {
        this.name = name;
        this.valuesMap = new HashMap<Long, Values>();
        if (mustProvision) {
            String message = null;
            try {
                message = Curl.getContentFromHttpResponse(Curl.add("Preconfigured", "Password1", "deviceGateway", "deviceGatewayURN=" + name, "operatorIdentifier=opIDBici", "deviceGatewaySpecificationURN=deviceGWSpecBici", "deviceGatewayGroupURN=deviceGWGBici", "deviceStatus=active", "connectivityServiceURN=connectivityServiceCurlTest", "password=Password1", "serialNumber=1234", "vendorNumber=9876", "name=Bike:" + name));
            } catch (IOException e) {
                message = e.getMessage();
            } finally {
                System.out.println(message);
            }
        }
    }

    public Values getDataForTime(long timestamp) {
        return valuesMap.get(timestamp);
    }

    public Set<Long> getAllTimes() {
        return valuesMap.keySet();
    }

    //TODO Complete this
}
