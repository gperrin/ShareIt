package fr.lyon.insa.ot.sims.shareIt.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Controller
public class MainController {
	
	@Autowired
	SharerRepository userRepository;
	
	@RequestMapping("/greeting")
    public @ResponseBody String greeting() {
		Sharer user = new Sharer();
		user.setAge(15);
		user.setFirstname("toto");
		user.setLastname("michale");
		user.setRating(2);
		this.userRepository.save(user);
        return "Hello World";
    }
}
