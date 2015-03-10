package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Juanlu on 19/02/2015.
 */
public class DataModel {



    /**
     * In this map, the String is the Device URN
     */
    private Map<String, Device> deviceMap;

    private DataModel() {
        deviceMap = new HashMap<String, Device>();
    }

    // This set shouldn't be modified
    public Set<String> getAllNames() {
        return deviceMap.keySet();
    }

    public Device getDevice(String name) {
        Device device = deviceMap.get(name);
        if (device == null) {
            device = new Device(name, false);
            deviceMap.put(name, device);
        }
        device.updateValues();
        return device;
    }

    public Device getNewDevice(String name) {
        Device device = new Device(name, true);
        deviceMap.put(name, device);
        return device;
    }

    // Singleton methods

    private static class InstanceHolder {
        private static DataModel instance = new DataModel();
    }

    public static DataModel getInstance() {
        return InstanceHolder.instance;
    }

}
