package curl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Juanlu on 30/10/14.
 */

@RestController
public class CurlTest {

    @RequestMapping("/curl/prov/post")
    public String post() {
      Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType", "Preconfigured", "Password1", "deviceGatewayGroupTypeURN=deviceGWGTypeJuanlu3", "name=deviceGWGTypeJuanlu3", "operatorIdentifier=opIDBIC", "description=GWGType_de_prueba");
      return curl.post(DMChannel.Provisioning);
    }

    @RequestMapping("/curl/prov/get")
    public String get() {
        Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType", "Preconfigured", "Password1", "deviceGatewayGroupTypeURN=deviceGWGTypeJuanlu2");
        return curl.get(DMChannel.Provisioning);
    }

    @RequestMapping("/curl/prov/put")
    public String put() {
        Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType/deviceGWGTypeJuanlu3", "Preconfigured", "Password1", "name=deviceGWGTypeJuanlu3", "operatorIdentifier=opIDBIC", "description=GWGType_de_prueba_updated");
        return curl.put(DMChannel.Provisioning);
    }

    @RequestMapping("/curl/prov/delete")
    public String delete() {
        Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType/deviceGWGTypeJuanlu", "Preconfigured", "Password1");
        return curl.delete(DMChannel.Provisioning);
    }

}
