package hello;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {

        return "Bienvenido a Talericsson.io<br>" +
                "Para empezar a enviar datos acceda a la siguiente dirección:<br>" +
                "<a href='http://localhost:8080/send/as/beasty-beast'>" +
                "http://localhost:8080/send/as/beasty-beast" +
                "</a>";
    }

    @RequestMapping("/send/as/{id}")
    public String del(@PathVariable("id") String id) {
        return "Hola " +
                id +"! :) Estamos recibiendo tus datos!!";
    }

}
