package model;

import hello.Client;

import java.util.Map;
import java.util.Set;

/**
 * Created by Juanlu on 20/02/2015.
 */
public class Device {

    private Map<Long, Client> valuesMap;

    public Device(String name, boolean mustProvision) {

    }

    public Client getDataForTime(long timestamp) {
        return valuesMap.get(timestamp);
    }

    public Set<Long> getAllTimes() {
        return valuesMap.keySet();
    }

    //TODO Complete this
}
