package hello;

import curl.Curl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/curl/south/send")
    public String southSend() {
        return Curl.send("domainApplicationCurlTest", "Password1", payloadCreator() );
    }

    @RequestMapping("/curl/north/receive")
    public String northReceive() {
        return Curl.receive("domainApplicationCurlTest", "Password1", "prueba");
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
