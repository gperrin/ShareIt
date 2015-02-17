package fr.lyon.insa.ot.sims.shareIt.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@RequestMapping("/greeting")
    public @ResponseBody String greeting() {
        return "greeting";
    }
}
