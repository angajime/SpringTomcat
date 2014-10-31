package hello;

import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class HelloController {

    List<Client> listaClientes = new LinkedList<Client>();
    SimpleDriverDataSource dataSource;

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
        return (new RandomNameGenerator(new Random().nextInt())).next();
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
            if (c.getId().equals(urn))
                rtn.add(c);

        return rtn;
    }

    @RequestMapping(value = {"/follow/{urn}", "/latest/for/{urn}"})
    @ResponseBody
    public Client getLast(@PathVariable("urn") String urn) {
        Client rtn = null;

        for (Client c : listaClientes)
            if (c.getId().equals(urn))
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

        //existe el cliente?

        //si:
        //  introducir sus datos

        //no:
        //  registrar sus datos

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

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("drop table alerts if exists");
        jdbcTemplate.execute("create table alerts(" +
                "id serial, " +
                "urn varchar(255), " +
                "resource varchar(255)," +
                "comparator varchar(255)," +
                "value number," +
                "recipient varchar(255))");
/*
        String[] names = "John Woo;Jeff Dean;Josh Bloch;Josh Long".split(";");
        for (String fullname : names) {
            String[] name = fullname.split(" ");
            System.out.printf("Inserting customer record for %s %s\n", name[0], name[1]);
            jdbcTemplate.update(
                    "INSERT INTO alerts(first_name,last_name) values(?,?)",
                    name[0], name[1]);
        }

        System.out.println("Querying for customer records where first_name = 'Josh':");
        List<Alert> results = jdbcTemplate.query(
                "select * from alerts where first_name = ?", new Object[] { "Josh" },
                new RowMapper<Alert>() {
                    @Override
                    public Alert mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Alert(0,null,null,null,null,null);
                    }
                });

        for (Alert customer : results) {
            System.out.println(customer);
        }
*/
        return "OK";
    }

    @RequestMapping("/freeboard")
    public String fb() {
        return "<script>window.location.assign(\"/index.html\")</script>";
    }

    @RequestMapping("/send")
    public String sendJS() {
        return "<script>window.location.assign(\"/send/index.html\")</script>";
    }

}
