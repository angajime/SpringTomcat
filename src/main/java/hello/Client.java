package hello;

import java.util.List;
import java.util.Map;

/**
 * Created by tarazaky on 28/10/14.
 */
public class Client {
    private final String id;
    private Map params;

    public Client(String i, Map p){
        id = i;
        params = p;
    }

    public String getId() {
        return id;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }
}
