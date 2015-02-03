package hello;

import curl.Curl;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class HelloController {

    @Autowired
    AlertLookupService alertLookupService;

    Future<List<Alert>> rtn;

    List<Client> listaClientes = new LinkedList<Client>();
    SimpleDriverDataSource dataSource;
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    public String index() {

        String id = (new RandomNameGenerator(new Random().nextInt())).next();

        return "Bienvenido a Talericsson.io<br>" +
                "Para empezar a enviar datos acceda a la siguiente dirección:<br>" +
                "<a href='http://localhost:8080/send/as/" + id + "'>" +
                "http://localhost:8080/send/as/" + id +
                "</a>";
    }

    @RequestMapping("/new")
    public String newID() {
        String urn = (new RandomNameGenerator(new Random().nextInt())).next();
        /*System.out.println(
                Curl.add("Preconfigured",
                        "Password1",
                        "deviceGateway",
                        "operatorIdentifier=opIDCurlTest",
                        "deviceGatewaySpecificationURN=deviceGWSpecCurlTest",
                        "deviceGatewayGroupURN=deviceGWGCurlTest",
                        "deviceStatus=active",
                        "password=Password1",
                        "name=" + urn,
                        "deviceGatewayURN=" + urn).toString());*/
        return urn;
    }

    /**
     * Pide un dato al DM del cliente elegido.
     *
     * @param urn: Identificador único del cliente.
     * @return Devuelve el dato.
     */
    @RequestMapping(value = {"/get/{urn}", "/every/{urn}"})
    @ResponseBody
    public List<Client> get(@PathVariable("urn") String urn) {
        //TODO: Leer datos de dicho cliente del DM
        List<Client> rtn = new LinkedList<Client>();

        for (Client c : listaClientes)
            if (c.getBicycleURN().equals(urn))
                rtn.add(c);

        return rtn;
    }

    @RequestMapping(value = {"/follow/{urn}", "/latest/for/{urn}"})
    @ResponseBody
    public Client getLast(@PathVariable("urn") String urn) {
        Client rtn = null;

        for (Client c : listaClientes)
            if (c.getBicycleURN().equals(urn))
                rtn = c;

        return rtn;
    }

    /**
     * Envia y registra un payload en el DM.
     *
     * @param urn:    Identificador único del cliente.
     * @param params: Diccionario de parámetros enviados.
     * @return Devuelve un ECHO.
     */
    @RequestMapping(value = "/post/{urn}", method = RequestMethod.POST)
    @ResponseBody
    public Client post(@PathVariable("urn") String urn, @RequestParam Map params) {
        Client client = new Client(urn, params);
        listaClientes.add(client);
        System.out.println(client.toString());
        System.out.println(createPayload(urn, params));
/*
        //Se puede mandar el payload? Sino, crearlo y volver a intentar:
        String payload = createPayload(urn, params);
        if(Curl.send("domainApplicationCurlTest",
                    "Password1",
                    payload).getStatusLine().getStatusCode() == 403) {
            System.out.println("Recibido 403: Realizamos subscripcion...");
            subscribePayload(urn,params);
            System.out.println("DATO_REENVIADO:"+
            Curl.send("domainApplicationCurlTest",
                    "Password1",
                    payload).toString());
        }
*/
        //Alert update:
        rtn = alertLookupService.findAlert(jdbcTemplate, urn, params);

        return client;
    }

    @RequestMapping("/alert/{recipients}/when/{urn}/{condition}")
    public String alert(@PathVariable("recipients") List<String> recipients,
                        @PathVariable("urn") String urn,
                        @PathVariable("condition") String condition) {

        System.out.println(condition);
        System.out.println(condition.split("-")[0]);

        String resource = condition.split("-")[0];
        String comparator = condition.split("-")[1];
        String value = condition.split("-")[2];

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(
                "INSERT INTO alerts(urn, resource, comparator, value, recipient) values(?,?,?,?,?)",
                urn, resource, comparator, value, recipients.get(0));

        return "OK";
    }

    @RequestMapping("/testdb")
    public String testdb() {
        dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:mem");
        dataSource.setPassword("");

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("drop table alerts if exists");
        jdbcTemplate.execute("create table alerts(" +
                "id serial, " +
                "urn varchar(255), " +
                "resource varchar(255)," +
                "comparator varchar(255)," +
                "value number," +
                "recipient varchar(255)," +
                "active boolean)");

        return "OK";
    }

    @RequestMapping("/listdb")
    public List<Alert> listdb() {

        List<Alert> results = jdbcTemplate.query(
                "select * from alerts",
                new RowMapper<Alert>() {
                    List<String> ls = new LinkedList<String>();

                    @Override
                    public Alert mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ls.add(rs.getString("recipient"));
                        return new Alert(rs.getLong("id"),
                                rs.getString("urn"),
                                rs.getString("resource"),
                                rs.getString("comparator"),
                                rs.getDouble("value"),
                                ls,
                                rs.getBoolean("active")
                        );
                    }
                }
        );

        return results;
    }

    @RequestMapping("/freeboard/{urn}")
    public String fb(@PathVariable(value = "urn") String urn) {
        return "<script>window.location.assign(\"/index.html?urn=" + urn + "\")</script>";
    }

    @RequestMapping("/dash/{urn}")
    public Dashboard dash(@PathVariable(value = "urn") String urn) {
        List<Pane> panes = new LinkedList<Pane>();
        List<Datasource> datasources = new LinkedList<Datasource>();
        List<Widget> widgets = new LinkedList<Widget>();
        List<Widget> widgets1 = new LinkedList<Widget>();

        Setting settings = new Setting("Mouse X", "regular",
                "datasources['" + urn + "']['params']['mouse[x]']",
                true);

        Setting settings2 = new Setting("Mouse Y", "regular",
                "datasources['" + urn + "']['params']['mouse[y]']",
                true);

        Setting settings3 = new Setting(
                "datasources['" + urn + "']['params']['location[latitude]']",
                "datasources['" + urn + "']['params']['location[longitude]']");

        widgets.add(new Widget("text_widget", settings));
        widgets.add(new Widget("text_widget", settings2));

        widgets1.add(new Widget("google_map", settings3));

        panes.add(new Pane(1, 1, 3, 1, widgets1));
        panes.add(new Pane(1, 1, 1, 1, widgets));

        datasources.add(new Datasource(
                urn,
                "JSON",
                new Settings(
                        "follow/" + urn,
                        false,
                        2,
                        "GET")));

        return new Dashboard(1, 1, true, panes, datasources);
    }

    @RequestMapping("/freeboard")
    public String fb() {
        return "<script>window.location.assign(\"/index.html\")</script>";
    }

    @RequestMapping("/send")
    public String sendJS() {
        return "<script>window.location.assign(\"/send/index.html\")</script>";
    }

    @Deprecated
    private String createPayload(String urn, Map params) {
        String plHeader = "<?xml version=\"1.0\" " +
                "encoding=\"UTF-8\"?>" +
                "<m2m:request " +
                "xmlns:m2m=\"urn:com:ericsson:schema:xml:m2m:protocols:vnd.ericsson.m2m.SB\" " +
                "xmlns:xsi=\"https://www.w3.org/2001/XMLSchema-instance\" " +
                "applicationType=\"domainApplicationCurlTest\">";

        String plEntry = "";
        for(Map.Entry<String, String> entry : ((Map<String,String>)params).entrySet())
            plEntry += createEntry(urn, entry);

        String plFooter = "</m2m:request>";
        return plHeader + plEntry + plFooter;
    }

    @Deprecated
    private String createEntry(String urn, Map.Entry<String, String> entry) {
        String key = entry.getKey().replaceAll("\\[", "_").replaceAll("\\]","_");
        String sensor = key+"-Sensor-"+urn;
        String set = key+"-Set-"+urn;
        String rec = key+"-Rec-"+urn;
        Long ms = System.currentTimeMillis();
        String value = entry.getValue();

        return "<m2m:payloadEntry " +
                "userId=\"userCurlTest\" " +
                "gatewayId=\""+urn+"\" " +
                "sensorId=\""+sensor+"\" " +
                "resourceId=\""+set+".resource"+rec+"\">" +
                "<m2m:timestamp>"+ms+"</m2m:timestamp>" +
                "<m2m:value type=\"double\">"+value+"</m2m:value>" +
                "</m2m:payloadEntry>";
    }

    @Deprecated
    private void subscribePayload(String urn, Map params) {
        for(Map.Entry<String, String> entry : ((Map<String,String>)params).entrySet()) {

            String key = entry.getKey().replaceAll("\\[", "_").replaceAll("\\]","_");
            String sensor = key+"-Sensor-"+urn;
            String set = key+"-Set-"+urn;
            String rec = key+"-Rec-"+urn;

            System.out.println(
                    Curl.add("Preconfigured",
                            "Password1",
                            "sensor",
                            "operatorIdentifier=opIDCurlTest",
                            "sensorSpecificationURN=sensorSpecCurlTest",
                            "parentGatewayURN="+urn,
                            "sensorURN="+sensor,
                            "name="+sensor,
                            "deviceStatus=active").toString());
            System.out.println(
                    Curl.add("Preconfigured",
                            "Password1",
                            "resource",
                            "operatorIdentifier=opIDCurlTest",
                            "resourceSpecificationURN=resourceSpecCurlTest",
                            "sensorURN="+sensor,
                            "resourceURN="+set+"."+rec,
                            "resourceSetURN="+set).toString());
            System.out.println(
                    Curl.add("Preconfigured",
                            "Password1",
                            "resourceSet",
                            "operatorIdentifier=opIDCurlTest",
                            "resourceSetURN="+set,
                            "resourceURNList="+set+"."+rec).toString());
            System.out.println(
                    Curl.add("Preconfigured",
                            "Password1",
                            "resourceSet/"+set,
                            "resourceURNList="+set+"."+rec)
            );
        }
    }

}
