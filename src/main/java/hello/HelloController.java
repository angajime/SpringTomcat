package hello;

import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class HelloController {

    List<Client> listaClientes = new LinkedList<Client>();

    @RequestMapping("/")
    public String index() {

        String id = (new RandomNameGenerator(new Random().nextInt())).next();

        return "Bienvenido a Talericsson.io<br>" +
                "Para empezar a enviar datos acceda a la siguiente dirección:<br>" +
                "<a href='http://localhost:8080/send/as/"+id+"'>" +
                "http://localhost:8080/send/as/"+id +
                "</a>";
    }

    @RequestMapping("/new")
    public String newID(){
        return (new RandomNameGenerator(new Random().nextInt())).next();
    }

    @RequestMapping("/follow/{id}")
    public String follow(@PathVariable("id") String id) {
        //TODO: Leer del DM
        System.out.println("Procesado");
        return "Estas siguiendo al pavo: "+id;
    }

    /**
     * Pide un dato al DM del cliente elegido.
     * @param urn: Identificador único del cliente.
     * @return Devuelve el dato.
     */
    @RequestMapping("/get/{urn}")
    @ResponseBody
    public List<Client> get(@PathVariable("urn") String urn) {
        //TODO: Leer datos de dicho cliente del DM
        List<Client> rtn = new LinkedList<Client>();

        for(Client c : listaClientes)
            if(c.getId().equals(urn))
                rtn.add(c);

        return rtn;
    }

    /**
     * Envia y registra un payload en el DM.
     * @param urn: Identificador único del cliente.
     * @param params: Diccionario de parámetros enviados.
     * @return Devuelve un ECHO.
     */
    @RequestMapping("/post/{urn}")
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

    @RequestMapping("/freeboard")
    public String fb(){
        return "<script>window.location.assign(\"/index.html\")</script>";
    }

}
