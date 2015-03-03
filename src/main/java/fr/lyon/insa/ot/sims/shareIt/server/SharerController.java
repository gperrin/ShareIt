package fr.lyon.insa.ot.sims.shareIt.server;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.BusinessLogicException;
import fr.lyon.insa.ot.sims.shareIt.server.exceptions.ResourceNotFoundException;

@Controller
public class SharerController extends GenericController{
	
	
	@RequestMapping(method = RequestMethod.POST, value="/user")
	@ResponseStatus (HttpStatus.CREATED) public @ResponseBody Sharer createUser(
			@RequestParam(required = true, value="firstname")String firstName,
			@RequestParam(required = true, value="lastname")String lastName,
			@RequestParam(required = true, value="postcode")int postCode,
			@RequestParam(required = false, value = "age") Integer age,
			@RequestParam(required = false , value = "sex") Character sex,
			@RequestParam(required = false , value = "phone") String telephone){
		Sharer sharer = this.sharerService.createUser(lastName, firstName, postCode);
		if ( sharer == null){
			throw new RuntimeException("User could not be created.");
		}
		if ( age != null ){
			sharer.setAge(age);
		}
		if ( sex != null ){
			if ( sex != 'M' && sex != 'F'){
				throw new BusinessLogicException("Sex parameter can either be 'M' or 'F'");
			}
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
		if ( sharer == null ) throw new ResourceNotFoundException("user", id);
		return sharer; 
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/user/{id:[\\d]+}")
	public @ResponseStatus (HttpStatus.OK) @ResponseBody Sharer updateUser(@PathVariable("id") int id,
			@RequestParam(required= false, value = "firstname") String firstName,
			@RequestParam(required= false, value = "lastname") String lastName,
			@RequestParam(required= false, value = "phone") String telephone,
			@RequestParam(required= false, value = "age") Integer age,
			@RequestParam(required= false, value = "sex") Character sex,
			@RequestParam(required= false, value = "postcode") Integer postCode,
			HttpEntity<byte[]> picture){
		Sharer sharer = this.sharerService.getUser(id);
		if ( sharer == null ) throw new ResourceNotFoundException ( "user", id);	
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
			if ( sex != 'M' && sex != 'F'){
				throw new BusinessLogicException("Sex parameter can either be 'M' or 'F'");
			}
			sharer.setSex(sex);
		}
		if (picture != null &&  picture.getBody()!= null && picture.getHeaders() != null && 
				picture.getHeaders().getContentType()!= null &&
				picture.getHeaders().getContentType().equals(MimeTypeUtils.IMAGE_PNG)){
			byte[] pic = picture.getBody();
			MediaType picType = picture.getHeaders().getContentType();
			sharer.setProfilePicture(pic);
			sharer.setProFilePictureType(picType);
		}
		if (! this.sharerService.updateUser(sharer)) throw new RuntimeException("User could not be updated :'(");
		return sharer;
	}
}
