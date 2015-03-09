package model;

import curl.Curl;

import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Juanlu on 20/02/2015.
 */
public class Device {

    /**
     * In this map, the Long is a timestamp.
     */
    private SortedMap<Long, Values> valuesMap;
    private String name;

    public Device(String name, boolean mustProvision) {
        this.name = name;
        this.valuesMap = new TreeMap<Long, Values>();
        if (mustProvision) {
            String message = null;
            String sensorURN = name + "sensorPower";
            String powerResourceURN = sensorURN + ".power";
            String stationResourceURN = sensorURN + ".station";
            try {
                message = Curl.getContentFromHttpResponse(Curl.add(Constants.user, Constants.pass, "deviceGateway", "deviceGatewayURN=" + name, "operatorIdentifier=" + Constants.operator, "deviceGatewaySpecificationURN=" + Constants.deviceGatewaySpec, "deviceGatewayGroupURN=" + Constants.deviceGatewayGroup, "deviceStatus=active", "connectivityServiceURN=" + Constants.connectivityService, "password=Password1", "serialNumber=1234", "vendorNumber=9876", "name=Bicycle:" + name));
                message += "\n" + Curl.getContentFromHttpResponse(Curl.add(Constants.user, Constants.pass, "sensor", "sensorURN=" + sensorURN, "operatorIdentifier=" + Constants.operator, "sensorSpecificationURN=" + Constants.sensorSpec, "parentGatewayURN=" + name, "name=Sensor:" + sensorURN, "deviceStatus=active", "serialNumber=1234"));
                message += "\n" + Curl.getContentFromHttpResponse(Curl.add(Constants.user, Constants.pass, "resource", "resourceURN=" + powerResourceURN, "operatorIdentifier" + Constants.operator, "resourceSpecificationURN=" + Constants.resourceSpec, "sensorURN=" + sensorURN, "physicalAddress=888", "resourceSetURN=resourceSet" + Constants.resourceSet));
                message += "\n" + Curl.getContentFromHttpResponse(Curl.add(Constants.user, Constants.pass, "resource", "resourceURN=" + stationResourceURN, "operatorIdentifier" + Constants.operator, "resourceSpecificationURN=" + Constants.resourceSpec, "sensorURN=" + sensorURN, "physicalAddress=888", "resourceSetURN=resourceSet" + Constants.resourceSet));
            } catch (IOException e) {
                message += e.getMessage();
            } finally {
                System.out.println(message);
            }
        }
    }

    public Values getDataForTime(long timestamp) {
        return valuesMap.get(timestamp);
    }

    public long getLastTime() {
        return valuesMap.lastKey();
    }

    public Set<Long> getAllTimes() {
        return valuesMap.keySet();
    }

    //TODO Complete this
}
