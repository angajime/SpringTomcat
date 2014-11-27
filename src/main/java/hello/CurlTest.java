package hello;

import curl.Curl;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Created by Juanlu on 30/10/14.
 */

@RestController
public class CurlTest {

//    @RequestMapping("/curl/prov/post")
//    public String post() {
//      Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType", "Preconfigured", "Password1", "deviceGatewayGroupTypeURN=deviceGWGTypeJuanlu3", "name=deviceGWGTypeJuanlu3", "operatorIdentifier=opIDBIC", "description=GWGType_de_prueba");
//      return curl.post(DMChannel.Provisioning);
//    }
//
//    @RequestMapping("/curl/prov/get")
//    public String get() {
//        Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType", "Preconfigured", "Password1", "deviceGatewayGroupTypeURN=deviceGWGTypeJuanlu2");
//        return curl.get(DMChannel.Provisioning);
//    }
//
//    @RequestMapping("/curl/prov/put")
//    public String put() {
//        Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType/deviceGWGTypeJuanlu3", "Preconfigured", "Password1", "name=deviceGWGTypeJuanlu3", "operatorIdentifier=opIDBIC", "description=GWGType_de_prueba_updated");
//        return curl.put(DMChannel.Provisioning);
//    }
//
//    @RequestMapping("/curl/prov/delete")
//    public String delete() {
//        Curl curl = new Curl("m2m/provisioning/LL/deviceGatewayGroupType/deviceGWGTypeJuanlu", "Preconfigured", "Password1");
//        return curl.delete(DMChannel.Provisioning);
//    }

//    @RequestMapping("/curl/south/send")
//    public String southSend() {
//        return Curl.send("domainApplicationCurlTest", "Password1", payloadCreator() );
//    }

    @RequestMapping("/curl/north/receive")
    public String northReceive() throws IOException {
        HttpResponse response = Curl.receive("domainApplicationCurlTest", "Password1");
        return Curl.getContentFromHttpResponse(response);

    }

    @RequestMapping("/curl/north/filter")
    public String northFilter() throws IOException, XMLStreamException {
        //String payload = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:response xmlns:m2m=\"urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.NB\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> <m2m:resourceset resourcesetid=\"resourceSetCurlTest\"><m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourceCurlTest\" sensorid=\"sensorCurlTest\"> <m2m:payload><m2m:value type=\"double\">28819.0</m2m:value><m2m:timestamp>2014-11-04T09:14:03Z</m2m:timestamp> </m2m:payload> <m2m:metadata><m2m:enduser>userCurlTest</m2m:enduser><m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec><m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec><m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec> </m2m:metadata></m2m:resource><m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourceCurlTest\" sensorid=\"sensorCurlTest\"> <m2m:payload><m2m:value type=\"double\">36.0</m2m:value><m2m:timestamp>2014-11-04T10:01:18.136Z</m2m:timestamp> </m2m:payload> <m2m:metadata><m2m:enduser>userCurlTest</m2m:enduser><m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec><m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec><m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec> </m2m:metadata></m2m:resource><m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourceCurlTest\" sensorid=\"sensorCurlTest\"> <m2m:payload><m2m:value type=\"double\">-4.0</m2m:value><m2m:timestamp>2014-11-04T10:01:18.136Z</m2m:timestamp> </m2m:payload> <m2m:metadata><m2m:enduser>userCurlTest</m2m:enduser><m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec><m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec><m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec> </m2m:metadata></m2m:resource><m2m:resource applicationtype=\"domainApplicationCurlTest\" gatewayid=\"deviceCurlTestStation\" resourceid=\"resourceSetCurlTest.resourceCurlTest\" sensorid=\"sensorCurlTest\"> <m2m:payload><m2m:value type=\"double\">16140.0</m2m:value><m2m:timestamp>2014-11-04T10:42:05Z</m2m:timestamp> </m2m:payload> <m2m:metadata><m2m:enduser>userCurlTest</m2m:enduser><m2m:resourcespec>resourceSpecCurlTest</m2m:resourcespec><m2m:sensorspec>sensorSpecCurlTest</m2m:sensorspec><m2m:gatewayspec>deviceGWSpecCurlTest</m2m:gatewayspec> </m2m:metadata></m2m:resource> </m2m:resourceset></m2m:response>";
        HttpResponse response = Curl.receive("domainApplicationCurlTest", "Password1");
        return Curl.filterXMLContentAsString(response, "deviceCurlTestStation");
    }

//    @RequestMapping("/curl/prov/auto")
//    public String provAuto() {
//        return Curl.add("Preconfigured", "Password1", "deviceGatewayCurlTest2", "sensorCurlTest2", "resourceCurlTest2");
//    }

    private String payloadCreator() {
        String domainApplication = "domainApplicationCurlTest";
        String user = "userCurlTest";
        String deviceStation = "devicedeviceGatewayCurlTest2"; // "deviceCurlTestStation"; //devicedeviceGatewayCurlTest2
        String sensor = "sensorsensorCurlTest2"; // "sensorCurlTest"; //sensorCurlTest2
        String resourceSet = "resourceSetCurlTest";
        String resource = "resourceresourceCurlTest2"; // "resourceCurlTest"; //resourceresourceCurlTest2
        String date = String.valueOf(System.currentTimeMillis());
        String type = "double";

        double lat = 36.0;
        double lon = -4.0;

        return "<?xml version='1.0' encoding='UTF-8'?> <m2m:request xmlns:m2m=\'urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.SB\' xmlns:xsi=\'https://www.w3.org/2001/XMLSchema-instance\' applicationType=\'"
                + domainApplication +
                "\'> <m2m:payloadEntry userId=\'"
                + user +
                "\' gatewayId=\'"
                + deviceStation +
                "\' sensorId=\'"
                + sensor +
                "\' resourceId=\'"
                /* + resourceSet + "."*/ + resource +
                "\'> <m2m:timestamp>"
                + date +
                "</m2m:timestamp> <m2m:value type=\'"
                + type +
                "\'>"
                + lat +
                "</m2m:value> </m2m:payloadEntry> " +
                "<m2m:payloadEntry userId=\'"
                + user +
                "\' gatewayId=\'"
                + deviceStation +
                "\' sensorId=\'"
                + sensor +
                "\' resourceId=\'"
                /* + resourceSet + "."*/ + resource +
                "\'> <m2m:timestamp>"
                + date +
                "</m2m:timestamp> <m2m:value type=\'"
                + type +
                "\'>"
                + lon +
                "</m2m:value> </m2m:payloadEntry>" +
                " </m2m:request>";
    }

}
