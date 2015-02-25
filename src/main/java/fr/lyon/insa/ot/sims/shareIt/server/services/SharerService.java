package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;



@Service
public class SharerService implements ISharerService{

	@Autowired
	SharerRepository sharerRepository;

	private Sharer retryPersist(Sharer sharer){ //quickfixx :)
		//sharer.setId(sharer.getId());
		int id = sharer.getId();
		for ( int i = id+1; i< id+10 ; i++){
			sharer.setId(i);
			try{
				this.sharerRepository.save(sharer);
				return sharer;
			}
			catch(DataIntegrityViolationException e){
				continue;
			}
		}
		return sharer;
	}
	
	@Override
	public Sharer createUser(String lastName, String firstName, int postCode) {
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
		catch ( DataIntegrityViolationException e){
			return retryPersist(newSharer);
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

	@Override
	public Collection<Sharer> getSharers(int postcode) {
		return this.sharerRepository.findByPostCode(postcode);
	}

	
	
	
}
