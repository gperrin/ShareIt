package fr.lyon.insa.ot.sims.shareIt.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;

@Controller
public class MainController {
	
	@Autowired
	SharerRepository sharerRepository;
	
	@RequestMapping("/greeting")
    public @ResponseBody String greeting() {
		Sharer user = new Sharer();
		user.setAge(15);
		user.setFirstname("toto");
		user.setLastname("michale");
		user.setRating(2);
		this.sharerRepository.save(user);
		System.out.println("/greeting");
        return "Hello World";
    }
	@RequestMapping
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public @ResponseBody HashMap<String, Object> test(){
		HashMap<String, Object> result = new HashMap<>();
		result.put("gros fail", true);
		
		return result;
	}
	@RequestMapping(value = "/picture" , method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT) void postPicture (HttpEntity<byte[]> requestEntity){
		/*byte[] payload = requestEntity.getBody();
		HttpHeaders headers = requestEntity.getHeaders();
		System.out.println(headers.getContentType().toString());
	    InputStream logo = new ByteArrayInputStream(payload);
	    System.out.println(payload.length);
	    Sharer user = new Sharer();
	    user.setAge(22);
	    user.setFirstname("jean paul");
	    user.setLastname("dupond");
	    user.setProfilePicture(payload);
	    user.setProFilePictureType(headers.getContentType());
	    user.setRating(2);
	    this.sharerRepository.save(user);*/
	}
	
	
}
