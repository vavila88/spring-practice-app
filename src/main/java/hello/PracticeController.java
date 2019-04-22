package hello;

import org.springframework.web.bind.annotation.*;

@RestController // annotate this class indicating that every method returns a domain object
                // rather than a view. Also indicates to Spring that this class should be
                //  considered when handling web requests
public class PracticeController {

    // static extends the scope of this variable to all
    private static final String template = "Hello from the '/' landing page!";

    @RequestMapping(path="/", method= RequestMethod.POST)// maps a request uri to this function
    public Practice greeting() {
        return new Practice(template);
    }

    @PutMapping(path="/onboarding")
    public OnboardForm onboardform(@RequestBody OnboardForm form) {

        return form;
    }


}
