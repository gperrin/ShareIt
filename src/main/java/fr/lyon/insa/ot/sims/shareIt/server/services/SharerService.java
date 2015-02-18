package fr.lyon.insa.ot.sims.shareIt.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;



@Service
public class SharerService implements ISharerService{

	@Autowired
	SharerRepository sharerRepository;

	@Override
	public boolean createUser(String lastName, String firstName, int postCode) {
		Sharer newSharer = new Sharer();
		newSharer.setFirstname(firstName);
		newSharer.setFirstname(lastName);
		newSharer.setPostCode(postCode);
		try{
			this.sharerRepository.save(newSharer);
		}
		catch (Exception e){
			return false;
		}
		return true;
	}

	@Override
	public Sharer getUser(int sharerId) {
		Sharer sharer = null;
		try{
			sharer = this.sharerRepository.findOne(sharerId);
		}
		catch ( Exception e ){
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
			return false;
		}
		return true;
	}

	
	
	
}
