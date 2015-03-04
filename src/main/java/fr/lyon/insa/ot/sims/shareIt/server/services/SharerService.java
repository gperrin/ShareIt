package fr.lyon.insa.ot.sims.shareIt.server.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.lyon.insa.ot.sims.shareIt.server.dao.SharerRepository;
import fr.lyon.insa.ot.sims.shareIt.server.dao.UserStatsRepository;
import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;

@Service
public class SharerService{

	@Autowired
	SharerRepository sharerRepository;
	
	@Autowired
	UserStatsRepository userStatsRepository;

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
	
	public Sharer createUser(String lastName, String firstName, int postCode) {
		Sharer newSharer = new Sharer();
		newSharer.setFirstname(firstName);
		newSharer.setLastname(lastName);
		newSharer.setPostCode(postCode);
		newSharer.setAge(0);
		newSharer.setProfilePicture(null);
		newSharer.setProFilePictureType(null);
		newSharer.setSex(' ');
		newSharer.setTelephone("");
		newSharer.setRating(-1);
		UserStats userStats = new UserStats();
		userStats = this.userStatsRepository.save(userStats);
		newSharer.setUserStats(userStats);
		this.sharerRepository.save(newSharer);
		return newSharer;
	}

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

	public boolean updateUser(Sharer sharer) {
		try {
			this.userStatsRepository.save(sharer.getUserStats());
			this.sharerRepository.save(sharer);
	}
		catch ( Exception e ){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Collection<Sharer> getSharers(int postcode) {
		return this.sharerRepository.findByPostCode(postcode);
	}	
}
