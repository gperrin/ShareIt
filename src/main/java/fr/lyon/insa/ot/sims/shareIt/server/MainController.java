package fr.lyon.insa.ot.sims.shareIt.server;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.services.ISharerService;

@Controller
public class MainController {
	
	@Autowired
	SharerRepository sharerRepository;
	
	@Autowired
	ISharerService sharerService;
	
	@RequestMapping("/greeting")
    public @ResponseBody String greeting() {
		/*Sharer user = new Sharer();
		user.setAge(15);
		user.setFirstname("toto");
		user.setLastname("michale");
		user.setRating(2);
		this.sharerRepository.save(user);
		System.out.println("/greeting");*/
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
	
	@RequestMapping(method = RequestMethod.POST, value="/user")
	@ResponseStatus (HttpStatus.CREATED) public @ResponseBody Sharer createUser(
			@RequestParam(required = true, value="firstname")String firstName,
			@RequestParam(required = true, value="lastname")String lastName,
			@RequestParam(required = true, value="postcode")int postCode,
			@RequestParam(required = false, value = "age") Integer age,
			@RequestParam(required = false , value = "sex") Character sex,
			@RequestParam(required = false , value = "phone") String telephone){
		//TODO : créer utilisateur
		Sharer sharer = this.sharerService.createUser(lastName, firstName, postCode);
		if ( sharer == null){
			return null;//TODO : err msg
		}
		if ( age != null ){
			sharer.setAge(age);
		}
		if ( sex != null ){
			sharer.setSex(sex);
		}
		if ( telephone != null ){
			sharer.setTelephone(telephone);
		}
		if (sex != null || telephone != null || age != null){
			this.sharerService.updateUser(sharer);
		}
		return sharer;
	}
	@RequestMapping(method = RequestMethod.GET, value="/user/{id:[\\d]+}")
	public @ResponseBody Sharer getUser(@PathVariable("id")int id){
		Sharer sharer = this.sharerService.getUser(id);
		return sharer; //TODO : rediriger sur erreur si nul
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/user/{id:[\\d]+}")
	public @ResponseStatus (HttpStatus.OK) void updateUser(@PathVariable("id") int id,
			@RequestParam(required= false, value = "firstname") String firstName,
			@RequestParam(required= false, value = "lastname") String lastName,
			@RequestParam(required= false, value = "phone") String telephone,
			@RequestParam(required= false, value = "age") Integer age,
			@RequestParam(required= false, value = "sex") Character sex,
			@RequestParam(required= false, value = "postcode") Integer postCode,
			HttpEntity<byte[]> picture){
		Sharer sharer = this.sharerService.getUser(id);
		if ( sharer != null ){
			if ( firstName != null ){
				sharer.setFirstname(firstName);
			}
			if ( lastName != null ){
				sharer.setLastname(lastName);
			}
			if ( telephone != null ){
				sharer.setTelephone(telephone);
			}
			if ( age != null ){
				sharer.setAge(age);
			}
			if ( postCode != null ){
				sharer.setPostCode(postCode);
			}
			if ( sex != null ){
				sharer.setSex(sex);
			}
			if ( picture.getBody()!= null){
				byte[] pic = picture.getBody();
				MediaType picType = picture.getHeaders().getContentType();
				sharer.setProfilePicture(pic);
				sharer.setProFilePictureType(picType);
			}
			this.sharerService.updateUser(sharer);
		}
	}
	
}
