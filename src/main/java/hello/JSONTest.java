package hello;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Juanlu on 30/10/14.
 */

@RestController
public class JSONTest {

    public static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<m2m:request xmlns:m2m=\"urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.SB\" xmlns:xsi=\"https://www.w3.org/2001/XMLSchema-instance\" applicationType=\"domainApplicationUMA\">\n" +
            "\t<m2m:payloadEntry userId=\"userUMA\" gatewayId=\"devicewaifaiStation\" sensorId=\"sensorCounter\" resourceId=\"resourceSetAntena.resourceCantidad\">\n" +
            "\t\t<m2m:timestamp>1412063953000</m2m:timestamp>\n" +
            "\t\t<m2m:value type=\"double\">32.5</m2m:value>\n" +
            "\t</m2m:payloadEntry>\n" +
            "\t<m2m:payloadEntry userId=\"userUMA\" gatewayId=\"devicewaifaiStation\" sensorId=\"sensorCounter\" resourceId=\"resourceSetAntena.resourceCantidad\">\n" +
            "\t\t<m2m:timestamp>1412063953500</m2m:timestamp>\n" +
            "\t\t<m2m:value type=\"double\">42.5</m2m:value>\n" +
            "\t</m2m:payloadEntry>\n" +
            "</m2m:request>";

    @RequestMapping("/json/fromxml")
    public String jsonFromXML() {
        return XML.toJSONObject(xml).toString();
    }

    @RequestMapping("json/toxml")
    public String jsonToXML() {
        String jsonString = "{\"m2m:request\":{\"xmlns:m2m\":\"urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.SB\",\"applicationType\":\"domainApplicationUMA\",\"xmlns:xsi\":\"https://www.w3.org/2001/XMLSchema-instance\",\"m2m:payloadEntry\":[{\"resourceId\":\"resourceSetAntena.resourceCantidad\",\"m2m:value\":{\"type\":\"double\",\"content\":32.5},\"m2m:timestamp\":1412063953000,\"userId\":\"userUMA\",\"gatewayId\":\"devicewaifaiStation\",\"sensorId\":\"sensorCounter\"},{\"resourceId\":\"resourceSetAntena.resourceCantidad\",\"m2m:value\":{\"type\":\"double\",\"content\":42.5},\"m2m:timestamp\":1412063953500,\"userId\":\"userUMA\",\"gatewayId\":\"devicewaifaiStation\",\"sensorId\":\"sensorCounter\"}]}}";
        JSONObject jsonObject = new JSONObject(jsonString);
        return XML.toString(jsonObject);
    }

    @RequestMapping("xml/to/client")
    public String xmlToClient() {
        String returnStr = "";
        Client.getClientsFromXML(xml);
        return returnStr;
    }

}
