package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

}
