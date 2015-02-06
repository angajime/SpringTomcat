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
            "<m2m:response\n" +
            "    xmlns:m2m=\"urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.NB\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "    <m2m:resourceset resourcesetid=\"resourceSetCurlTest\">\n" +
            "        <m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourcePower\" sensorid=\"sensorCurlTest\">\n" +
            "            <m2m:payload>\n" +
            "                <m2m:value type=\"double\">50.0</m2m:value>\n" +
            "                <m2m:timestamp>2014-11-04T09:14:03Z</m2m:timestamp>\n" +
            "            </m2m:payload>\n" +
            "            <m2m:metadata>\n" +
            "                <m2m:enduser>userCurlTest</m2m:enduser>\n" +
            "                <m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec>\n" +
            "                <m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec>\n" +
            "                <m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec>\n" +
            "            </m2m:metadata>\n" +
            "        </m2m:resource>\n" +
            "        <m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourcePower\" sensorid=\"sensorCurlTest\">\n" +
            "            <m2m:payload>\n" +
            "                <m2m:value type=\"double\">36.0</m2m:value>\n" +
            "                <m2m:timestamp>2014-11-04T10:01:18.136Z</m2m:timestamp>\n" +
            "            </m2m:payload>\n" +
            "            <m2m:metadata>\n" +
            "                <m2m:enduser>userCurlTest</m2m:enduser>\n" +
            "                <m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec>\n" +
            "                <m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec>\n" +
            "                <m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec>\n" +
            "            </m2m:metadata>\n" +
            "        </m2m:resource>\n" +
            "        <m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourceStation\" sensorid=\"sensorCurlTest\">\n" +
            "            <m2m:payload>\n" +
            "                <m2m:value type=\"double\">1.0</m2m:value>\n" +
            "                <m2m:timestamp>2014-11-04T10:01:18.136Z</m2m:timestamp>\n" +
            "            </m2m:payload>\n" +
            "            <m2m:metadata>\n" +
            "                <m2m:enduser>userCurlTest</m2m:enduser>\n" +
            "                <m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec>\n" +
            "                <m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec>\n" +
            "                <m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec>\n" +
            "            </m2m:metadata>\n" +
            "        </m2m:resource>\n" +
            "        <m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourceStation\" sensorid=\"sensorCurlTest\">\n" +
            "            <m2m:payload>\n" +
            "                <m2m:value type=\"double\">1.0</m2m:value>\n" +
            "                <m2m:timestamp>2014-11-04T09:14:03Z</m2m:timestamp>\n" +
            "            </m2m:payload>\n" +
            "            <m2m:metadata>\n" +
            "                <m2m:enduser>userCurlTest</m2m:enduser>\n" +
            "                <m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec>\n" +
            "                <m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec>\n" +
            "                <m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec>\n" +
            "            </m2m:metadata>\n" +
            "        </m2m:resource>\n" +
            "        <m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"bicycle2\" resourceid=\"resourceSetCurlTest.resourceStation\" sensorid=\"sensorCurlTest\">\n" +
            "            <m2m:payload>\n" +
            "                <m2m:value type=\"double\">1.0</m2m:value>\n" +
            "                <m2m:timestamp>2014-11-04T09:14:03Z</m2m:timestamp>\n" +
            "            </m2m:payload>\n" +
            "            <m2m:metadata>\n" +
            "                <m2m:enduser>userCurlTest</m2m:enduser>\n" +
            "                <m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec>\n" +
            "                <m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec>\n" +
            "                <m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec>\n" +
            "            </m2m:metadata>\n" +
            "        </m2m:resource>\n" +
            "    </m2m:resourceset>\n" +
            "</m2m:response>";

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
