package fr.lyon.insa.ot.sims.shareIt.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;



@Service
public class SharerService implements ISharerService{

	@Autowired
	SharerRepository sharerRepository;

	@Override
	public Sharer createUser(String lastName, String firstName, int postCode) {
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(postCode);
		
		Sharer newSharer = new Sharer();
		newSharer.setFirstname(firstName);
		newSharer.setLastname(lastName);
		newSharer.setPostCode(postCode);
		newSharer.setAge(-1);
		newSharer.setProfilePicture(null);
		newSharer.setProFilePictureType(null);
		newSharer.setSex(' ');
		newSharer.setTelephone("");
		newSharer.setRating(0);
		try{
			this.sharerRepository.save(newSharer);
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return newSharer;
	}

	@Override
	public Sharer getUser(int sharerId) {
		Sharer sharer = null;
		try{
			sharer = this.sharerRepository.findOne(sharerId);
		}
		catch ( Exception e ){
			e.printStackTrace();
			return null;
		}
		return sharer;
	}

	@Override
	public boolean updateUser(Sharer sharer) {
		try {
			this.sharerRepository.save(sharer);
		}
		catch ( Exception e ){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	
	
}
