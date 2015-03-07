package fr.lyon.insa.ot.sims.shareIt.server.domain.builders;

import org.springframework.http.MediaType;

import fr.lyon.insa.ot.sims.shareIt.server.domain.Sharer;
import fr.lyon.insa.ot.sims.shareIt.server.domain.UserStats;

public class SharerBuilder {
	private Sharer sharer = new Sharer();
	
	private SharerBuilder id ( int id ){
		sharer.setId(id);
		return this;
	}
	
	private SharerBuilder profilePicture ( byte[] profilePicture){
		sharer.setProfilePicture(profilePicture);
		return this;
	}
	private SharerBuilder profilePictureType ( MediaType profilePictureType){
		sharer.setProFilePictureType(profilePictureType);
		return this;
	}
	private SharerBuilder lastname( String lastname){
		sharer.setLastname(lastname);
		return this;
	}
	private SharerBuilder firstname( String firstname){
		sharer.setFirstname(firstname);
		return this;
	}
	private SharerBuilder age( int age){
		sharer.setAge(age);
		return this;
	}
	private SharerBuilder sex ( char sex){
		sharer.setSex(sex);
		return this;
	}
	private SharerBuilder rating ( float rating){
		sharer.setRating(rating);
		return this;
	}
	private SharerBuilder postCode ( int postCode){
		sharer.setPostCode(postCode);
		return this;
	}
	private SharerBuilder telephone ( String telephone ){
		sharer.setTelephone(telephone);
		return this;
	}
	private SharerBuilder userStats ( UserStats userStats){
		sharer.setUserStats(userStats);
		return this;
	}
	private Sharer build(){
		return sharer;
	}
}
