package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // annotate this class indicating that every method returns a domain object
                // rather than a view. Also indicates to Spring that this class should be
                //  considered when handling web requests
public class GreetingController {

    // static extends the scope of this variable to all
    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")// maps a request uri to this function
    // the params to this function are the params for the request. If there are none present, use the default value
    // specified (world)
    // NOTE: the RequestParam is a query param, not a form body. The annotation expects a param
    //          called "name" and assigns it's value to the Java String variable "name"
    public Greeting greeting(@RequestParam(value="name", defaultValue="world") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    /**
     * Returns a friendly message and a random quote
     * @param name
     * @return
     */
    @RequestMapping("/greeting/quote")
    public Greeting greetingWithQuote(@RequestParam(value="name", defaultValue="world") String name) {
        // RestTemplate object is used to map the contents of a JSON payload directly to a domain object.
        // In this case we mapp the random quote json to our object representation
        RestTemplate restTemplate = new RestTemplate();

        // Call the url provided and create the specified object type form the results
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        String ret = template + " Here is your quote for today: '%s'";
        return new Greeting(counter.incrementAndGet(),
                String.format(ret, name, quote.getValue().getQuote()));
    }
}
