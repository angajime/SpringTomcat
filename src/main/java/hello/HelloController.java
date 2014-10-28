package hello;

import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class HelloController {

    private int counter = 0;

    @RequestMapping("/")
    public String index() {

        String id = (new RandomNameGenerator(new Random().nextInt())).next();

        return "Bienvenido a Talericsson.io<br>" +
                "Para empezar a enviar datos acceda a la siguiente dirección:<br>" +
                "<a href='http://localhost:8080/send/as/"+id+"'>" +
                "http://localhost:8080/send/as/"+id +
                "</a>";
    }

    @RequestMapping("/send/as/{id}")
    public String del(@PathVariable("id") String id ,@RequestBody String data) {
        System.out.println("Procesado");
        return "Hola " +
                id +"! :) Acabamos de recibir esto de ti: <br>" +
                data;
    }

    @RequestMapping("/get/{urn}")
    @ResponseBody
    public Client get(@PathVariable("urn") String urn) {
        //contectarme a la url dada
        return new Client(urn, counter++);
    }

    @RequestMapping("/freeboard")
    public String fb(){
        return "<script>window.location.assign(\"/index.html\")</script>";
    }

}
