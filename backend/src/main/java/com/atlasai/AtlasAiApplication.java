import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AtlasAiApplication {

    /*
     * Build cache function
     */

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/chat")
    String chat() {
        return "Hello World";
    }

    public static void main(String[] args) {
        SpringApplication.run(AtlasAiApplication.class, args);
    }

}