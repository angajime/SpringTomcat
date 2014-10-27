package hello;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByeController {
    int counter = 0;

    @RequestMapping("/bye")
    public String index() {
        return "Greetings from Spring Boot! ADIOS MARCOS:" + counter++;
    }


}
